package zx.blog.log.domain;

import java.util.Date;

/*
 * 玩家登录日志类
 */
public class LoginLog {
	private int logId;
	private int userId;
	private Date lastLoginTime;
	private String lastLoginIp;
	
	/**
	 * 实例化方法
	 * @param userId
	 * @param lastLoginTime
	 * @param lastLoginIp
	 * @return
	 */
	public static LoginLog valueOf(int userId, Date lastLoginTime, String lastLoginIp)
	{
		LoginLog result = new LoginLog();
		
		result.setUserId(userId);
		result.setLastLoginIp(lastLoginIp);
		result.setLastLoginTime(lastLoginTime);
		
		return result;
	}
	
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
}
