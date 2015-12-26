package zx.blog.util;

import java.util.HashMap;
import java.util.Map;

import zx.blog.system.SystemContext;

public class PageUtils {
	/**
	 * 根据页面拿到要取出的数目信息
	 * @param pageNum 当前页
	 * @return
	 */
	public static Map<String, Integer> getNextArticlePage(int pageNum){
		int totalArticleNum = SystemContext.articleTotalNum.get();
		int totalPage = getTotalArticlePage(totalArticleNum);
		if(pageNum < 1) 
			pageNum = 1;
		if(pageNum > totalPage)
			pageNum = totalPage;
		Map<String, Integer> map = new HashMap<String, Integer>();
		int start, preEnd, end;
		if(pageNum <= 1)
			start = 0;
		else 
			start = (pageNum - 1) * SystemContext.articlePrePageNum;
		preEnd = start + SystemContext.articlePrePageNum - 1;
		end = preEnd < totalArticleNum ? SystemContext.articlePrePageNum : SystemContext.articlePrePageNum - (preEnd - totalArticleNum + 1);
		map.put("start", start);
		map.put("end", end);
		return map;
	}
	
	/**
	 * 取得页面信息
	 * @param pageNum
	 * @return
	 */
	public static Map<String, Integer> getPageInfo(int pageNum){
		int totalArticleNum = SystemContext.articleTotalNum.get();
		int totalPage = getTotalArticlePage(totalArticleNum);
		int start, end ;
		Map<String, Integer> pageInfo = new HashMap<String, Integer>();
		// 不足SystemContext.pageListNum 页
		if(totalPage < SystemContext.pageListNum){
			start = 1;
			end = totalPage;
		} else {
			if(pageNum > totalPage) {
				pageNum = totalPage;
				start = totalPage - SystemContext.pageListNum + 1;
				end = totalPage;
			} else if(pageNum < 1){
				pageNum = 1;
				start = 1;
				end = SystemContext.pageListNum;
			} else {
				start = pageNum - (SystemContext.pageListNum - 1) / 2 ; 
				end = pageNum + (SystemContext.pageListNum - 1) / 2 ; 
				if (start < 1){
					start = 1;
					end = SystemContext.pageListNum;
				} else {
					if(end > totalPage){
						end = totalPage;
						start = totalPage - SystemContext.pageListNum + 1;
					}
				}
			}
		}
		//开始页面
		pageInfo.put("start", start);
		//结束页面
		pageInfo.put("end", end);
		//当前页面
		pageInfo.put("currentPage", pageNum);
		pageInfo.put("totalPage", totalPage);
		return pageInfo;
	}
	
	/**
	 * 取得文章的总页面数目
	 * @return
	 */
	public static int getTotalArticlePage(int totalArticleNum){
		if(totalArticleNum <= 0)
			return 0;
		return (int) Math.ceil((double)totalArticleNum/SystemContext.articlePrePageNum);
	}
}
