package com.stu;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.stu.api.GreetingService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author N_J
 */
public class Consumer {
    public static void main(String[] args) {
        providerByXml();
//        providerByAPI();
    }

    private static void providerByXml() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:consumer.xml");
        context.start();
        for(int i =0 ;i<10;i++) {
            // 获取远程服务代理
            GreetingService demoService = (GreetingService) context.getBean("demoService");
            // 执行远程方法
            String hello = demoService.sayHello("world");
            System.out.println( hello );
        }
    }

    private static void providerByAPI() {

        ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<>();
        // 设置服务提供方的 url
        referenceConfig.setUrl("dubbo://192.168.93.1:20880/com.stu.api.GreetingService");

        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        RegistryConfig registryConfig = new RegistryConfig("multicast://224.5.6.7:1234");

        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(GreetingService.class);
        referenceConfig.setVersion("0.0.1");
        GreetingService greetingService = referenceConfig.get();
        System.out.println(greetingService.sayHello("world"));
    }


}
