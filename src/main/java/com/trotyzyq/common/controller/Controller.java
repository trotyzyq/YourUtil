package com.trotyzyq.common.controller;

import com.trotyzyq.common.util.RandomUtil;
import com.trotyzyq.common.util.RedisUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class Controller implements InitializingBean {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/rest")
    public Object rest(){
        System.out.println(1/0);
        int i = RandomUtil.nextInt(10);
        redisUtil.set("test",i);
        redisUtil.get("test");
        return redisUtil.get("test");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(11);
    }

    @PostConstruct
    public void post(){
        System.out.println("post");
    }
}
