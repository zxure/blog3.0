package zx.blog.log.domain;


/*
 * 玩家登录日志类
 */
public class LoginLog {
	private int logId;
	private int userId;
	private String lastLoginTime;
	private String lastLoginIp;
	
	/**
	 * 实例化方法
	 * @param userId
	 * @param lastLoginTime
	 * @param lastLoginIp
	 * @return
	 */
	public static LoginLog valueOf(int userId, String lastLoginTime, String lastLoginIp)
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
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
}
