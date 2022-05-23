package com.movie2.mapper;

import com.movie2.model.entity.Poster;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PosterMapper {

    @Select("select * from t_poster where  status = 1")
    List<Poster> getAllPoster();

    @Insert("insert into t_poster values(null,#{fid},#{title},#{url},#{status},now(),now())")
    int insertPoster(Poster poster);
}
