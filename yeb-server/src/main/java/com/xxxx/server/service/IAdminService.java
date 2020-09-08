package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2020-09-07
 */
public interface IAdminService extends IService<Admin> {

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	RespBean login(String username, String password);

	/**
	 * 根据用户名获取用户对象
	 * @param username
	 * @return
	 */
	Admin getAdminByUserName(String username);

	/**
	 * 根据用户id获取角色
	 * @param adminId
	 * @return
	 */
	List<Role> getRoles(Integer adminId);
}
