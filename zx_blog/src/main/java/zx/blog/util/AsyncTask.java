package zx.blog.util;

import java.util.List;

import zx.blog.article.domain.Article;

/**
 * 异步任务
 * @author zxuan
 *
 */
public class AsyncTask {
	/**
	 * 缓存文章评论和回复
	 * @param articleId
	 * @return
	 */
	public static Runnable getCacheCommentForArticleTask(final int articleId){
		Runnable task = new Runnable(){
			public void run(){
				
			}
		};
		return task;
	}
	
	/**
	 * 缓存一篇文章
	 * @param articleId
	 * @return
	 */
	public static Runnable getCacheArticleByIdTask(final int articleId){
		Runnable task = new Runnable(){
			public void run(){
			}
		};
		return task;
	}
	/**
	 * 缓存一篇文章
	 * @param article
	 * @return
	 */
	public static Runnable getCacheArticleTask(final Article article){
		Runnable task = new Runnable(){
			public void run(){
			}
		};
		return task;
	}
	
	/**
	 * 根据文章ID列表 缓存文章
	 * @param articleIds
	 * @return
	 */
	public static Runnable getCacheArticleByIdListTask(final List<Integer> articleIds){
		Runnable task = new Runnable(){
			public void run(){
			}
		};
		return task;
	}
}
