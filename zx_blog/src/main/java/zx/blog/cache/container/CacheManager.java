package zx.blog.cache.container;

import java.util.List;
import java.util.stream.Collectors;

import zx.blog.cache.Cache;
import zx.blog.cache.impl.local.LocalCache;
import zx.blog.category.domain.Category;
import zx.blog.category.dto.CategoryConvert;
import zx.blog.category.dto.SimpleCategoryDto;
import zx.blog.user.domain.User;

public class CacheManager{
	
	/*用户缓存    key:用户ID -> value:用户*/
	private static Cache<Integer, User> userCache = new LocalCache<Integer, User>();
	/* 用户名 对应 的用户ID*/
	private static Cache<String, Integer> userName2Id = new LocalCache<String, Integer>();
	
	/*文章类别缓存   key:类别ID -> value:类别*/
	private static Cache<Integer, Category> categoryCache = new LocalCache<Integer, Category>();
	/* 类别名称 对应 的用户ID*/
	private static Cache<String, Integer> categoryName2Id = new LocalCache<String, Integer>();
	/**
	 * 缓存一个玩家
	 * @param user
	 */
	public static void addUser(User user){
		userCache.add(user.getUserId(), user);
		userName2Id.add(user.getUserName(), user.getUserId());
	}
	
	/**
	 * 根据用户ID查找
	 * @param userId
	 * @return
	 */
	public static User getUserById(Integer userId){
		return userCache.get(userId);
	}
	/**
	 * 根据用户名称查找
	 * @param name
	 * @return
	 */
	public static User getUserByName(String name){
		Integer userId = userName2Id.get(name);
		return userId == null ? null : getUserById(userId);
	}
	
	/**
	 * 缓存一个类别
	 * @param category
	 */
	public static void addCategory(Category category){
		categoryCache.add(category.getCategoryId(), category);
		categoryName2Id.add(category.getCategoryName(), category.getCategoryId());
	}
	
	/**
	 * 根据移除类别缓存
	 * @param categoryId
	 */
	public static void removeCategoryById(int categoryId) {
		categoryName2Id.remove(categoryCache.get(categoryId).getCategoryName());
		categoryCache.remove(categoryId);
	}

	
	/**
	 * 根据类别ID查找
	 * @param categoryId
	 * @return
	 */
	public static Category getCagetoryById(Integer categoryId){
		return categoryCache.get(categoryId);
	}
	
	/**
	 * 获取类别集合
	 * @return
	 */
	public static List<Category> getCategoryList(){
		return categoryCache.getList();
	}
	
	/**
	 * 获取类别集合 简单对象
	 * @return
	 */
	public static List<SimpleCategoryDto> getSimpleCategoryDtoList() {
		return getCategoryList().stream().map(CategoryConvert::convertToSimpleCategoryDto).collect(Collectors.toList());
	}

	/**
	 * 判断是否存在该缓存
	 * @param categoryName
	 * @return
	 */
	public static boolean isExistCategory(String categoryName) {
		return categoryName2Id.get(categoryName) != null;
	}

}