package zx.blog.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import zx.blog.article.service.ArticleService;
import zx.blog.log.domain.LoginLog;
import zx.blog.log.service.LogService;
import zx.blog.result.ResultCode;
import zx.blog.user.common.UserConstant;
import zx.blog.user.domain.User;
import zx.blog.user.service.UserService;
import zx.blog.util.TimeDateUtil;

@Controller
@RequestMapping("/admin")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private LogService logService;
	@Autowired
	private ArticleService articleService;
	/**
	 * 用户登录
	 * @param userName  用户名
	 * @param password	密码
	 * @return 用户信息 
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, String> login(String userName, String password){
		Map<String, String> map = new HashMap<String, String>();
		
		if(StringUtils.isBlank(userName)){
			map.put("msgCode", ResultCode.LOGIN_EMPTY_USERNAME.getCode());
			map.put("errMsg", ResultCode.LOGIN_EMPTY_USERNAME.getContent());
			return map;
		}
		
		if(StringUtils.isBlank(password)){
			map.put("msgCode", ResultCode.LOGIN_EMPTY_PASSWORD.getCode());
			map.put("errMsg", ResultCode.LOGIN_EMPTY_PASSWORD.getContent());
			return map;
		}
		
		User user = this.userService.validLogin(userName, password);
		if(user != null){
			Date nowTime = TimeDateUtil.getNowTime();
			//插入登录日志
			LoginLog loginLog = LoginLog.valueOf(user.getUserId(), nowTime, this.getRemoteIp());
			
			this.logService.addLoginLog(loginLog);
			
			//更新用户登录时间
			user.setLastLoginTime(nowTime);
			this.userService.updateUserLoginTime(user);
			
			//设置用户登录状态到 session
			HttpSession session = this.getRequest().getSession(true) ;
			session.setAttribute(UserConstant.LOGIN_KEY, user);
			session.setMaxInactiveInterval(UserConstant.SESSION_INVALID_TIME);
			
			map.put("msgCode", ResultCode.SUCCESS.getCode());
		} else {
			map.put("msgCode", ResultCode.LOGIN_FAIL.getCode());
			map.put("errMsg", ResultCode.LOGIN_FAIL.getContent());
		}
		return map;
	}
	
	/**
	 * 登出
	 * @param userName
	 * @return
	 */
	@RequestMapping("/logout") 
	public ModelAndView logout(){
		ModelAndView mdv = new ModelAndView();
		mdv.setViewName(UserConstant.LOGIN_VIEW_NAME);
		HttpSession session = this.getRequest().getSession(true) ;
		
		if(session.getAttribute(UserConstant.LOGIN_KEY) != null){
			session.removeAttribute(UserConstant.LOGIN_KEY);
			mdv.addObject(UserConstant.LOGOUT_RESULT, true);
		} else {
			mdv.addObject(UserConstant.LOGOUT_RESULT, false);
		}
		return mdv;
	}
	
	/*
	 * 跳转到登录页面
	 */
	@RequestMapping("/loginPage")
	public String loginPage(){
		return UserConstant.LOGIN_VIEW_NAME;
	}
	
	/*
	 * 跳转到首页
	 */
	@RequestMapping(value={"","/", "/index"})
	public ModelAndView mainPage(){
		ModelAndView mdv = new ModelAndView();
/*		//设置所有的文章基本信息（名称，作者，时间，类别，标签）
		List<SimpleArticleDto> articles = articleService.getAllSimpleArticleDto();
		//设置最近一篇文章的详细信息
		if(articles != null && articles.size() > 0){
			Article firstArticle = articleService.getArticleDtoById(articles.get(0).getArticleId());
			mdv.addObject("articles", articles);
			mdv.addObject("firstArticle", firstArticle);
		}
		mdv.setViewName(UserConstant.BACKSTAGE_MAIN_VIEW_NAME);*/
		return mdv;
	}
	
	//获取request
	public HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	//获取IP地址
	private String getRemoteIp(){
		HttpServletRequest request = this.getRequest() ;
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	@RequestMapping("self")
	public ModelAndView selfInfoConfig(){
		return null;
	}
}
