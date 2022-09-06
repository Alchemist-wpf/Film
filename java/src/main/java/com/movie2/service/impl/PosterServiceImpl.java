package com.movie2.service.impl;

import com.movie2.config.MyConstants;
import com.movie2.mapper.PosterMapper;
import com.movie2.model.entity.Poster;
import com.movie2.service.PosterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PosterServiceImpl implements PosterService {
    @Autowired
    PosterMapper posterMapper;
    @Override
    public List<Poster> getAllPoster() {
        log.info("PosterServiceImpl--------------------------------------->getAllPoster()");
        List<Poster> allPoster = posterMapper.getAllPoster();
        for (Poster poster : allPoster) {
            poster.setUrl(MyConstants.MY_URL+poster.getUrl());
        }
        return allPoster;
    }

    @Override
    public List<Poster> getPoster() {
        log.info("PosterServiceImpl--------------------------------------->getAllPoster()");
        List<Poster> allPoster = posterMapper.getPoster();
        for (Poster poster : allPoster) {
            poster.setUrl(MyConstants.MY_URL+poster.getUrl());
        }
        return allPoster;
    }

    @Override
    public int updatePoster(Integer id){
        log.info("主键ID更新Poster信息:deleteByPrimaryKey() ");
        return posterMapper.updatePoster(id);
    }

    @Override
    public int updateByPrimaryKey(Poster poster) {
        log.info("主键ID更新Poster信息:deleteByPrimaryKey() ");
        return posterMapper.updateByPrimaryKey(poster);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        log.info("主键ID删除Poster信息:deleteByPrimaryKey() ");
        return posterMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertPoster(Poster poster) {
        log.info("PosterServiceImpl--------------------------------------->insertPoster(Poster poster)");
        return posterMapper.insertPoster(poster);
    }

    @Override
    public int deleteAllPoster() {
        log.info("PosterServiceImpl--------------------------------------->deleteAllPoster()");
        return posterMapper.deleteAllPoster();
    }
}
