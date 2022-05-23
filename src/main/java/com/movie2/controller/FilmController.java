package com.movie2.controller;

import com.movie2.config.MyConstants;
import com.movie2.model.entity.Arrangement;
import com.movie2.model.entity.Film;
import com.movie2.model.entity.Poster;
import com.movie2.model.entity.Seat;
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
import java.io.UnsupportedEncodingException;
import java.util.*;

// /api/poster  get
@RestController
@CrossOrigin
@Slf4j
public class FilmController {

    @Autowired
    SeatService seatService;
    @Autowired
    FilmService filmService;
    @Autowired
    ArrangementService arrangementService;
    //   /api/film/hot/limit
    @GetMapping("/api/film/hot/limit")
    public List<Film> getAllFilmsHot(){
        log.info("FilmController-> getAllFilmsHot() ");
        return filmService.getFilmsHot(5);
    }

    @GetMapping("/api/film")
    public List<Film> getAllFilms(@RequestParam(name = "region",required = false,defaultValue = "全部")String region,
                                  @RequestParam(name = "type",required = false,defaultValue = "全部")String type){
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

    @GetMapping("/filmdetail/detail")
    public Film getFilmById(@RequestParam("fid") int fid){
        log.info("FilmController->getFilmById(@RequestParam(fid) int fid) ");
        return filmService.selectByPrimaryKey(fid);
    }

    //获得场次信息
    @GetMapping("/api/arrangement/film/fid")
    public List<Arrangement> getArrangementById(@RequestParam("fid") int fid){
        log.info("FilmController->getArrangementById(@RequestParam(fid) int fid) ");
        System.out.println("fid:"+fid);
        Arrangement temp = new Arrangement();
        temp.setFid(fid);
        temp.setDate(new Date());
        return arrangementService.selectArrangementsByCondition(temp);
    }

    @GetMapping("/api/film/id")
    public Film getFilmById2(@RequestParam("fid") int fid){
        log.info("FilmController->getFilmById2(@RequestParam(fid) int fid) ");
        Film film = filmService.selectByPrimaryKey(fid);
        film.setCover(MyConstants.MY_URL+film.getCover());

        return film;
    }
    // 进入特定的场次，获得座位信息，电影信息等（通过排片Id获得）
    // /api/arrangement/id
    @GetMapping("/api/arrangement/id")
    public Map<String,Object> getArrangementAndSeatById(@RequestParam("arrangementId") int id){
        log.info("FilmController->getArrangementAndSeatById(@RequestParam(arrangementId) int fid) ");
        System.out.println("arrangementId:"+id);
        Map<String,Object> map = new HashMap<>();
        //获得排片信息
        Arrangement arrangement = arrangementService.selectByPrimaryKey(id);
        //通过fId获得电影信息
        Film film = filmService.selectByPrimaryKey(arrangement.getFid());
        map.put("film",film);
        map.put("arrangement",arrangement);
        return map;
    }
    //查询电影
    // /api/film/name
    @GetMapping("/api/film/name")
    public List<Film> getFilmsByName(@RequestParam("name") String name) throws UnsupportedEncodingException {
        log.info("FilmController->getFilmsByName(@RequestParam(name) String name) ");
        System.out.println(name);
        //name = new String(name.getBytes("iso-8859-1"),"utf-8");
        Film  f= new Film();
        f.setName(name);//模糊查询
        List<Film> films = filmService.selectFilmsByCondition(f);
        return films;
    }
    //api/arrangement/getSeats 获得已经定过座位信息
    @GetMapping("/api/arrangement/getSeats")
    public List<Seat> getSeats(@RequestParam("arrangementId") int id){
        log.info("FilmController->getSeats(@RequestParam(arrangementId) int fid) ");
        System.out.println("arrangementId:"+id);
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat();
        seat.setAid(id);
        seats = seatService.selectSeatsByCondition(seat);
        return seats;
    }

}
