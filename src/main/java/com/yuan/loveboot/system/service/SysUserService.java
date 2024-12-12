package com.yuan.loveboot.system.service;

import com.yuan.loveboot.common.mybatis.service.BaseService;
import com.yuan.loveboot.system.dto.SysUserBaseDTO;
import com.yuan.loveboot.system.dto.SysUserDTO;
import com.yuan.loveboot.system.dto.SysUserPasswordDTO;
import com.yuan.loveboot.system.po.SysUser;
import com.yuan.loveboot.system.vo.SysUserProfileVO;
import com.yuan.loveboot.system.vo.SysUserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 系统用户
 *
 * @author Maverick
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 查找用户昵称和头像
     *
     * @param idList 用户ID
     * @return 查找结果
     */
    List<SysUserProfileVO> findByIds(List<Integer> idList);

    void save(SysUserDTO dto);

    void update(SysUserBaseDTO dto);

    /**
     * 更新当前用户头像
     *
     * @param file 头像
     */
    void updateAvatar(MultipartFile file);

    /**
     * 更新当前用户背景图
     *
     * @param file 背景图
     */
    void updateBackground(MultipartFile file);

    /**
     * 更新密码
     *
     * @param dto 修改信息
     */
    void updatePassword(SysUserPasswordDTO dto);

    /**
     * 获取当前用户的信息
     *
     * @return 用户信息
     */
    SysUserVO getUser();
}
