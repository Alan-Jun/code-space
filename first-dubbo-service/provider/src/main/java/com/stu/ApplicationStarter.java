package com.stu;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.stu.api.GreetingService;
import com.stu.imp.GreetingServiceImp;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author N_J
 */
public class ApplicationStarter {

    public static void main(String[] args) throws IOException {
        providerByXML();
//        providerByAPI();
    }

    private static void providerByXML() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider.xml");
        context.start();
        // 按任意键退出
        System.in.read();
    }

    private static void providerByAPI() throws IOException {
        ServiceConfig<GreetingService> serviceConfig = new ServiceConfig<>();
        // 提供方应用信息，用于计算依赖关系
        ApplicationConfig applicationConfig = new ApplicationConfig("first-dubbo-provider");
        applicationConfig.setQosPort(33333);

        // 注册中心信息
        RegistryConfig registryConfig = new RegistryConfig("multicast://224.5.6.7:1234");

        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setInterface(GreetingService.class);
        serviceConfig.setRef(new GreetingServiceImp());
        serviceConfig.setVersion("0.0.1");
        serviceConfig.export();
        System.in.read();
    }


}
