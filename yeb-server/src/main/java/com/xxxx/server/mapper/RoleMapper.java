package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2020-09-07
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 根据用户id获取角色
	 * @param adminId
	 * @return
	 */
	List<Role> getRoles(Integer adminId);
}
