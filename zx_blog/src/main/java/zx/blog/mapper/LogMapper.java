package zx.blog.mapper;

import zx.blog.log.domain.LoginLog;

public interface LogMapper {
	/**
	 * 登入日志记录
	 * @param loginLog
	 */
	public void insertLoginLog(LoginLog loginLog);
}
