package com.movie2.controller;

import com.movie2.config.MyConstants;
import com.movie2.model.entity.*;
import com.movie2.model.vo.LoginVo;
import com.movie2.service.ArrangementService;
import com.movie2.service.FilmService;
import com.movie2.service.PosterService;
import com.movie2.service.SeatService;
import com.sun.jmx.remote.security.FileLoginModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

// /api/poster  get
@RestController
@RequestMapping("/api/film")
@CrossOrigin
@Slf4j
public class FilmController {

    @Autowired
    FilmService filmService;
    @Autowired
    ArrangementService arrangementService;
    @Autowired
    SeatService seatService;
    @GetMapping("")
    public List<Film> selectAllFilms(){
        return filmService.selectAllFilms();
    }

    @GetMapping("/hot/limit")
    public List<Film> getAllFilmsHot(){
        log.info("FilmController-> getAllFilms() ");
        return filmService.getFilmsHot(10);
    }

    @GetMapping("/id")
    public Film  getFilmById (@RequestParam("id") int id){
        log.info("通过主键查询Film信息:selectByPrimaryKey() ");
        Film film = filmService.selectByPrimaryKey(id);
        film.setCover(MyConstants.MY_URL+film.getCover());

        return film;
    }

    @GetMapping("/select")
    public List<Film> getAllFilms(@RequestParam(name = "region",required = false,defaultValue = "全部") String region,
                                  @RequestParam(name = "type",required = false,defaultValue = "全部") String type){
        log.info("FilmController-> getAllFilms() ");
        List<Film> films=null;
        if("全部".equalsIgnoreCase(region)  && "全部".equalsIgnoreCase(type)){
            films = filmService.selectAllFilms();
        }else if(!"全部".equalsIgnoreCase(region) && "全部".equalsIgnoreCase(type)) {
            films = filmService.selectAllFilms(region,null);
        } else if("全部".equalsIgnoreCase(region) && !"全部".equalsIgnoreCase(type)) {
            films = filmService.selectAllFilms(null,type);
        }else{
            films = filmService.selectAllFilms(region,type);
        }


        return films;
    }

    // /api/film/name
    @GetMapping("/name")
    public List<Film> getFilmsByName(@RequestParam("name") String name){
        log.info("FilmController->getFilmsByName(@RequestParam(\"name\") String name) ");
        Film  f= new Film();
        f.setName(name);//模糊查询
        List<Film> films = filmService.selectFilmsByCondition(f);
        return films;
    }

    //获得场次信息
    @GetMapping("/arrangement/fid")
    public List<Arrangement> getArrangementById(@RequestParam("fid") int fid){
        log.info("FilmController->getArrangementById(@RequestParam(fid) int fid) ");
        System.out.println("fid:"+fid);
        Arrangement temp = new Arrangement();
        temp.setFid(fid);
        temp.setDate(new String());
        return arrangementService.selectArrangementsByCondition(temp);
    }

    // 进入特定的场次，获得座位信息，电影信息等（通过排片Id获得）
    // /api/arrangement/id
    @GetMapping("/arrangement/id")
    public Map<String,Object> getArrangementAndSeatById(@RequestParam("arrangementId") int id){
        log.info("FilmController->getArrangementAndSeatById(@RequestParam(arrangementId) int fid) ");
        System.out.println("id:"+id);
        Map<String,Object> map = new HashMap<>();
        //获得排片信息
        Arrangement arrangement = arrangementService.selectByPrimaryKey(id);
        //通过fId获得电影信息
        Film film = filmService.selectByPrimaryKey(arrangement.getFid());
        map.put("film",film);
        map.put("arrangement",arrangement);
          return map;
    }

    //api/arrangement/getSeats 获得已经定过座位信息
    @GetMapping("/arrangement/getSeats")
    public List<Seat> getSeats(@RequestParam("arrangementId") int id){
        log.info("FilmController->getSeats(@RequestParam(arrangementId) int fid) ");
        System.out.println("arrangementId:"+id);
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat();
        seat.setAid(id);
        seats = seatService.selectSeatsByCondition(seat);
        return seats;
    }

    @PostMapping("/add")
    public Map<String,Object> addFilm(@RequestBody Film film){
        log.info("加入新电影：FilmController    --------->  addFilm(@RequestBody Film film) ");
        //是否批量处理
        //https://blog.csdn.net/chang100111/article/details/115664432
        //这里使用是一个字符串添加到数据库中，如果是批量添加，需要在购物车中加入orderId字段
        Map<String,Object> map =new HashMap<>();
        int count = filmService.insert(film);
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }

    @GetMapping("/arrangement")
    public List<Arrangement> getArrangement(){
        log.info("FilmController->getArrangement() ");
        return arrangementService.selectAllArrangements();
    }

    @PostMapping("/arrangement/delete")
    public Map<String,Object> delArrangement(@RequestBody Arrangement arrangement){
        log.info("删除：ArrangementController    --------->  delArrangement(@RequestBody Arrangement arrangement) ");
        Map<String,Object> map =new HashMap<>();
        int count = arrangementService.deleteByPrimaryKey(arrangement.getId());
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }

    @PostMapping("/arrangement/update")
    public Map<String,Object> addArrangement(@RequestBody Arrangement arrangement){
        log.info("添加：ArrangementController    --------->  addArrangement(@RequestBody Arrangement arrangement) ");
        Map<String,Object> map =new HashMap<>();
        int count = arrangementService.updateByPrimaryKeySelective(arrangement);
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }

    @PostMapping("/delete")
    public Map<String,Object> delFilm(@RequestBody Film film){
        log.info("删除：FilmController    --------->  delArrangement(@RequestBody Film film) ");
        Map<String,Object> map =new HashMap<>();
        int count = filmService.deleteByPrimaryKey(film.getId());
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }

    @PostMapping("/arrangement/add")
    public Map<String,Object> updateArrangement(@RequestBody Arrangement arrangement){
        log.info("更新一：ArrangementController    --------->  updateArrangement(@RequestBody Arrangement arrangement) ");
        Map<String,Object> map =new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = arrangement.getStartTime();
        String d = simpleDateFormat.format(date);
        arrangement.setDate(d);
        int count = arrangementService.insert(arrangement);
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }

    @PostMapping("/update")
    public Map<String,Object> updateFilm(@RequestBody Film film){
        log.info("更改：FilmController    --------->  updateFilm(@RequestBody Film film) ");
        Map<String,Object> map =new HashMap<>();
        int count = filmService.updateByPrimaryKeySelective(film);
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }

    @PostMapping("/hot/update")
    public Map<String,Object> updateHot(@RequestBody Film film){
        log.info("更改：FilmController    --------->  updateHot(@RequestBody Film film) ");
        Map<String,Object> map =new HashMap<>();
        int count = filmService.updateByPrimaryKeySelective(film);
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }
}
