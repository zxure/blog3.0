package zx.blog.user.domain;

import zx.blog.entity.TableRecordVersion;

public class User extends TableRecordVersion{
	private static final long serialVersionUID = -5821890438452510851L;
	private int userId; //用户ID
	private String userName;  //用户名
	private String title;	//称呼（用于显示在界面上）
	private String userPassword;  //用户密码
	private String lastLoginTime;   //上次登录时间(时间戳，秒)
	private int userState;		//用户状态
	private int privilege;		//权限级别
	private String imgUrl;		//头像地址
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getLastLoginTime()
	{
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}
	public int getUserState() {
		return userState;
	}
	public void setUserState(int userState) {
		this.userState = userState;
	}
	public int getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Override
	public String obtainCacheKey() {
		return User.class.getSimpleName() + SEPERATOR + this.userId;
	}
	
	public static String genKey(int id){
		return User.class.getSimpleName() + SEPERATOR + id;
	}
}
