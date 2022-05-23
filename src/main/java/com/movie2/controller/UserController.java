package com.movie2.controller;

import com.movie2.config.MyConstants;
import com.movie2.model.entity.User;
import com.movie2.model.vo.EmailVo;
import com.movie2.model.vo.LoginVo;
import com.movie2.service.UserService;
import com.movie2.util.JwtTokenUtil;
import com.movie2.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
    //验证用户名是否存在
    @PostMapping("/isExist")
    public Map<String,String> isExist(@RequestBody EmailVo vo){
        log.info("UserController---------------->isExist(EmailVo vo)");
        Map<String,String> map = new HashMap<>();
        User user = new User();
        user.setUsername(vo.getEmail());//只放置登录名
        List<User> users = userService.selectUsersByCondition(user);
        if(users==null || users.size()==0){
            //没有找到，就可以注册
            map.put("msg","用户名不存在，可以注册");
            map.put("success","success");
        }else{
            map.put("msg","用户名存在，不可以注册");
            map.put("success","fail");
        }
        return  map;

    }

    //注册功能
    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody LoginVo vo){
        log.info("UserController---------------->register(@RequestBody LoginVo vo)");
        Map<String,Object> map = new HashMap<>();
        String token = vo.getToken();
        //获得token中加密的验证码
        String code = JwtTokenUtil.getUserName(token);
        if(code.equalsIgnoreCase(vo.getEmailCode())){
            User user = new User();
            user.setUsername(vo.getUsername());
            user.setPassword(vo.getPassword());
            user.setEmail(vo.getEmail());
            user.setAvatar("/images/7.jpg");//默认头像
            user.setNickname("宝宝");//默认昵称
            int insert = userService.insert(user);
            if(insert==1){
                map.put("msg","注册成功");
            }else{
                map.put("msg","注册失败");
            }
        }else{
            map.put("msg","验证码错误");
        }

        return  map;
    }

    //登录功能
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody LoginVo vo){
        log.info("UserController---------------->login(@RequestBody LoginVo vo)");
        Map<String,Object> map = new HashMap<>();
        //获得token中加密的验证码
        User user = new User();
        user.setUsername(vo.getUsername());
        List<User> users  = userService.selectUsersByCondition(user);
        if(users==null  || users.size()==0){
            map.put("msg","用户名或密码错误");
            map.put("success","fail");
        }else{
            User result = users.get(0);
            //用户是否已经禁用，
            if(result.getStatus()==1){
                map.put("msg","该用户已经被禁用，请联系管理员");
                map.put("success","fail");
            }else{
                //加密密码，判断密码是否匹配
                String newPass = MD5Util.md5To32String(vo.getPassword(),result.getSlat(),result.getSlatIndex());
                if(newPass.equalsIgnoreCase(result.getPassword())){
                    //登录成功
                    result.setErrorNum(0);
                    result.setStatus(0);
                    map.put("msg","登录成功");
                    map.put("success","success");
                    List<String> list = new ArrayList<>();
                    list.add("user");
                    String token=JwtTokenUtil.createToken(result.getUsername(),list,JwtTokenUtil.EXPIRATION_TIME);
                    map.put("token",token);
                    map.put("user",result);
                }else{
                    //密码加密后，与数据库中存储的不一致
                    result.setErrorNum(result.getErrorNum()+1);
                    map.put("msg","用户名或密码错误");
                    map.put("success","fail");
                    if(result.getErrorNum()>=5){
                        //锁定账户
                        result.setStatus(1);
                        map.put("msg","用户输入密码连续5次错误，该账户被禁用，请联系管理员");
                        map.put("success","fail");
                    }
                }
                userService.updateByPrimaryKey(result);
            }
        }

        return  map;
    }

    //注册功能
    @GetMapping("/id")
    public User getUserId(@RequestParam("id") int id){
        log.info("UserController---------------->getUserId(@RequestBody LoginVo vo)");
        User user = userService.selectByPrimaryKey(id);
        user.setAvatar(MyConstants.MY_URL+user.getAvatar());
        return  user;
    }
}

