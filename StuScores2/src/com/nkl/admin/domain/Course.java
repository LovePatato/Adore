package com.nkl.admin.domain;

import com.nkl.common.domain.BaseDomain;

public class Course extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 7424393039239963354L;
	private Integer course_id; // 
	private String course_name; // 
	private String note; // 

	private int user_id; // 
	private int score_year; // 
	private int score_year_half; // 
	
	private String ids;
	private String random;

	public void setCourse_id(Integer course_id){
		this.course_id=course_id;
	}

	public Integer getCourse_id(){
		return course_id;
	}

	public void setCourse_name(String course_name){
		this.course_name=course_name;
	}

	public String getCourse_name(){
		return course_name;
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

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getScore_year() {
		return score_year;
	}

	public void setScore_year(int score_year) {
		this.score_year = score_year;
	}

	public int getScore_year_half() {
		return score_year_half;
	}

	public void setScore_year_half(int score_year_half) {
		this.score_year_half = score_year_half;
	}

}
