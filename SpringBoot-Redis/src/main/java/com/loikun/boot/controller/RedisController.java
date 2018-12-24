package com.loikun.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping(name = "/get1", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getData() {
        redisTemplate.opsForValue().set("key1","value1");
        stringRedisTemplate.opsForValue().set("init_key", "1");
        stringRedisTemplate.opsForValue().increment("ini_key",1);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }
}
