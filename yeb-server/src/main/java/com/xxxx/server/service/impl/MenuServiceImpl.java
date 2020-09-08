package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.MenuMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2020-09-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private RedisTemplate redisTemplate;


	/**
	 * 获取所有菜单
	 * @param adminId
	 * @return
	 */
	@Override
	public List<Menu> menusWithChildren(Integer adminId) {
		return menuMapper.menusWithChildren(adminId);
	}

	/**
	 * 查询菜单权限
	 * @return
	 */
	@Override
	public List<Menu> menusWithRoles() {
		return menuMapper.menusWithRoles();
	}
	/**
	 * 通过用户id获取菜单列表
	 *
	 * @return
	 */
	@Override
	public List<Menu> getMenusByAdminId() {
		Integer adminId = ((Admin)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		//查询缓存中是否有数据
		List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
		if (CollectionUtils.isEmpty(menus)){
		//如果没数据，数据库中查询，并设置到缓存中
		menus = menuMapper.getMenusByAdminId(adminId);
		valueOperations.set("menu_"+adminId,menus);
		}
		return menus;
	}
}
