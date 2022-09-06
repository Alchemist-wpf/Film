package com.movie2.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 东软-java-张绍鹏
 * addr: 江苏南京
 * tel:137 7663 2257 张老师  （微信同号）
 * @user: Administrator
 * @date: 2022/5/18 - 9:25
 * @Description: 简单描述一下Java类
 * projectName:movie2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerVo implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private String nickname;
    private String phone;
    private Boolean entry=true;
    private String gender;
    private String department;
    private Date createAt;
    private Date updateAt;
    private Boolean dialogVisible=false;
    private Boolean dialogVisible2=false;
}
