package com.stu.imp;

import com.stu.api.GreetingService;

/**
 * @author N_J
 */
public class GreetingServiceImp implements GreetingService {

    @Override
    public String sayHello(String name) {
        return " hello  "+name;
    }
}
