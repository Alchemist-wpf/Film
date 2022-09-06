package com.movie2.model.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class FilmEvaluate implements Serializable {

	/****/
	private String id;

	/****/
	private String fid;

	/****/
	private String uid;

	/****/
	private Integer star;

	/****/
	private String comment;

	/****/
	private Date createAt;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setFid(String fid){
		this.fid = fid;
	}

	public String getFid(){
		return this.fid;
	}

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return this.uid;
	}

	public void setStar(Integer star){
		this.star = star;
	}

	public Integer getStar(){
		return this.star;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return this.comment;
	}

	public void setCreateAt(Date createAt){
		this.createAt = createAt;
	}

	public Date getCreateAt(){
		return this.createAt;
	}

	@Override
	public String toString() {
		return "FilmEvaluate [id=" + id + ", fid=" + fid + ", uid=" + uid + ", star=" + star + ", comment=" + comment + ", createAt=" + createAt+ "]";
	}
}
