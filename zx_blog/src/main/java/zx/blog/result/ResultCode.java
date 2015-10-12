package zx.blog.result;

/**
 * 返回码
 * @author Administrator
 *
 */
public enum ResultCode
{
	SUCCESS("1", ""),
	
	LOGIN_EMPTY_USERNAME("-1", "用户名为空！"),
	
	LOGIN_EMPTY_PASSWORD("-2", "密码为空！"),
	
	LOGIN_FAIL("-3", "用户名和密码不匹配"),
	
	;
	private String code;
	private String content;
	
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	private ResultCode(String code, String content)
	{
		this.code = code;
		this.content = content;
	}
}
