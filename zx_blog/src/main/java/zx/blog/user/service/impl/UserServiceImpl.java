package zx.blog.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.cache.container.CacheHolder;
import zx.blog.user.dao.UserDao;
import zx.blog.user.domain.User;
import zx.blog.user.service.UserService;

@Component
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> findAllUser() {
		return this.userDao.findAll();
	}

	@Override
	public User findByUserName(String userName) {
		return this.userDao.findByName(userName);
	}

	@Override
	public User validLogin(String userName, String password) {
		User user = CacheHolder.getUserByName(userName);
		if(user != null && password.equals(user.getUserPassword()))
			return user;
		return null;
	}

	@Override
	public void updateUserLoginTime(User user) {
		this.userDao.updateUser(user);
	}
}
