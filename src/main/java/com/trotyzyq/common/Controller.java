package com.trotyzyq.common;

import com.trotyzyq.common.util.RandomUtil;
import com.trotyzyq.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/rest")
    public Object rest(){
        int i = RandomUtil.nextInt(10);
        redisUtil.set("test",i);
        redisUtil.get("test");
        return redisUtil.get("test");
    }
}
