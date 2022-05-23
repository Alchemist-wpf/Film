package com.movie2.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poster implements Serializable {
     private Integer id;
     private Integer fid;
     private String title;
     private String url;
     private Integer status;
     private Date createTime;
     private Date updateTime;
}
