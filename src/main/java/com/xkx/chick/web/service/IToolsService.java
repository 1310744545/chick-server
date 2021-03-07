package com.xkx.chick.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.common.base.R;
import com.xkx.chick.web.pojo.entity.Tools;
import com.xkx.chick.web.pojo.vo.ToolsVO;

import java.util.List;

/**
 * <p>
 * 在线工具表 服务类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
public interface IToolsService extends IService<Tools> {
    /**
     * 获取在线工具列表
     *
     * @param validPage 分页信息
     * @param keyword   关键字
     * @param delFlag   删除标记
     * @return 分页列表
     */
    Page<ToolsVO> list(Page<ToolsVO> validPage, String keyword, String delFlag);

    /**
     * 生成UUID
     *
     * @param count 生成个数
     * @return UUID
     */
    List<String> generateUUID(Integer count);

    /**
     * 生成随机密码
     *
     * @param count 生成个数
     * @param smallLetter 生成个数
     * @param bigLetter 生成个数
     * @param number 生成个数
     * @param specialCharacter 生成个数
     * @param rubbishCharacter 生成个数
     * @return 随机密码
     */
    List<String> generateRandomCipher(Integer count, Integer numberCount, Boolean smallLetter, Boolean bigLetter, Boolean number, Boolean specialCharacter, Boolean rubbishCharacter);

    /**
     * base64编码/解码
     *
     * @param code base64编码/解码的字符
     * @param flag 0编码 1解码
     * @return 编码/解码后的字符
     */
    R base64EncodeOrDecode(String code, String flag);
}
