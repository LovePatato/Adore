package com.nkl.admin.domain;

import java.util.Date;

import com.nkl.common.domain.BaseDomain;
import com.nkl.common.util.DateUtil;

public class User extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;
	private Integer user_id; // 
	private String user_name; // 
	private String user_pass; // 
	private String real_name; // 
	private Integer user_sex; // 1：男  2：女
	private Integer user_age; // 
	private Clazz clazz; // 
	private Date reg_date; //  
	private Integer user_type; // 1：学生 2：教师 3：系统管理员 
	private String note; // 

	private int course_id; // 
	private int score_year; // 
	private int score_year_half; // 
	
	private String ids;
	private String random;

	
	public String getUser_typeDesc(){
		switch (user_type) {
		case 1:
			return "学生";
		case 2:
			return "教师";
		case 3:
			return "系统管理员 ";
		default:
			return "学生";
		}
	}
	
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
	
	public String getReg_dateDesc() {
		try {
			return DateUtil.dateToDateString(reg_date);
		} catch (Exception e) {
			return "";
		}
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_pass() {
		return user_pass;
	}

	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public Integer getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(Integer user_sex) {
		this.user_sex = user_sex;
	}

	public Integer getUser_age() {
		return user_age;
	}

	public void setUser_age(Integer user_age) {
		this.user_age = user_age;
	}

	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
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

}
