package com.movie2.controller;

import com.movie2.model.entity.*;
import com.movie2.model.vo.OrderVo;
import com.movie2.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
@Slf4j
public class OrderController {
    @Autowired
    ArrangementService arrangementService;
    @Autowired
    FilmService filmService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    @Autowired
    SeatService seatService;

    @PostMapping("/add")
    public Map<String,Object> add(@RequestBody OrderVo vo){
        // ids 所有的要付款的购物车的id，修改它的状态
        log.info("OrderController-------------> add(@RequestBody int[] ids)");
        //通过id获得购物车项，获得所有的座位信息
        System.out.println(vo.getIds());
        List<Cart> carts = cartService.selectAllCartsByIds(vo.getIds());
        StringBuffer sb = new StringBuffer();
        int aid = 0;
        for (Cart cart : carts) {
            sb.append(cart.getSeats());
            sb.append(",");
            aid=cart.getAid();
            //0,1,37,36
        }
        String seats = sb.toString();
        seats  = seats.substring(0,seats.length()-1);
        //修改选中的购物车项，修改状态是2，用foreach

        Map<String,Object> map =new HashMap<>();
        map.put("status",2);
        map.put("array",vo.getIds());

        int count = cartService.updateStatusByIds(map);

        //添加一个订单项
        Order order = new Order();
        order.setAid(vo.getAid());
        order.setPhone(vo.getPhone());
        order.setPrice(vo.getPrice());
        order.setStatus(vo.getStatus());
        order.setUid(vo.getUid());
        order.setSeats(seats);
        order.setStatus(2);

        count=orderService.insert(order);

        Map<String,Object> result =new HashMap<>();
        List<Seat> seatList = new ArrayList<>();
        String[] mySeats = seats.split(",");
        for (String mySeat : mySeats) {
            Seat s = new Seat();
            s.setAid(aid);
            s.setSeat(Integer.parseInt(mySeat));
            seatList.add(s);
        }
        //https://blog.csdn.net/chang100111/article/details/115664432
        count = seatService.insertList(seatList);
        result.put("success","ok");
        return result;
    }


    //订单，用户，电影，排片
    // 通过用户id，获得用户,获得订单，获得排片，获得电影
    @GetMapping("/user/id")
    public List< Map<String,Object> > getMyOrder(@RequestParam("uid") int uid) {
        log.info("OrderController-------------> getMyOrder(@RequestParam(\"uid\") int uid)");
        List< Map<String,Object> > result = new ArrayList<>();
        // 通过用户id，获得订单，获得排片，获得电影
        //(1) 获得用户
        User user =userService.selectByPrimaryKey(uid);
        //(2)获得订单
        Order ordertemp = new Order();
        ordertemp.setUid(user.getId());
        List<Order> orders = orderService.selectOrdersByCondition(ordertemp);
        for (Order order : orders) {
            //获得排片
            Arrangement arrangement = arrangementService.selectByPrimaryKey(order.getAid());
            // 获得电影信息
            Film film = filmService.selectByPrimaryKey(arrangement.getFid());
            Map<String,Object> map = new HashMap<>();
            map.put("user",user);
            map.put("order",order);
            map.put("arrangement",arrangement);
            map.put("film",film);
            result.add(map);
        }

        return  result;
    }

    @GetMapping("")
    public List< Map<String,Object> > getAllOrder() {
        log.info("OrderController-------------> getAllOrder()");
        List< Map<String,Object> > result = new ArrayList<>();
        // 获得订单
        List<Order> orders = orderService.selectAllOrders();
        for (Order order : orders) {
            //获得排片
            Arrangement arrangement = arrangementService.selectByPrimaryKey(order.getAid());
            // 获得电影信息
            User user = userService.selectByPrimaryKey(order.getUid());
            Film film = filmService.selectByPrimaryKey(arrangement.getFid());
            Map<String,Object> map = new HashMap<>();
            map.put("user",user);
            map.put("order",order);
            map.put("arrangement",arrangement);
            map.put("film",film);
            result.add(map);
        }

        return  result;
    }

    @PostMapping("/update")
    public Map<String,Object> addReply(@RequestBody Order order){
        Map<String,Object> map = new HashMap<>();
        map.put("success","ok");
        int update = orderService.updateByPrimaryKey(order);
        return map;
    }

    @GetMapping("/error")
    public List< Map<String,Object> > getAllErrorOrder() {
        log.info("OrderController-------------> getAllErrorOrder()");
        List< Map<String,Object> > result = new ArrayList<>();
        // 获得订单
        List<Order> orders = orderService.selectAllOrders();
        for (Order order : orders) {
            //获得排片
            if(order.getStatus().equals(3)){
                Arrangement arrangement = arrangementService.selectByPrimaryKey(order.getAid());
                // 获得电影信息
                User user = userService.selectByPrimaryKey(order.getUid());
                Film film = filmService.selectByPrimaryKey(arrangement.getFid());
                Map<String,Object> map = new HashMap<>();
                map.put("user",user);
                map.put("order",order);
                map.put("arrangement",arrangement);
                map.put("film",film);
                result.add(map);
            }
        }

        return  result;
    }
}
