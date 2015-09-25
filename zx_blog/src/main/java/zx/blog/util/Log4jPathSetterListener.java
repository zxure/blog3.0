package zx.blog.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Log4jPathSetterListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		InputStream ins = Log4jPathSetterListener.class.getClassLoader().getResourceAsStream("props/system.properties");
		try {
			Properties prop = new Properties();
			prop.load(ins);
			System.setProperty("log4jdir", prop.getProperty("log4jdir", "D://log").trim());
			prop.clear();
			prop = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
