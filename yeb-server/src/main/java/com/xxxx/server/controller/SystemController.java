package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system")
public class SystemController {

	@Autowired
	private IMenuService menuService;

	@ApiOperation(value = "获取所有菜单")
	@GetMapping("/menu")
	public List<Menu> MenusWithChildren(Authentication authentication){
				return menuService.menusWithChildren(((Admin)authentication.getPrincipal()).getId());
	}

}