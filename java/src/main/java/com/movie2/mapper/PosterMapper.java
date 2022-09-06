package com.movie2.mapper;

import com.movie2.model.entity.Poster;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PosterMapper {

    @Select("select * from t_poster where status = 1")
    List<Poster> getPoster();

    @Select("select * from t_poster")
    List<Poster> getAllPoster();

    @Update("update set status = #{status} from t_poster where id = #{id}")
    int updatePoster(Integer id);

    @Insert("insert into t_poster values(null,#{fid},#{title},#{url},#{status},now(),now())")
    int insertPoster(Poster poster);

    @Delete("delete from t_poster")
    int deleteAllPoster();

    public int deleteByPrimaryKey(Integer id);

    public int updateByPrimaryKey(Poster poster);
}
