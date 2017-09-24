package com.mxcx.erp.te;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Client {

	private static final DefaultListableBeanFactory beanFactory;  
    static {  
        beanFactory = new DefaultListableBeanFactory();  
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);  
        Resource resource = new ClassPathResource("spring-application.xml");  
        beanDefinitionReader.loadBeanDefinitions(resource);  
    }  
    public static void main(String[] args) {  
        SayHelloService1 sayHelloService = beanFactory.getBean("sayHelloService1", SayHelloService1.class);  
        String ss=sayHelloService.getHello("王五");  
        System.out.println("-----------------"+ss);
    }  
	
}
