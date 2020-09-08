package com.xxxx.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.server.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登录或者token失效的时候的访问结果
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		RespBean respBean = RespBean.error("尚未登录或登录已过期，请重新登录！");
		respBean.setCode(401);
		writer.write(new ObjectMapper().writeValueAsString(respBean));
		writer.flush();
		writer.close();
	}
}