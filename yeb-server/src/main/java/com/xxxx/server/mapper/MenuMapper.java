package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2020-09-07
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 获取所有菜单
	 * @param adminId
	 * @return
	 */
	List<Menu> menusWithChildren(Integer adminId);

	/**
	 * 查询菜单权限
	 * @return
	 */
	List<Menu> menusWithRoles();


	List<Menu> getMenusByAdminId(Integer adminId);
}
