package zx.blog.search.lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.wltea.analyzer.lucene.IKAnalyzer;

import zx.blog.article.domain.Article;
import zx.blog.mapper.ArticleMapper;

@Component
public class SearchLogic implements ServletContextAware{
	@Autowired
	private ArticleMapper articleDao;
	Analyzer analyzer = new IKAnalyzer();
	private String path = "";

	@SuppressWarnings("unused")
	private void createArticleIndex(){
		long startTime = System.currentTimeMillis();
		Directory directory = null;
		IndexWriter indexWriter = null;
		List<Article> articles = articleDao.selectAll();
		try{
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer);
			File indexDir = new File(this.path);
			if(!indexDir.exists())
				indexDir.mkdir();
			directory = FSDirectory.open(indexDir);
			indexWriter = new IndexWriter(directory, indexWriterConfig);
			//删除之前的所有索引
			indexWriter.deleteAll();
			for(Article article : articles){
				Document document = new Document();
				document.add(new IntField("id", article.getArticleId(), Store.YES));
				document.add(new TextField("title", article.getTitle(), Store.YES));
				String content = article.getContent();
				content = content.replaceAll("<pre class=\"brush:\\w*; \\w* \" data-pbcklang=\"brush:\\w*; \\w*\" data-pbcktabsize=\"\\d\">", "<pre>");
				document.add(new TextField("content", content, Store.YES));
				//document.add(new TextField("authorName", article.getCategoryName(), Store.YES));
				//document.add(new TextField("categoryName", article.getCategoryName(), Store.YES));
				indexWriter.addDocument(document); 
			}
		} catch(Exception e){
			e.printStackTrace();
		}finally{
			if(indexWriter != null)
				try {
					indexWriter.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(directory != null)
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("索引创建完毕，总共花费时间：" + (endTime - startTime) + "豪秒");
	}
	
	/**
	 * 查看分词器是如何把一个词语拆分的
	 */
	public void splitWord(String text, boolean isMaxWordLength){
		System.out.println("分词开始............");
		//分词器对象
		Analyzer analyzer = new IKAnalyzer(isMaxWordLength);
		StringReader reader = new StringReader(text);
		
		//分词
		TokenStream ts = null;
		try {
			ts = analyzer.tokenStream("", reader);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
		
		//遍历分词数据
		System.out.println("IKAnalyzer 把关键字"+ text +"拆分的结果是：");
		try {
			ts.reset();
			while(ts.incrementToken()){
				System.out.println("【"+term.toString()+"】");
			}
			ts.end();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			reader.close();
		}
		System.out.println("分词结束............");
	}
	

	@SuppressWarnings("deprecation")
	public List<Article> search(String keyWord){
		long startTime = System.currentTimeMillis();
		System.out.println("********查询搜索结果开始*******");
		List<Article> adticleDtoList = new ArrayList<Article>();
		IndexReader indexReader = null;
		IndexSearcher indexSearcher = null;
		try{
			indexReader = DirectoryReader.open(FSDirectory.open(new File(this.path)));
			//创建搜索类
			indexSearcher = new IndexSearcher(indexReader);
			//创建QueryParser 查询解析器
			//QueryParser 支持单个字段的查询，MultiFieldQueryParser支持多个字段查询，可以实现全文检索的功能
			QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_4_10_0, new String[]{"title", "content", "authorName", "categoryName"}, analyzer);
			//利用queryParser来解析传递过来的检索关键字，完成Query对象的封装
			Query query = parser.parse(keyWord);
			//显示分词信息
			this.splitWord(keyWord, true);
			//执行检索操作
			TopDocs topDocs = indexSearcher.search(query, indexReader.maxDoc());
			System.out.println("一共查询到"+ topDocs.totalHits +"条记录");
			//高亮显示搜索结果（关键字）
			SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<span style=\"color:red;background:#FFFF66;\">", "</span>");
			Highlighter highlighter = new Highlighter(simpleHtmlFormatter, new QueryScorer(query));
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			
			for(ScoreDoc scoreDoc : scoreDocs){
				int docId = scoreDoc.doc;
				Document document = indexSearcher.doc(docId);
				Article dto = new Article();
				int id = Integer.valueOf(document.get("id"));
				String title = document.get("title");
				String content = document.get("content");
				String authorName = document.get("categoryName");
				String categoryName = document.get("categoryName");
				
				TokenStream tokenStream = null;
				if(title != null && !"".equals(title)){
					tokenStream = analyzer.tokenStream("title", new StringReader(title));
					title = highlighter.getBestFragment(tokenStream, title);
				}
				
				if(content != null && !"".equals(content)){
					//content = content.replaceAll("<pre class=\"brush:\\w*; \\w* \" data-pbcklang=\"brush:\\w*; \\w*\" data-pbcktabsize=\"\\d\">", "<pre>");
					//System.out.println("content1:" + content);
					tokenStream = analyzer.tokenStream("content", new StringReader(content));
					highlighter.setTextFragmenter(new SimpleFragmenter(content.length()));
					content = highlighter.getBestFragment(tokenStream, content);
					//System.out.println("content2:" + content);
				}

				if(authorName != null && !"".equals(authorName)){
					tokenStream = analyzer.tokenStream("categoryName", new StringReader(authorName));
					authorName = highlighter.getBestFragment(tokenStream, authorName);
				}
				
				if(categoryName != null && !"".equals(categoryName)){
					tokenStream = analyzer.tokenStream("categoryName", new StringReader(categoryName));
					categoryName = highlighter.getBestFragment(tokenStream, categoryName);
				}
				dto.setArticleId(id);
				//高亮操作后，如果没有要显示的高亮内容，则会返回一个null
				dto.setTitle(title != null ? title : document.get("title"));
				dto.setContent(content != null ? content : document.get("content"));
				//dto.setCategoryName(categoryName != null ? categoryName : document.get("categoryName"));
				adticleDtoList.add(dto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				indexReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("全文检索完毕，总共花费时间：" + (endTime - startTime) + "豪秒");
		return adticleDtoList;
	}
	
	@SuppressWarnings("unused")
	private void openIndex(){
		Directory directory = null;
		IndexReader reader = null;
		try{
			directory = FSDirectory.open(new File(this.path));
			reader = DirectoryReader.open(directory); 
			System.out.println("一共找到了"+reader.maxDoc()+"条记录");
			for(int i = 0;i < reader.maxDoc(); i++){
				Document doc = reader.document(i);
				System.out.println("title:"+doc.get("title"));
				System.out.println("content:"+doc.get("content"));
				System.out.println("categoryName:"+doc.get("categoryName"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(reader != null)
				try {
					reader.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(directory != null)
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 添加索引
	 * @param article
	 */
	public void addIndex(Article article){
		IndexWriter writer = null;
		Directory directory = null;
		
		try {
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer);
			directory = FSDirectory.open(new File(this.path));
			writer = new IndexWriter(directory, iwc);
			Document doc = new Document();
			doc.add(new IntField("id", article.getArticleId(), Store.YES));
			doc.add(new TextField("title", article.getTitle(), Store.YES));
			String content = article.getContent();
			content = content.replaceAll("<pre class=\"brush:\\w*; \\w* \" data-pbcklang=\"brush:\\w*; \\w*\" data-pbcktabsize=\"\\d\">", "<pre>");
			doc.add(new TextField("content", content, Store.YES));
			//doc.add(new TextField("authorName", article.getCategoryName(), Store.YES));
			//doc.add(new TextField("categoryName", article.getCategoryName(), Store.YES));
			writer.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null)
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(directory != null)
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 更新索引
	 */
	public void updateIndex(Article article){
		//TODO
	}
	
	/**
	 * 删除索引
	 */
	public void deleteIndex(Article article){
		//TODO
	}
	
	/**
	 * 删除全部索引
	 */
	public void deleteAllIndex(){
		//TODO
	}
	
	@PostConstruct
	public void init(){
		//this.createArticleIndex();
		//this.openIndex();
	}

	@Override
	public void setServletContext(ServletContext servletCtx) { 
		this.path = servletCtx.getRealPath("indexKey");
	}
	
	public static void main(String args[]){
		String str = "<pre class=\"brush:java; java \" data-pbcklang=\"brush:java; java\" data-pbcktabsize=\"4\">";
		String str2 = str.replaceAll("<pre class=\"brush:\\w*; \\w* \" data-pbcklang=\"brush:\\w*; \\w*\" data-pbcktabsize=\"\\d\">" , "<pre>");
		System.out.println(str2);
	}
}
