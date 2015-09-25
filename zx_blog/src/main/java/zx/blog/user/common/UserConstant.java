package zx.blog.user.common;

public class UserConstant {
	/**登录状态 标记名称*/
	public static final String LOGIN_KEY = "is_login";
	/**登录结果 标记名称*/
	public static final String LOGIN_RESULT = "loginResult";
	/**登出结果 标记名称*/
	public static final String LOGOUT_RESULT = "logoutResult";
	
	/**session 过期时间 秒   1天*/
	public static final int SESSION_INVALID_TIME = 86400;
	
	/**后台登录页面 视图名称*/
	public static final String LOGIN_VIEW_NAME = "backstage/login";
	/**后台首页页面 视图名称*/
	public static final String BACKSTAGE_MAIN_VIEW_NAME = "backstage/index";
}
