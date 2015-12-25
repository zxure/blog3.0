package zx.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zx.blog.user.domain.User;

public interface UserMapper extends BaseMapper<Integer, User>{
	/**
	 * 查询全部的用户
	 * @return 全部的用户
	 */
	public List<User> findAll();
	
	/**
	 * 根据用户ID 查找用户
	 * @param userId
	 * @return 用户
	 */
	public User findByName(@Param("userName")String userName);
}
