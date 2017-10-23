package com.nkl.admin.domain;

import com.nkl.common.domain.BaseDomain;

public class Plan extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -2314005237608205836L;
	private Integer plan_id; // 
	private Integer plan_year; // 
	private Integer plan_year_half; // 1-上半年 2-下半年
	private Clazz clazz; // 
	private Course course; // 
	private User user; // 
	private String note; // 

	private String ids;
	private String random;

	public String getPlan_year_halfDesc(){
		switch (plan_year_half) {
		case 1:
			return "上半年";
		case 2:
			return "下半年";
		default:
			return "";
		}
	}
	
	public void setPlan_id(Integer plan_id){
		this.plan_id=plan_id;
	}

	public Integer getPlan_id(){
		return plan_id;
	}

	public void setPlan_year(Integer plan_year){
		this.plan_year=plan_year;
	}

	public Integer getPlan_year(){
		return plan_year;
	}

	public void setPlan_year_half(Integer plan_year_half){
		this.plan_year_half=plan_year_half;
	}

	public Integer getPlan_year_half(){
		return plan_year_half;
	}

	public void setNote(String note){
		this.note=note;
	}

	public String getNote(){
		return note;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getIds() {
		return ids;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getRandom() {
		return random;
	}

	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
