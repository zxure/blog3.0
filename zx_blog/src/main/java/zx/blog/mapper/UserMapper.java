package zx.blog.mapper;

import org.apache.ibatis.annotations.Param;

import zx.blog.user.domain.User;

public interface UserMapper extends BaseMapper<Integer, User>{
	
	/**
	 * 根据用户ID 查找用户
	 * @param userId
	 * @return 用户
	 */
	public User findByName(@Param("userName")String userName);
}
