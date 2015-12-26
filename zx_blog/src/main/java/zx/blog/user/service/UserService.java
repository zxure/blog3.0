package zx.blog.user.service;

import java.util.List;
import java.util.Optional;

import zx.blog.user.domain.User;

public interface UserService {
	
	/**
	 * 查找全部的用户
	 * @return
	 */
	public List<User> findAllUser();
	
	/**
	 * 根据用户名查找用户
	 * @param userName  用户名
	 * @return 
	 */
	public Optional<User> findByUserName(String userName);

	/**
	 * 验证玩家登录
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 */
	public Optional<User> validLogin(String userName, String password);

	/**
	 * 更新玩家登录的时间
	 * @param user
	 */
	public void updateUserLoginTime(User user);

}
