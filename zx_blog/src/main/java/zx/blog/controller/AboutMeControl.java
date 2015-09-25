package zx.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutMeControl {
	
	@RequestMapping("/about")
	public ModelAndView album(){
		ModelAndView mdv = new ModelAndView();
		mdv.setViewName("about/about");
		return mdv;
	}
}
