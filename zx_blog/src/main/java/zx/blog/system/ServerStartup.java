package zx.blog.system;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.article.service.ArticleService;


@Component
public class ServerStartup{
	@Autowired
	private ArticleService articleService;
	@PostConstruct
	public void startUp(){
		//载入系统配置文件
		SystemContext.load();
		//载入文章总数
		SystemContext.articleTotalNum = new AtomicInteger(articleService.getArticleTotalNum());
	}
}