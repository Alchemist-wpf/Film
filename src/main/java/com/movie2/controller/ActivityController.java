package com.movie2.controller;

import com.movie2.model.entity.Activity;
import com.movie2.model.entity.Arrangement;
import com.movie2.model.entity.Cart;
import com.movie2.model.entity.Film;
import com.movie2.service.ActivityService;
import com.movie2.service.ArrangementService;
import com.movie2.service.CartService;
import com.movie2.service.FilmService;
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

}
