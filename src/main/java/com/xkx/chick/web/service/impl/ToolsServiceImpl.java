package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.constant.ChickConstant;
import com.xkx.chick.web.mapper.ToolsMapper;
import com.xkx.chick.web.pojo.entity.Tools;
import com.xkx.chick.web.service.IToolsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


/**
 * <p>
 * 在线工具表 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Service
public class ToolsServiceImpl extends ServiceImpl<ToolsMapper, Tools> implements IToolsService {

    /**
     * 获取在线工具列表
     *
     * @param validPage 分页信息
     * @param keyword   关键字
     * @param delFlag   删除标记
     * @return 分页列表
     */
    @Override
    public Page<Tools> list(Page<Tools> validPage, String keyword, String delFlag) {
        LambdaQueryWrapper<Tools> wrapper = Wrappers.<Tools>lambdaQuery()
                .eq(Tools::getDelFlag, delFlag)
                .orderByAsc(Tools::getCreateDate);
        //3.添加关键字
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(wr -> wr.like(Tools::getName, keyword));
        }
        return baseMapper.selectPage(validPage, wrapper);
    }

    /**
     * 生成UUID
     *
     * @param count 生成个数
     * @return UUID
     */
    @Override
    public List<String> generateUUID(Integer count) {
        ArrayList<String> UUIDList = new ArrayList<>();
        for (int i = 1; i <= count; i++){
            UUIDList.add(UUID.randomUUID().toString());
        }
        return UUIDList;
    }

    /**
     * 生成随机密码
     *
     * @param count 生成个数
     * @param numberCount 密码位数
     * @param smallLetter 小写字母
     * @param bigLetter 大写字母
     * @param number 数字
     * @param specialCharacter 特殊字符
     * @param rubbishCharacter 去除不易识别字符
     * @return 随机密码
     */
    @Override
    public List<String> generateRandomCipher(Integer count, Integer numberCount, Boolean smallLetter, Boolean bigLetter, Boolean number, Boolean specialCharacter, Boolean rubbishCharacter) {
        //存储用户选择的字符
        List<String> characterList =new ArrayList<>();
        if (smallLetter){
            characterList.addAll(ChickConstant.LOWERCASE);
        }
        if (bigLetter){
            characterList.addAll(ChickConstant.UPPERCASE);
        }
        if (number){
            characterList.addAll(ChickConstant.NUMBER);
        }
        if (specialCharacter){
            characterList.addAll(ChickConstant.SPECIAL_CHARACTER);
        }
        //是否去除不易识别字符
        if (rubbishCharacter){
            for (String c : ChickConstant.RUBBISH_CHARACTER){
                if (characterList.contains(c)){
                    characterList.remove(c);
                }
            }
        }
        //计算结果
        List<String> resultList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++){
            StringBuffer resultString = new StringBuffer();
            for (int j = 0; j < numberCount; j++){
                int n = random.nextInt(characterList.size());
                resultString.append(characterList.get(n));
            }
            resultList.add(resultString.toString());
        }
        return resultList;
    }
}
