package com.xxxx.server.config;

import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.security.access.SecurityConfig;

import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求的url获取对应的角色
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class CustomerFilter implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private IMenuService menuService;

	AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		//获取请求的url
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		List<Menu> menus = menuService.menusWithRoles();
		for (Menu menu : menus) {
			//判断请求url是否为菜单的url
			if (antPathMatcher.match(menu.getUrl(),requestUrl)) {
				String[] roles = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
				return SecurityConfig.createList(roles);
			}
		}
		//如果没有匹配上菜单，默认登录就可以用
		return SecurityConfig.createList("ROLE_login");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
}