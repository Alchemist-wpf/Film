package com.movie2.controller;

import com.movie2.model.entity.Poster;
import com.movie2.model.entity.Worker;
import com.movie2.service.PosterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// /api/poster  get
@RestController
@RequestMapping("/api/poster")
@CrossOrigin
@Slf4j
public class PosterController {

    @Autowired
    PosterService posterService;

    @GetMapping("")
    public List<Poster> getPoster(){
        log.info("PosterController-> getAllPoster() ");
        return posterService.getPoster();
    }

    @GetMapping("/all")
    public List<Poster> getAllPoster(){
        log.info("PosterController-> getAllPoster() ");
        return posterService.getAllPoster();
    }

    @Value("${zsp.poster.upload}")
    String upload;//获得本地目录
    @PostMapping("/upload")
    public Map<String,String> uploadPoster(
            @RequestParam(name="title",defaultValue = "电影标题")String title,
            @RequestParam(name="fid",defaultValue = "1")int fid
            , MultipartFile file){
        log.info("PosterController-> uploadPoster() ");
        Map<String,String> map = new HashMap<>();
        //保存到文件中
        File path = new File(upload);
        if(!path.exists()){
            path.mkdirs();//文件夹不存在，创建文件夹
        }
        //(2)文件重名，a.jpg,        a.jpg
        //(解决办法)uuid；当前时间.......
        //使用uuid，保留原来的文件名，文件的类型是一致，a.jpg   ==>   *.gif
        String originalFilename = file.getOriginalFilename();//获得原始的文件名 "a.jpg"
        int index=originalFilename.lastIndexOf(".");
        //originalFilename.substring(0,index) 获得原始文件的名字，没有扩展名
        //+originalFilename.substring(index) 获得文件扩展名
        String newName = originalFilename.substring(0,index)+"_"+ UUID.randomUUID().toString()+originalFilename.substring(index);
        //保存文件
        try {
            file.transferTo(new File(upload+newName));//不出现异常，就是文件上传成功
            //对数据库进行操作
            map.put("message","文件保存成功");
            Poster poster = new Poster();
            poster.setUrl("/images/"+newName);
            poster.setFid(fid);
            poster.setStatus(1);
            poster.setTitle(title);
            //保存到数据库中
            int count = posterService.insertPoster(poster);
            map.put("db","数据库保存成功");
        } catch (IOException e) {
            e.printStackTrace();//文件上传失败
            map.put("message","文件保存失败");
        }
        return map;
    }

    @PostMapping("/add")
    public Map<String,Object> addPoster(@RequestBody Poster poster){
        log.info("加入新轮播海报：PosterController    --------->  addPoster(@RequestBody Poster poster) ");
        Map<String,Object> map =new HashMap<>();
        int count = posterService.insertPoster(poster);
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }

    @PostMapping("/delete")
    public Map<String,Object> delPoster(@RequestBody Poster poster){
        log.info("删除一个轮播海报：PosterController    --------->  delPoster(@RequestBody Poster poster) ");
        Map<String,Object> map =new HashMap<>();
        int count = posterService.deleteByPrimaryKey(poster.getId());
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }

    @PostMapping("/update")
    public Map<String,Object> updatePoster(@RequestBody Poster poster){
        log.info("更新一个轮播海报：PosterController    --------->  updatePoster(@RequestBody Poster poster) ");
        Map<String,Object> map =new HashMap<>();
        int count = posterService.updateByPrimaryKey(poster);
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }


    @PostMapping("/all/delete")
    public Map<String,Object> delAllPoster(){
        log.info("删除所有轮播海报：PosterController    --------->  delAllPoster() ");
        Map<String,Object> map =new HashMap<>();
        int count = posterService.deleteAllPoster();
        if(count==1){
            map.put("success","ok");
        }
        return map;
    }
}
