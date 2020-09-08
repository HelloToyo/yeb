package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2020-09-07
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * 获取所有菜单
	 * @return
	 */
	List<Menu> menusWithChildren(Integer adminId);

	/**
	 * 查询菜单权限
	 * @return
	 */
	List<Menu> menusWithRoles();

	List<Menu> getMenusByAdminId();
}
