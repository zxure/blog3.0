package zx.blog.ueditor.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import zx.blog.ueditor.ActionEnter;

@Controller
@RequestMapping("/admin/")
public class UeditorController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static final Logger logger = Logger.getLogger(UeditorController.class);
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	@RequestMapping("ueditor/mainController")
	public void ueditorAllController() throws IOException{
 		response.setHeader("Content-Type" , "text/html");
		String rootPath = request.getServletContext().getRealPath( "/" );
		logger.error(rootPath);
		ActionEnter actionEnter = new ActionEnter( request, rootPath );
		String result = actionEnter.exec();
		response.getOutputStream().write(result.getBytes());
	}
}
