package zx.blog.article.utils;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import zx.blog.article.utils.htmlTag.Pre;

/**
 * 文章 摘要生成
 * @author Administrator
 *
 */

public class ArticleBriefTools {
	public static final int DEFAULT_LENGTH = 1000;
	public static String getBrief(String content, int maxSize){
		if(StringUtils.isBlank(content))
			return "";
		if(maxSize <= 0)
			maxSize = DEFAULT_LENGTH;
		int currentLength = 0;
		StringBuilder result = new StringBuilder();
		//content = getMaxLengthContent(content, maxSize);
		
		Parser parser = Parser.createParser(content, "utf-8");
		//注册 pre 标签
		PrototypicalNodeFactory factory = (PrototypicalNodeFactory)(parser.getNodeFactory());
		factory.registerTag(new Pre());
		NodeList nodes = null;
		try {
			nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
				private static final long serialVersionUID = 4476668746854748093L;

				@Override
				public boolean accept(Node node) {
					if(node instanceof CompositeTag)
						return true;
					return false;
				}
			});
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < nodes.size(); i++){
			Node node = nodes.elementAt(i);
			//result.append(node.toHtml());
			String nodeStr = node.toPlainTextString();
			int byteLength = nodeStr.getBytes(Charset.forName("gbk")).length;
			if(currentLength + byteLength >= maxSize){
				for(int j = 0; j < nodeStr.length(); j++){
					 String code = nodeStr.substring(j, j + 1);
					 currentLength = currentLength + code.getBytes(Charset.forName("gbk")).length;
					 if(currentLength >= maxSize){
						 result.append(code);
						 return result.toString() + "...";
					 }else{
						 result.append(code);
					 }
				}
			}else{
				result.append(nodeStr);
				currentLength = currentLength + byteLength;
			} 
		}
		return result.toString() + "...";
	}
	
/*	private static String getMaxLengthContent(String content, int maxSize){
		int pos = 0,len = 0,count = 0;
		String s = "";
		StringBuffer sb = new StringBuffer();
		while(true){
			if(count > maxSize)
			    break;
			if(pos >= content.length())
				return sb.toString();
			s = content.substring(pos, pos+1);
			if(s.equals("<")){
				len = content.indexOf(">", pos)-pos;
				for(int i=0;i<len;i++){
					s = content.substring(pos+i, pos+i+1);
					sb.append(s);
				}
				pos += len;
			} else {
				if(count <= maxSize) {
				    if(s.equals(">")){
				    	sb.append(s);
				        pos++;
				    }else{
				    	sb.append(s);
				    	count++;
				    	pos++;
				   	}
			    }
		     }
		}
		sb.append(" ..........");
		return sb.toString();
	}*/
}
