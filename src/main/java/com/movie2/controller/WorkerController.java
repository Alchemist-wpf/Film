package com.movie2.controller;

import com.movie2.model.entity.Activity;
import com.movie2.model.entity.Worker;
import com.movie2.service.ActivityService;
import com.movie2.service.ArrangementService;
import com.movie2.service.CartService;
import com.movie2.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
@CrossOrigin
@Slf4j
public class WorkerController {
    @Autowired
    WorkerService workerService;//客服
    @Autowired
    ArrangementService arrangementService;//排片
    @Autowired
    ActivityService activityService;//活动
    @GetMapping("")
    public List<Worker> getAllActivity(){
        log.info("获得所有的活动信息：ActivityController---》getAllActivity");
        List<Worker> list = new ArrayList<>();
        list = workerService.selectAllWorkers();
        return list;
    }

}
