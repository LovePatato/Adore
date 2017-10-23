package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.Course;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class CourseDao extends BaseDao{

	public void addCourse(Course course){
		super.add(course);
	}

	public void delCourse(Integer course_id){
		super.del(Course.class, course_id);
	}

	public void delCourses(String[] course_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <course_ids.length; i++) {
			sBuilder.append(course_ids[i]);
			if (i !=course_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Course WHERE course_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);	
	}

	public void updateCourse(Course course){
		Course _course = (Course)super.get(Course.class, course.getCourse_id());
		if (!StringUtil.isEmptyString(course.getCourse_name())) {
			_course.setCourse_name(course.getCourse_name());
		}
		if (!StringUtil.isEmptyString(course.getNote())) {
			_course.setNote(course.getNote());
		}
	}

	@SuppressWarnings("rawtypes")
	public Course getCourse(Course course){
		Course _course=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Course WHERE 1=1");
		if (course.getCourse_id()!=null && course.getCourse_id()!=0) {
			sBuilder.append(" and course_id = " + course.getCourse_id() +" ");
		}

		List list = super.executeQueryHql(sBuilder.toString(), null);
		if (list != null && list.size() > 0) {
			 _course = (Course)list.get(0);
		}
		return _course;
	}

	@SuppressWarnings("rawtypes")
	public List<Course>  listCourses(Course course){
		List<Course> courses = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Course WHERE 1=1");

		if (course.getCourse_id()!=null && course.getCourse_id()!=0) {
			sBuilder.append(" and course_id = " + course.getCourse_id() +" ");
		}
		if (!StringUtil.isEmptyString(course.getCourse_name())) {
			sBuilder.append(" and course_name like '%" + course.getCourse_name() +"%' ");
		}
		if (course.getUser_id()!=0) {
			sBuilder.append(" and course_id in (select p.course.course_id from Plan p where p.user.user_id = " + course.getUser_id() 
					+" and p.plan_year=" + course.getScore_year()+" and p.plan_year_half=" + course.getScore_year_half() +") ");
		}
		
		sBuilder.append(" order by course_id asc");

		List list = null;
		if (course.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), null, course.getStart(), course.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), null);
		}
		if (list != null && list.size() > 0) {
			courses = new ArrayList<Course>();
			for (Object object : list) {
				courses.add((Course)object);
			}
		}
		return courses;
	}

	public int  listCoursesCount(Course course){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Course WHERE 1=1");

		if (course.getCourse_id()!=null && course.getCourse_id()!=0) {
			sBuilder.append(" and course_id = " + course.getCourse_id() +" ");
		}
		if (!StringUtil.isEmptyString(course.getCourse_name())) {
			sBuilder.append(" and course_name like '%" + course.getCourse_name() +"%' ");
		}
		if (course.getUser_id()!=0) {
			sBuilder.append(" and course_id in (select p.course.course_id from Plan p where p.user.user_id = " + course.getUser_id() 
					+" and p.plan_year=" + course.getScore_year()+" and p.plan_year_half=" + course.getScore_year_half() +") ");
		}

		long count = (Long)super.executeQueryCountHql(sBuilder.toString(), null);
		sum = (int)count;
		return sum;
	}

}
