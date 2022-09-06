package com.movie2.controller;

import com.movie2.config.MyConstants;
import com.movie2.model.entity.Activity;
import com.movie2.model.entity.Cart;
import com.movie2.model.entity.User;
import com.movie2.model.entity.Worker;
import com.movie2.model.vo.LoginVo;
import com.movie2.model.vo.WorkerVo;
import com.movie2.service.ActivityService;
import com.movie2.service.ArrangementService;
import com.movie2.service.CartService;
import com.movie2.service.WorkerService;
import com.movie2.util.JwtTokenUtil;
import com.movie2.util.MD5Util;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<WorkerVo> getAllActivity(){
        log.info("获得所有的活动信息：ActivityController---》getAllActivity");
//        {"id": "3344",
//                "username": "shixinyu",
//                "password":
//            "$2a$10$NYp9CGE1Co/J3SemIFALn.9df7KJOMNJmdFx4DW.2toNTJ1IEc2ni",
//                    "nickname": "史新雨",
//                "gender": "女",
//                "phone": "15678900989",
//                "avatar": "https://s2.loli.net/2022/03/31/pti2yc4UHMEFjmh.jpg",
//                "entry": false,
//                "department": "客服部",
//                "createAt": "2022-01-14 11:15:48",
//                "updateAt": "2022-01-14 11:16:00",
//                "dialogVisible": false,
//                "dialogVisible2": false,
//        },
        List<WorkerVo> result = new ArrayList<>();
        List<Worker> list = workerService.selectAllWorkers();
        for (Worker worker : list) {
            WorkerVo vo = new WorkerVo();
            vo.setId(worker.getId());
            vo.setAvatar(worker.getAvatar());
            vo.setCreateAt(worker.getCreateTime());
            vo.setDepartment(worker.getDepartment());
            vo.setGender(worker.getGender());
            vo.setNickname(worker.getNickname());
            vo.setPassword(worker.getPassword());
            vo.setPhone(worker.getPhone());
            vo.setUpdateAt(worker.getUpdateTime());
            vo.setUsername(worker.getUsername());
            result.add(vo);
        }
        return result;
    }

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Worker worker){
        log.info("WorkerController---------------->login(@RequestBody Worker worker)");
        Map<String,Object> map = new HashMap<>();
        Worker worker1 = new Worker();
        worker1.setUsername(worker.getUsername());
        List<Worker> workers  = workerService.selectWorkersByCondition(worker1);
        if(workers==null  || workers.size()==0){
            map.put("msg","用户名或密码错误");
            map.put("success","fail");
        }else{
            Worker result = workers.get(0);
            //用户是否已经禁用，
            if(worker.getPassword().equalsIgnoreCase(result.getPassword())){
                map.put("msg","登录成功");
                map.put("success","success");
                List<String> list = new ArrayList<>();
                list.add("user");
                String token=JwtTokenUtil.createToken(result.getUsername(),list,JwtTokenUtil.EXPIRATION_TIME);
                map.put("token",token);
                map.put("worker",result);
            }else{
                map.put("msg","用户名或密码错误");
                map.put("success","fail");
            }
        }
        return  map;
    }

    @GetMapping("/logout")
    public Map<String,Object> logout(@RequestParam("wid") int wid){
        log.info("WorkerController---------------->logout()");
        Worker result = workerService.selectByPrimaryKey(wid);//也可以不查
        List<String> list = new ArrayList<>();
        String token=JwtTokenUtil.createToken(result.getUsername(),list,30*1000);
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return map;
    }

    @GetMapping("/id")
    public Worker getWorkerId(@RequestParam("id") int id){
        log.info("UserController---------------->getUserId(@RequestBody LoginVo vo)");
        Worker worker = workerService.selectByPrimaryKey(id);
        worker.setAvatar(MyConstants.MY_URL+worker.getAvatar());
        return  worker;
    }

    @PostMapping("/modify")
    public Map<String,Object> modify(@RequestBody Worker worker) {
        log.info("WorkerController---------------->modify()");
        //修改
        workerService.updateByPrimaryKeySelective(worker);//通过主键修改其他非空的字段
        Map<String,Object> map = new HashMap<>();
        map.put("token","ok");
        return map;
    }

    @PostMapping("/delete")
    public Map<String,Object> deleteWorker(@RequestBody Worker worker){
        log.info("WorkerController---------------->deleteWorker(@RequestBody Worker worker)");
        int count = workerService.deleteByPrimaryKey(worker.getId());
        Map<String,Object> map = new HashMap<>();
        map.put("success","success");
        return map;
    }

    @PostMapping("/add")
    public Map<String,Object> addWorker(@RequestBody Worker worker){
        log.info("加入新员工：WorkerController    --------->  addWorker(@RequestBody Worker worker) ");
        Map<String,Object> map =new HashMap<>();
        Worker worker1 = new Worker();
        worker1.setUsername(worker.getUsername());
        List<Worker> workers = workerService.selectWorkersByCondition(worker1);
        if(workers==null || workers.size()==0){
            //没有找到，就可以注册
            map.put("msg","用户名不存在，可以添加");
            map.put("success","success");
            int count = workerService.insert(worker);
        }else{
            map.put("msg","用户名存在，不可以添加");
            map.put("success","fail");
        }
        return map;
    }
}

