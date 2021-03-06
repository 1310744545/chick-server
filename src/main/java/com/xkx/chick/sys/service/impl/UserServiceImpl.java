package com.xkx.chick.sys.service.impl;


import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.security.UserInfoDetail;
import com.xkx.chick.common.security.service.UserDetailServiceImpl;
import com.xkx.chick.common.util.AliyunOSSClientUtil;
import com.xkx.chick.common.util.JwtUtils;
import com.xkx.chick.common.util.SecurityUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.mapper.FileMapper;
import com.xkx.chick.sys.mapper.UserMapper;
import com.xkx.chick.sys.mapper.ZdxMapper;
import com.xkx.chick.sys.pojo.entity.Role;
import com.xkx.chick.sys.pojo.entity.SysFile;
import com.xkx.chick.sys.pojo.entity.User;
import com.xkx.chick.sys.pojo.entity.Zdx;
import com.xkx.chick.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.xkx.chick.common.constant.OSSClientConstants.BACKET_NAME;
import static com.xkx.chick.common.constant.OSSClientConstants.URL_HEAD;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-01-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserMapper userMapper;
    @Resource
    UserDetailServiceImpl userDetailService;
    @Resource
    private JwtUtils jwtUtils;
    @Value("${jwt.head}")
    private String head;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private AliyunOSSClientUtil aliyunOSSClientUtil;
    @Resource
    private ZdxMapper zdxMapper;
    @Resource
    private FileMapper fileMapper;

    /**
     * 查询是否存在该用户
     *
     * @param username
     * @return user
     */
    @Override
    public User getUserByUserName(String username) {
        User user = baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getDelFlag, CommonConstants.NO));
        if (ObjectUtils.isNull(user)) {
            return null;
        }
        return user;
    }

    /**
     * 查询用户的所有权限
     *
     * @param username
     * @return
     */
    @Override
    public List<String> getPermissionByUserName(String username) {
        return userMapper.getPermissionByUserName(username);
    }

    /**
     * 根据用户ID获取用户角色
     *
     * @param username 用户名
     * @return 用户角色
     */
    @Override
    public List<Role> getUserRole(String username) {
        return baseMapper.selectUserRole(username);
    }

    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @param request
     */
    @Override
    public R<HashMap> login(String username, String password, String captchaText, String code, HttpServletRequest request) {
        //校验验证码
//        String rightCode = (String) request.getSession().getAttribute("rightCode");
//        if (StringUtils.isEmpty(rightCode) || !rightCode.equalsIgnoreCase(code)) {
//            return R.failed("验证码输入错误,请重新输入");
//        }
        if(!code.equals(captchaText)){
            return R.failed("验证码输入错误,请重新输入");
        }
        //构造token
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        //验证用户信息
        try {
            Authentication authentication = authenticationManager.authenticate(upToken);
        } catch (DisabledException e) {
            return R.failed("账户已被禁用");
        } catch (LockedException e) {
            return R.failed("账户已被锁定");
        } catch (AuthenticationException e) {
            return R.failed("用户名或密码不正确");
        }

        //通过自己的UserDetailService获取用户信息
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        //生成token
        String token = jwtUtils.generateToken((UserInfoDetail) userDetails);

        HashMap<String, String> tokenCarry = new HashMap<>();
        tokenCarry.put("head", head);
        tokenCarry.put("token", token);
        return R.ok(tokenCarry, "登陆成功");
    }

    @Override
    public R register(String username, String password) {
        List<User> userCount = baseMapper.selectList(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username));
        if (userCount.size() > 0){
            return R.failed("该用户名已存在");
        }
        String encode = passwordEncoder.encode(password);
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(encode);
        int insert = baseMapper.insert(user);
        if (insert > 0) {
            return R.ok("注册成功");
        } else {
            return R.failed("注册失败,系统错误");
        }
    }

    /**
     * 退出登录
     */
    @Override
    public R logout() {
        //获取用户登录信息
        String username = SecurityUtils.getUsername();
        //删除Token信息
        if (StringUtils.isNotBlank(username)) {
//            jwtUtils.deleteToken(username);
        }
        return R.ok();
    }

    /**
     * 通过用户id获取用户名字
     */
    @Override
    public String getNameByUserId(String userId) {
        return userMapper.getNameByUserId(userId);
    }

    /**
     * 通过jwt令牌获取用户信息
     */
    @Override
    public User getUserByJwt(String JwtToken) {
        String id = jwtUtils.getIDFromToken(JwtToken.split(" ")[1]);
        return baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .select(User::getUserId, User::getUsername, User::getName, User::getBirthday, User::getSex, User::getPhone,
                        User::getEmail, User::getLockFlag, User::getEnabledFlag, User::getLastLoginTime,
                        User::getDelFlag, User::getHeadPortraitUrl)
                .eq(User::getUserId, id));
    }

    /**
     * 上传头像
     * @param userId 用户id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R uploadHeadPortrait(MultipartFile file, String userId) {
        //用户头像的字典项
        Zdx zdx = zdxMapper.selectOne(Wrappers.<Zdx>lambdaQuery().eq(Zdx::getName, "用户头像"));
        //初始化OSSClient
        OSSClient ossClient = aliyunOSSClientUtil.getOSSClient();
        //上传文件,返回唯一key
        System.out.println(file.getOriginalFilename());
        String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, file, BACKET_NAME, zdx.getValue() + userId +"/");
        //关闭OSSClient
        ossClient.shutdown();
        //获取地址
        String url  = URL_HEAD + zdx.getValue() + userId +"/" + file.getOriginalFilename();

        //先通过md5ket去数据库中检查是否已经上传,如果是则不在向数据库中插入
        SysFile sysFile = fileMapper.selectOne(Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getMd5key, md5key)
                .eq(SysFile::getDelFlag, CommonConstants.DELETE_FLAG));
        //插入文件表
        if (ObjectUtils.isEmpty(sysFile)){
            String uuid= UUID.randomUUID().toString();
            sysFile = new SysFile(uuid, md5key, file.getOriginalFilename(), zdx.getId(), url, file.getOriginalFilename(), file.getSize());
            if (fileMapper.insert(sysFile) < 1) {
                return R.failed("上传失败");
            }
        }
        //更改用户头像
        User user = getById(userId);
        user.setHeadPortraitUrl(url);
        if (userMapper.updateById(user) < 1){
            return R.failed("上传失败");
        }
        return R.ok("上传成功");
    }

    @Override
    public R updateUser(String userId, String sex, String phone, String name, String email, String birthday) {
        User user = new User(userId, name, sex, birthday, phone, email);
        int i = baseMapper.updateById(user);
        if (i == 1){
            return R.ok("更新成功");
        }
        return R.failed("更新失败");
    }
}
