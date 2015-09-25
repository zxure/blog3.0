package zx.blog.settings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import zx.blog.cache.container.CacheHolder;

@Controller
@RequestMapping("/admin")
public class SettingsController {
	
	/**
	 * 全局 设置页面跳转
	 * @param type
	 * @return
	 */
	@RequestMapping(value={"/settings", "/settings/", "/settings/global"})
	public ModelAndView settingsGlobal(){
		ModelAndView mdv = new ModelAndView();
		mdv.addObject("isSettingsGlobal", true);
		mdv.setViewName("backstage/settingsGlobal");
		return mdv;
	}
	
	/**
	 * 个人 设置页面跳转
	 * @param type
	 * @return
	 */
	@RequestMapping("/settings/user")
	public ModelAndView settingsUser(){
		ModelAndView mdv = new ModelAndView();
		mdv.addObject("isSettingsUser", true);
		mdv.setViewName("backstage/settingsUser");
		return mdv;
	}
	
	/**
	 * 类别 设置页面跳转
	 * @param type
	 * @return
	 */
	@RequestMapping("/settings/category")
	public ModelAndView settingsCategory(){
		ModelAndView mdv = new ModelAndView();
		mdv.addObject("isSettingsCategory", true);
		mdv.addObject("categories", CacheHolder.getSimpleCategoryDtoList());
		mdv.setViewName("backstage/settingsCategory");
		return mdv;
	}
	
	/**
	 * 标签 设置页面跳转
	 * @param type
	 * @return
	 */
	@RequestMapping("/settings/tag")
	public ModelAndView settingsTag(){
		ModelAndView mdv = new ModelAndView();
		mdv.addObject("isSettingsTag", true);
		mdv.setViewName("backstage/settingsTag");
		return mdv;
	}
	
	/**
	 * 实验室 设置页面跳转
	 * @param type
	 * @return
	 */
	@RequestMapping("/settings/library")
	public ModelAndView settingsLibrary(){
		ModelAndView mdv = new ModelAndView();
		mdv.addObject("isSettingsLibrary", true);
		mdv.setViewName("backstage/settingsLibrary");
		return mdv;
	}
}
