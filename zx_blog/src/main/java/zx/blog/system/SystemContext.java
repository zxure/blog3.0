package zx.blog.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
public class SystemContext {
	//异步执行线程池
	public static ExecutorService asyncTaskHandler = Executors.newScheduledThreadPool(10);
	
	//文章总数
	public static AtomicInteger articleTotalNum;
	
	private static Properties prop = new Properties();
	/**分页信息显几页 1,2,3,4,5*/
	public static int pageListNum;
	/**每一页显示的文章数目*/
	public static int articlePrePageNum;
	/**每页要显示的留言数目*/
	public static int commentPrePageNum;
	
	/**
	 * 载入配置文件
	 */
	public static void load() {
		InputStream ins = SystemContext.class.getClassLoader().getResourceAsStream("props/system.properties");
		try {
			prop.load(ins);
			articlePrePageNum = Integer.parseInt(prop.getProperty("articlePrePageNum", "5").trim());
			commentPrePageNum = Integer.parseInt(prop.getProperty("commentPrePageNum", "5").trim());
			pageListNum = Integer.parseInt(prop.getProperty("pageListNum", "5").trim());
			if(pageListNum % 2 == 0){//如果为偶数，则变为基数
				pageListNum = pageListNum + 1;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			prop.clear();
			prop = null;
			try {
				ins.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**重新加载*/
	public boolean reload(){
		load();
		return true;
	}
	
	
	
}

