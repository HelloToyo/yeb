package com.xxxx.server.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 权限控制
 * 根据用户获取对象的角色
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class CustomUrlAccessDecisionManager implements AccessDecisionManager {


	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		for (ConfigAttribute configAttribute : configAttributes) {
			//获取所需角色
			String needRole = configAttribute.getAttribute();
			//判断所需角色是否为登录可用
			if (needRole.equals("ROLE_login")){
				//判断是否登录
				if (authentication instanceof AnonymousAuthenticationToken){
					throw new AccessDeniedException("尚未登录，请登录！");
				}else {
					return;
				}
			}
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority authority : authorities) {
				if (authority.getAuthority().equals(needRole)){
					return;
				}
			}
		}
		throw new AccessDeniedException("权限不足，请联系管理员");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
}