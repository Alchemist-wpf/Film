package com.movie2.service;

import com.movie2.model.entity.Poster;

import java.util.List;

public interface PosterService {
    //获得轮播图
    List<Poster> getAllPoster();

    List<Poster> getPoster();

    int updatePoster(Integer id);

    public int deleteByPrimaryKey(Integer id);

    public int updateByPrimaryKey(Poster poster);

    //添加一个轮播图广告
    int insertPoster(Poster poster);

    int deleteAllPoster();
}
