package com.example.feignclient.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    @Autowired
    EurekaClientFeign eurekaClientFeign;
    public String sayHello(){
        return eurekaClientFeign.sayHelloFromOrderService();
    }
}