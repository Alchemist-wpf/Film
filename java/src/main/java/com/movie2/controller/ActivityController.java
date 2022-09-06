package com.movie2.controller;

import com.movie2.model.entity.Activity;
import com.movie2.model.entity.Arrangement;
import com.movie2.model.entity.Cart;
import com.movie2.model.entity.Film;
import com.movie2.service.ActivityService;
import com.movie2.service.ArrangementService;
import com.movie2.service.CartService;
import com.movie2.service.FilmService;
import com.sun.org.glassfish.gmbal.ParameterNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activity")
@CrossOrigin
@Slf4j
public class ActivityController {
    @Autowired
    CartService cartService;//购物车
    @Autowired
    ArrangementService arrangementService;//排片
    @Autowired
    ActivityService activityService;//活动
    @GetMapping("")
    public List<Activity> getAllActivity(){
        log.info("获得所有的活动信息：ActivityController---》getAllActivity");
        List<Activity> list = new ArrayList<>();
        list = activityService.selectAllActivitys();
        return list;
    }

    @PostMapping("")
    public Map<String,Object> AddActivity(@RequestBody Activity activity){
        log.info("获得所有的活动信息：ActivityController---》AddActivity");
        Map<String,Object> map = new HashMap<>();
        int count = activityService.insert(activity);
        if(count==1){
            map.put("message","ok");
        }
        return map;
    }

    @PostMapping("/delete")
    public Map<String,Object> DelActivity(@RequestBody Activity activity){
        log.info("获得所有的活动信息：ActivityController---》DelActivity");
        Map<String,Object> map = new HashMap<>();
        int count = activityService.deleteByPrimaryKey(activity.getId());
        if(count==1){
            map.put("message","ok");
        }
        return map;
    }


}
