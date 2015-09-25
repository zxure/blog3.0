package zx.blog.log.service;

import zx.blog.log.domain.LoginLog;

public interface LogService {
	/**
	 * 增加 登录日志
	 * @param loginLog
	 */
	public void addLoginLog(LoginLog loginLog);
}
