package zx.blog.util;

public class FileUtils {
	/**
	 * 文件操作
	 * @param command 命令 
	 * @see Command
	 * @param args 命令参数
	 */
	public static void execute(Command command, String... args){
		if(command == Command.touch){
			touch(args);
		} else if(command == Command.mkdir){
			mkdir(args);
		} else if(command == Command.cp){
			cp(args);
		} else if(command == Command.mv){
			mv(args);
		} else if(command == Command.rm){
			rm(args);
		}
	}
	
	//创建文件
	public static void touch(String... args){
		
	}
	
	//创建文件夹
	public static void mkdir(String... args){
		
	}
	
	//复制文件
	public static void cp(String... args){
		
	}
	
	//移动文件
	public static void mv(String... args){
		
	}
	
	//删除文件
	public static void rm(String... args){
		
	}

	
	/** 命令类型 */
	static enum Command{
		touch(),
		mkdir(),
		cp(),
		mv(),
		rm();
	}
}
