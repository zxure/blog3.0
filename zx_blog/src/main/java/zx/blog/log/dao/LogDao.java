package zx.blog.log.dao;

import zx.blog.log.domain.LoginLog;

public interface LogDao {
	/**
	 * 登入日志记录
	 * @param loginLog
	 */
	public void insertLoginLog(LoginLog loginLog);
}
