package com.nkl.admin.domain;

import com.nkl.common.domain.BaseDomain;

public class Score extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 5376026488216840230L;
	private Integer score_id; // 
	private User user; // 
	private Course course; // 
	private Double score_value; // 
	private Integer score_year; // 
	private Integer score_year_half; // 1-上半年 2-下半年
	private String note; // 

	private String real_name; // 
	private int user_sex; // 
	private String clazz_name; // 
	
	private double score_valueMin; 
	private double score_valueMax; 
	private int teacher_id; // 
	
	private double sec_sum; // 
	
	private String ids;
	private String random;
	
	public String getUser_sexDesc(){
		switch (user_sex) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "男";
		}
	}
	
	public String getScore_year_halfDesc(){
		switch (score_year_half) {
		case 1:
			return "上半年";
		case 2:
			return "下半年";
		default:
			return "";
		}
	}

	public Integer getScore_id() {
		return score_id;
	}

	public void setScore_id(Integer score_id) {
		this.score_id = score_id;
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

	public Double getScore_value() {
		return score_value;
	}

	public void setScore_value(Double score_value) {
		this.score_value = score_value;
	}

	public Integer getScore_year() {
		return score_year;
	}

	public void setScore_year(Integer score_year) {
		this.score_year = score_year;
	}

	public Integer getScore_year_half() {
		return score_year_half;
	}

	public void setScore_year_half(Integer score_year_half) {
		this.score_year_half = score_year_half;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getScore_valueMin() {
		return score_valueMin;
	}

	public void setScore_valueMin(double score_valueMin) {
		this.score_valueMin = score_valueMin;
	}

	public double getScore_valueMax() {
		return score_valueMax;
	}

	public void setScore_valueMax(double score_valueMax) {
		this.score_valueMax = score_valueMax;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public double getSec_sum() {
		return sec_sum;
	}

	public void setSec_sum(double sec_sum) {
		this.sec_sum = sec_sum;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public int getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(int user_sex) {
		this.user_sex = user_sex;
	}

	public String getClazz_name() {
		return clazz_name;
	}

	public void setClazz_name(String clazz_name) {
		this.clazz_name = clazz_name;
	}
	

}
