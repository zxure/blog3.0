package org.springframework.web.servlet.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class RequestMappingOperator implements ApplicationContextAware {
	
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	public void test(){
		System.out.println(this.requestMappingHandlerMapping);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		WebApplicationContext webApplicationContext = (WebApplicationContext)applicationContext;
	
		this.requestMappingHandlerMapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);	
	}

}
