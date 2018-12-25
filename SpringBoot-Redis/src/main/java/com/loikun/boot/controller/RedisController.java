package com.loikun.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping(name = "/get1", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object getData() {
        redisTemplate.opsForValue().set("key1", "value1");
        stringRedisTemplate.opsForValue().set("init_key", "1");
        stringRedisTemplate.opsForValue().increment("ini_key", 1);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    /**
     * Spring操作链表
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object testList() {
        //插入两个链表，注意链表的顺序
        ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
        opsForList.leftPushAll("list1", "v2", "v4", "v6", "v8", "v10");
        opsForList.rightPushAll("list2", "v1", "v2", "v3", "v4", "v5");
        // 绑定list2链表的操作
        BoundListOperations<String, String> listOps = stringRedisTemplate.boundListOps("list2");
        // 从右边弹出一个成员
        String result1 = listOps.rightPop();
        // 获取定位元素，Redis 是从下标为0开始计算，这里的值为v2， 查询的是list2
        String result2 = listOps.index(1);
        // 左边再插入链表
        listOps.leftPush("v0");
        //求出链表长度
        Long size = listOps.size();
        //求链表下标区间成员，整个链表下标范围为0 到 size-1, 这里不取最后一个元素。
        List<String> range = listOps.range(0, size - 2);
        Map<String, Object> map = new HashMap<>();
        map.put("result1", result1);
        map.put("result2", result2);
        map.put("range", range);
        map.put("success", true);
        return map;
    }

    /**
     * redis 的集合操作
     * @return
     */
    @RequestMapping("/set")
    @ResponseBody
    public Object testSet(){
        // 集合是不允许出现重复的，所以只保存一个
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
        opsForSet.add("set1","v1","v2","v3","v3","v4","v5");
        opsForSet.add("set2","v2", "v4", "v6", "v8", "v10");
        // 指定对set1 进行操作
        BoundSetOperations<String, String> setOps = stringRedisTemplate.boundSetOps("set1");
        // 向键为set1的集合添加两个元素
        setOps.add("v6","v7");
        // 返回所有元素
        Set<String> sets = setOps.members();
        // 获取集合的元素个数
        Long size = setOps.size();
        // 获取 set1 和 set2 的交集
        Set<String> intersect = setOps.intersect("set2");
        // 求出交集，并且用新的集合 inter 保存
        setOps.intersectAndStore("set2","inter");
        // 求差集
        Set<String> diff = setOps.diff("set2");
        //求差集并且用新集合 diff 保存
        setOps.diffAndStore("set2","diff");
        // 求出并集
        Set<String> union = setOps.union("set2");
        //求并集并且用新集合 union 保存
        setOps.unionAndStore("set2","union");
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    /**
     * 实现有序集合
     * @return
     */
    @RequestMapping("/zset")
    @ResponseBody
    public Object testZset(){
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = new HashSet<>();
        for (int i = 1; i < 9 ; i++) {
            double score = i *0.1;
            DefaultTypedTuple<String> typedTuple = new DefaultTypedTuple<>("value" + i, score);
            typedTupleSet.add(typedTuple);
        }
        ZSetOperations<String, String> opsForZSet = stringRedisTemplate.opsForZSet();
        // 往有序集合里面添加元素
        opsForZSet.add("zset1",typedTupleSet);
        BoundZSetOperations<String, String> zSetOps = stringRedisTemplate.boundZSetOps("zset1");
        zSetOps.add("value10",0.26);
        // 按分数排序获取有序集合
        Set<String> setRange = zSetOps.range(1, 6);
        // 定义值范围
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gt("value3");

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }




}
