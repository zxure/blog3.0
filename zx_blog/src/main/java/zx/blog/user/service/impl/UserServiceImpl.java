package zx.blog.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.cache.core.impl.UserCacheManager;
import zx.blog.mapper.UserMapper;
import zx.blog.user.domain.User;
import zx.blog.user.service.UserService;

@Component
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserCacheManager userCacheManager;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> findAllUser() {
		return this.userCacheManager.findAll();
	}

	@Override
	public Optional<User> findByUserName(String userName) {
		return this.findAllUser().stream().filter(user->StringUtils.isNotBlank(userName) && userName.equals(user.getUserName())).findFirst();
	}

	@Override
	public Optional<User> validLogin(String userName, String password) {
		return this.findAllUser().stream()
				.filter(user->StringUtils.isNotBlank(userName) && userName.equals(user.getUserName()) && StringUtils.isNotBlank(password) && password.equals(user.getUserPassword()))
				.findFirst();
	}

	@Override
	public void updateUserLoginTime(User user) {
		this.userMapper.update(user);
	}
}
