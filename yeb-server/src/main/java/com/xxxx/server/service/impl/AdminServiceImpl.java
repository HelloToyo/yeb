package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.mapper.RoleMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2020-09-07
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private JwtUtil jwtUtil;
	@Value("${jwt.tokenHead}")
	private String tokenHead;
	@Autowired
	private RoleMapper roleMapper;


	/**
	 * 登录
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public RespBean login(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
			return RespBean.error("用户名或密码不正确！");
		}
		if (!userDetails.isEnabled()) {
			return RespBean.error("用户被禁用，请联系管理员！");
		}
		//将用户对象存入SpringSecurity全局上下文中，方便其他方法获取用户对象
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
				null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		String token = jwtUtil.generatorToken(userDetails);
		Map<String, Object> map = new HashMap<>();
		map.put("tokenHead", tokenHead);
		map.put("token", token);
		return RespBean.success("登录成功！", map);
	}

	/**
	 * 根据用户名获取用户对象
	 * @param username
	 * @return
	 */
	@Override
	public Admin getAdminByUserName(String username) {
		Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
		return admin;
	}

	/**
	 * 根据用户id获取角色
	 * @param adminId
	 * @return
	 */
	@Override
	public List<Role> getRoles(Integer adminId) {
		return roleMapper.getRoles(adminId);
	}
}
