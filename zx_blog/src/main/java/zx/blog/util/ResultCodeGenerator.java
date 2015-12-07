package zx.blog.util;

import zx.blog.result.ResultCode;

/**
 * 生成 js 用的错误码 json 串
 * @author Administrator
 *
 */
public class ResultCodeGenerator
{
	public static void main(String args[]){
		ResultCode[] resultCodeArr = ResultCode.values();
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append("ResultCode = {\n");
		
		for(int i = 0; i < resultCodeArr.length; i++){
			ResultCode resultCode = resultCodeArr[i];
			strBuilder.append("\t" + resultCode.getCode() + ":" + "\"" +resultCode.getContent() + "\"");
			if(i < resultCodeArr.length - 1){
				strBuilder.append(",\n");
			}
		}
			
		strBuilder.append("\n}");
		System.out.println(strBuilder.toString());
	}
}