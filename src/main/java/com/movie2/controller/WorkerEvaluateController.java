package com.movie2.controller;

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
    public List<WorkerEvaluate> getMyAllWorkerEvaluate(@RequestParam("uid") int uid){
        log.info("WorkerEvaluateController----------->getMyAllWorkerEvaluate(@RequestParam(\"uid\") int uid)");
        List<WorkerEvaluate> list = new ArrayList<>();
        WorkerEvaluate temp = new WorkerEvaluate();
        temp.setUid(uid);
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

}
