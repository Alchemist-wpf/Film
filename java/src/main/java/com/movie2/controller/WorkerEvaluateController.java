package com.movie2.controller;

import com.movie2.config.MyConstants;
import com.movie2.model.entity.Activity;
import com.movie2.model.entity.LeavingMessage;
import com.movie2.model.entity.User;
import com.movie2.model.entity.WorkerEvaluate;
import com.movie2.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class WorkerEvaluateController {
    @Autowired
    WorkerEvaluateService workerEvaluateService;//对客服评价
    @Autowired
    LeavingMessageService leavingMessageService;

    @Autowired
    UserService userService;

    @GetMapping("/we")//我的评论
    public List<WorkerEvaluate> getMyAllWorkerEvaluate(@RequestParam("uid") int uid,
                                                       @RequestParam("wid") int wid){
        log.info("WorkerEvaluateController----------->getMyAllWorkerEvaluate(@RequestParam(\"uid\") int uid,\n" +
                "                                                       @RequestParam(\"wid\") int wid)");
        List<WorkerEvaluate> list = new ArrayList<>();
        WorkerEvaluate temp = new WorkerEvaluate();
        temp.setUid(uid);
        temp.setWid(wid);
        list = workerEvaluateService.selectWorkerEvaluatesByCondition(temp);
        return  list;
    }

    @GetMapping("/me")//我的评论
    public List<WorkerEvaluate> getMeAllWorkerEvaluate(@RequestParam("wid") int wid){
        log.info("WorkerEvaluateController----------->getMyAllWorkerEvaluate(@RequestParam(\"wid\") int uid)");
        List<WorkerEvaluate> list = new ArrayList<>();
        WorkerEvaluate temp = new WorkerEvaluate();
        temp.setWid(wid);
        list = workerEvaluateService.selectWorkerEvaluatesByCondition(temp);
        return  list;
    }
    @GetMapping("/lm")//所有评论
    public List<Map<String,Object>> getAllWorkerEvaluate(){
        log.info("WorkerEvaluateController----------->getAllWorkerEvaluate()");
        List<Map<String,Object>> result = new ArrayList<>();
        List<LeavingMessage> list = leavingMessageService.selectAllLeavingMessages();
        for (LeavingMessage leavingMessage : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",leavingMessage.getId());

            map.put("leavingMessage",leavingMessage);
            User user =userService.selectByPrimaryKey(leavingMessage.getUid());
            map.put("user",user);
            result.add(map);
        }
        return  result;
    }

    @PostMapping("/addEvaluate")//所有评论
    public Map<String,Object> addEvaluate(@RequestBody WorkerEvaluate evaluate){
        Map<String,Object> map = new HashMap<>();
        map.put("success","ok");
        int insert = workerEvaluateService.insert(evaluate);
        return map;
    }

    @PostMapping("/addMyEvaluate")//所有评论
    public Map<String,Object> addMyEvaluate(@RequestBody LeavingMessage leavingMessage){
        Map<String,Object> map = new HashMap<>();
        map.put("success","ok");
        int insert = leavingMessageService.insert(leavingMessage);
        return map;
    }

    @PostMapping("/reply")
    public Map<String,Object> addReply(@RequestBody LeavingMessage leavingMessage){
        Map<String,Object> map = new HashMap<>();
        map.put("success","ok");
        int update = leavingMessageService.updateByPrimaryKey(leavingMessage);
        return map;
    }
}
