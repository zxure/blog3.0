package zx.blog.category.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zx.blog.cache.CacheHolder;
import zx.blog.category.domain.Category;
import zx.blog.category.service.CategoryService;
import zx.blog.util.TimeDateUtil;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 添加一个类别
	 * @param categoryName
	 * @return
	 */
	@RequestMapping("/category/add")
	@ResponseBody
	public Map<String, Object> addCategory(String categoryName){
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isBlank(categoryName)){
			result.put("msgCode", -1);
			result.put("message", "类别名称不可为空！");
			return result;
		}
		categoryName = categoryName.trim();
		//存在同样的名字
		if(CacheHolder.isExistCategory(categoryName)){
			result.put("msgCode", -2);
			result.put("message", "已经存在相同的类别！");
			return result;
		}
		Category category = new Category();
		category.setCategoryName(categoryName);
		category.setCreateTime(TimeDateUtil.getCurrentTimestr());
		categoryService.addCategory(category);
		CacheHolder.addCategory(category);
		result.put("categoryId", category.getCategoryId());
		result.put("msgCode", 0);
		result.put("message", "类别添加成功！");
		return result;
	}
	
	@RequestMapping("/category/delete")
	@ResponseBody
	public Map<String, Object> removeCategory(int categoryId){
		Map<String, Object> result = new HashMap<String, Object>();
		Category category = CacheHolder.getCagetoryById(categoryId);
		if(category == null){
			result.put("msgCode", -1);
			result.put("message", "类别不存在！");
			return result;
		}
		categoryService.remove(categoryId);
		CacheHolder.removeCategoryById(categoryId);
		result.put("msgCode", 0);
		result.put("message", "类别删除成功！");
		return result;
	}
	
	@RequestMapping("/category/update")
	@ResponseBody
	public Map<String, Object> update(int categoryId, String categoryName){
		Map<String, Object> result = new HashMap<String, Object>();
		Category category = CacheHolder.getCagetoryById(categoryId);
		if(category == null){
			result.put("msgCode", -1);
			result.put("message", "该类别不存在！");
			return result;
		} 
		if(StringUtils.isBlank(categoryName)){
			result.put("msgCode", -2);
			result.put("message", "类别名称不可以为空！");
			return result;
		}
		categoryName = categoryName.trim();
		category.setCategoryName(categoryName);
		categoryService.updateCategory(category);
		result.put("msgCode", 0);
		result.put("message", "更新成功！");
		return result;
	}
	
}
