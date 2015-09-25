package zx.blog.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.log.dao.LogDao;
import zx.blog.log.domain.LoginLog;
import zx.blog.log.service.LogService;

@Component
public class LogServiceImpl implements LogService{
	@Autowired
	public LogDao logDao;
	
	@Override
	public void addLoginLog(LoginLog loginLog) {
		this.logDao.insertLoginLog(loginLog);
	}
}
