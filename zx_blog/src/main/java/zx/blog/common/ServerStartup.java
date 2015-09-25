package zx.blog.common;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.article.service.ArticleService;
import zx.blog.cache.container.CacheHolder;
import zx.blog.category.domain.Category;
import zx.blog.category.service.CategoryService;
import zx.blog.user.domain.User;
import zx.blog.user.service.UserService;


@Component
public class ServerStartup{
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ArticleService articleService;
	@PostConstruct
	public void startUp(){
		//载入系统配置文件
		SystemContext.load();
		//载入文章总数
		SystemContext.articleTotalNum = new AtomicInteger(articleService.getArticleTotalNum());
		//初始化缓存
		this.initCache();
	}
	
	@PreDestroy
	public void destroy(){
		System.out.println("服务器关闭了...."); 
	}
	
	//初始化缓存
	private void initCache(){
		//用户缓存
		List<User> allUser = userService.findAllUser();
		for(User user : allUser){
			CacheHolder.addUser(user);
		}
		
		//文章类别缓存
		List<Category> allCategory = categoryService.findAllCategory();
		for(Category category : allCategory){
			CacheHolder.addCategory(category);
		}
	}
}
