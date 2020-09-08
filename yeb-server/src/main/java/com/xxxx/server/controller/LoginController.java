package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminLoginParams;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 登录
 *
 * @author zhoubin
 * @since 1.0.0
 */
@RestController
public class LoginController {

	@Autowired
	private IAdminService adminService;

	@ApiOperation(value = "登录")
	@PostMapping("/login")
	public RespBean login(@RequestBody AdminLoginParams adminLoginParams) {
		return adminService.login(adminLoginParams.getUsername(), adminLoginParams.getPassword());
	}

	@ApiOperation(value = "获取用户对象")
	@GetMapping("/admin/info")
	public Admin adminInfo(Principal principal) {
		if (null == principal) {
			return null;
		}
		String username = principal.getName();
		Admin admin = adminService.getAdminByUserName(username);
		if (null == admin) {
			return null;
		}
		admin.setRoles(adminService.getRoles(admin.getId()));
		admin.setPassword(null);
		return admin;
	}


	@ApiOperation(value = "退出登录")
	@PostMapping("/logout")
	public RespBean logout() {
		return RespBean.success("注销成功！");
	}

}