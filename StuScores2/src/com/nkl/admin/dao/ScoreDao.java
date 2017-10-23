package com.nkl.admin.dao;

import com.nkl.admin.domain.Score;
import com.nkl.admin.domain.vo.QueryParam;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreDao extends BaseDao{

	public void addScore(Score score){
		super.add(score);
	}

	public void importScore(Score score){
		String hql = "insert into score(user_id,course_id,score_year,score_year_half,score_value) "
				   + "select t1.user_id,t2.course_id,"+score.getScore_year()+","+score.getScore_year_half()+","+score.getScore_value()+" from "
				   + "(select user_id from `user` u where u.user_name='"+score.getUser().getUser_name()+"') t1, "
				   + "(select course_id from course c where c.course_name='"+score.getCourse().getCourse_name()+"') t2";
		Object[] params = null;
		super.executeUpdateSql(hql, params);
	}
	
	public void delScore(Integer score_id){
		super.del(Score.class, score_id);
	}
	
	public void delScoreByStuId(String user_id){
		String hql = "DELETE FROM Score s WHERE s.user.user_id=?";

		Object[] params = new Object[] { new Integer(user_id)};
		super.executeUpdateHql(hql, params);
	}

	public void delScores(String[] score_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <score_ids.length; i++) {
			sBuilder.append(score_ids[i]);
			if (i !=score_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Score WHERE score_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);
	}

	public void updateScore(Score score){
		Score _score = (Score)super.get(Score.class, score.getScore_id());
		if (score.getScore_value()!=null && score.getScore_value()!=0) {
			_score.setScore_value(score.getScore_value());
		}
		if (score.getScore_year()!=null && score.getScore_year()!=0) {
			_score.setScore_year(score.getScore_year());
		}
		if (score.getScore_year_half()!=null && score.getScore_year_half()!=0) {
			_score.setScore_year_half(score.getScore_year_half());
		}
	}

	@SuppressWarnings("rawtypes")
	public Score getScore(Score score){
		Score _score=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("  FROM Score s ");
		sBuilder.append("  join fetch s.user ");
		sBuilder.append("  join fetch s.course ");
		sBuilder.append("  join fetch s.user.clazz ");
		sBuilder.append(" where 1=1 ");
		if (score.getScore_id()!=null && score.getScore_id()!=0) {
			sBuilder.append(" and s.score_id = " + score.getScore_id() +" ");
		}
		if (score.getUser()!=null && score.getUser().getUser_id()!=0) {
			sBuilder.append(" and s.user.user_id = " + score.getUser().getUser_id() +" ");
		}
		if (score.getCourse()!=null && score.getCourse().getCourse_id()!=0) {
			sBuilder.append(" and s.course.course_id = " + score.getCourse().getCourse_id() +" ");
		}
		if (score.getUser()!=null && score.getUser().getClazz()!=null && score.getUser().getClazz().getClazz_id()!=null && score.getUser().getClazz().getClazz_id()!=0) {
			sBuilder.append(" and s.user.clazz.clazz_id = " + score.getUser().getClazz().getClazz_id() +" ");
		}
		if (score.getScore_year()!=null && score.getScore_year()!=0) {
			sBuilder.append(" and s.score_year = " + score.getScore_year() +" ");
		}
		if (score.getScore_year_half()!=null && score.getScore_year_half()!=0) {
			sBuilder.append(" and s.score_year_half = " + score.getScore_year_half() +" ");
		}

		List list = super.executeQueryHql(sBuilder.toString(), null);
		if (list != null && list.size() > 0) {
			 _score = (Score)list.get(0);
		}
		return _score;
	}

	@SuppressWarnings("rawtypes")
	public List<Score>  listScores(Score score){
		List<Score> scores = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("  FROM Score s ");
		sBuilder.append("  join fetch s.user ");
		sBuilder.append("  join fetch s.course ");
		sBuilder.append("  join fetch s.user.clazz ");
		sBuilder.append(" where 1=1 ");

		if (score.getScore_id()!=null && score.getScore_id()!=0) {
			sBuilder.append(" and s.score_id = " + score.getScore_id() +" ");
		}
		if (score.getUser()!=null && score.getUser().getUser_id()!=null && score.getUser().getUser_id()!=0) {
			sBuilder.append(" and s.user.user_id = " + score.getUser().getUser_id() +" ");
		}
		if (score.getUser()!=null && !StringUtil.isEmptyString(score.getUser().getUser_name())) {
			sBuilder.append(" and s.user.user_name like '%" + score.getUser().getUser_name() +"%' ");
		}
		if (score.getUser()!=null && !StringUtil.isEmptyString(score.getUser().getReal_name())) {
			sBuilder.append(" and s.user.real_name like '%" + score.getUser().getReal_name() +"%' ");
		}
		if (score.getCourse()!=null && score.getCourse().getCourse_id()!=null && score.getCourse().getCourse_id()!=0) {
			sBuilder.append(" and s.course.course_id = " + score.getCourse().getCourse_id() +" ");
		}
		if (score.getUser()!=null && score.getUser().getClazz()!=null && score.getUser().getClazz().getClazz_id()!=null && score.getUser().getClazz().getClazz_id()!=0) {
			sBuilder.append(" and s.user.clazz.clazz_id = " + score.getUser().getClazz().getClazz_id() +" ");
		}
		if (score.getScore_year()!=null && score.getScore_year()!=0) {
			sBuilder.append(" and s.score_year = " + score.getScore_year() +" ");
		}
		if (score.getScore_year_half()!=null && score.getScore_year_half()!=0) {
			sBuilder.append(" and s.score_year_half = " + score.getScore_year_half() +" ");
		}
		if (score.getScore_valueMin()!=0) {
			sBuilder.append(" and s.score_value >= " + score.getScore_valueMin() +" ");
		}
		if (score.getScore_valueMax()!=0) {
			sBuilder.append(" and s.score_value <= " + score.getScore_valueMax() +" ");
		}
		if (score.getTeacher_id()!=0) {
			sBuilder.append(" and (s.course.course_id,s.score_year,s.score_year_half) in (select p.course.course_id,plan_year,plan_year_half from Plan p where p.user.user_id=" + score.getTeacher_id() +") ");
		}
		
		sBuilder.append(" order by score_value desc,score_id asc ");

		List list = null;
		if (score.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), null, score.getStart(), score.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), null);
		}
		if (list != null && list.size() > 0) {
			scores = new ArrayList<Score>();
			for (Object object : list) {
				scores.add((Score)object);
			}
		}
		return scores;
	}

	public int  listScoresCount(Score score){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Score s "); 
		sBuilder.append(" where 1=1 ");

		if (score.getScore_id()!=null && score.getScore_id()!=0) {
			sBuilder.append(" and s.score_id = " + score.getScore_id() +" ");
		}
		if (score.getUser()!=null && score.getUser().getUser_id()!=null && score.getUser().getUser_id()!=0) {
			sBuilder.append(" and s.user.user_id = " + score.getUser().getUser_id() +" ");
		}
		if (score.getUser()!=null && !StringUtil.isEmptyString(score.getUser().getUser_name())) {
			sBuilder.append(" and s.user.user_name like '%" + score.getUser().getUser_name() +"%' ");
		}
		if (score.getUser()!=null && !StringUtil.isEmptyString(score.getUser().getReal_name())) {
			sBuilder.append(" and s.user.real_name like '%" + score.getUser().getReal_name() +"%' ");
		}
		if (score.getCourse()!=null && score.getCourse().getCourse_id()!=null && score.getCourse().getCourse_id()!=0) {
			sBuilder.append(" and s.course.course_id = " + score.getCourse().getCourse_id() +" ");
		}
		if (score.getUser()!=null && score.getUser().getClazz()!=null && score.getUser().getClazz().getClazz_id()!=null && score.getUser().getClazz().getClazz_id()!=0) {
			sBuilder.append(" and s.user.clazz.clazz_id = " + score.getUser().getClazz().getClazz_id() +" ");
		}
		if (score.getScore_year()!=null && score.getScore_year()!=0) {
			sBuilder.append(" and s.score_year = " + score.getScore_year() +" ");
		}
		if (score.getScore_year_half()!=null && score.getScore_year_half()!=0) {
			sBuilder.append(" and s.score_year_half = " + score.getScore_year_half() +" ");
		}
		if (score.getScore_valueMin()!=0) {
			sBuilder.append(" and s.score_value >= " + score.getScore_valueMin() +" ");
		}
		if (score.getScore_valueMax()!=0) {
			sBuilder.append(" and s.score_value <= " + score.getScore_valueMax() +" ");
		}
		if (score.getTeacher_id()!=0) {
			sBuilder.append(" and (s.course.course_id,s.score_year,s.score_year_half) in (select p.course.course_id,plan_year,plan_year_half from Plan p where p.user.user_id=" + score.getTeacher_id() +") ");
		}
		
		long count = (Long)super.executeQueryCountHql(sBuilder.toString(), null);
		sum = (int)count;
		return sum;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Score>  listScoresSum(Score score){
		List<Score> scores = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT u.real_name,u.user_sex,c.clazz_name,s.score_year,s.score_year_half, ");
		sBuilder.append("  ROUND(sum(s.score_value),2) score_value FROM score s ");
		sBuilder.append("  join user u on s.user_id=u.user_id ");
		sBuilder.append("  join clazz c on c.clazz_id = u.clazz_id  ");
		sBuilder.append(" where 1=1 ");

		if (score.getUser()!=null && score.getUser().getUser_id()!=null &&score.getUser().getUser_id()!=0) {
			sBuilder.append(" and s.user_id = " + score.getUser().getUser_id() +" ");
		}
		if (score.getUser()!=null && !StringUtil.isEmptyString(score.getUser().getReal_name())) {
			sBuilder.append(" and u.real_name like '%" + score.getUser().getReal_name() +"%' ");
		}
		if (score.getCourse()!=null && score.getCourse().getCourse_id()!=0) {
			sBuilder.append(" and s.course_id = " + score.getCourse().getCourse_id() +" ");
		}
		if (score.getUser()!=null && score.getUser().getClazz()!=null && score.getUser().getClazz().getClazz_id()!=null && score.getUser().getClazz().getClazz_id()!=0) {
			sBuilder.append(" and u.clazz_id = " + score.getUser().getClazz().getClazz_id() +" ");
		}
		if (score.getScore_year()!=null && score.getScore_year()!=0) {
			sBuilder.append(" and s.score_year = " + score.getScore_year() +" ");
		}
		if (score.getScore_year_half()!=null && score.getScore_year_half()!=0) {
			sBuilder.append(" and s.score_year_half = " + score.getScore_year_half() +" ");
		}
		if (score.getTeacher_id()!=0) {
			sBuilder.append(" and (s.course_id,s.score_year,s.score_year_half) in (select p.course_id,plan_year,plan_year_half from Plan p where p.user_id=" + score.getTeacher_id() +") ");
		}
		sBuilder.append(" group by u.real_name,u.user_sex,c.clazz_name,s.score_year,s.score_year_half ");
		sBuilder.append(" order by s.score_year,s.score_year_half,u.real_name ");

		String[] scalars = {"real_name","user_sex","clazz_name","score_year","score_year_half","score_value"};
		Type[] types = {Hibernate.STRING,Hibernate.INTEGER,Hibernate.STRING,Hibernate.INTEGER,Hibernate.INTEGER,Hibernate.DOUBLE};

		List list = null;
		if (score.getStart()!=-1) {
			list = super.executeQueryJavaBeanSql(sBuilder.toString(), score.getClass(), null, scalars, types, score.getStart(), score.getLimit());
		}else {
			list = super.executeQueryJavaBeanSql(sBuilder.toString(), score.getClass(), null, scalars, types);
		}
		if (list != null && list.size() > 0) {
			scores = new ArrayList<Score>();
			for (Object object : list) {
				scores.add((Score)object);
			}
		}
		return scores;
	}

	@SuppressWarnings("rawtypes")
	public int  listScoresSumCount(Score score){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) s_count FROM (");
		sBuilder.append("SELECT count(*) s_count FROM Score s ");
		sBuilder.append("  join user u on s.user_id=u.user_id ");
		sBuilder.append("  join clazz c on c.clazz_id = u.clazz_id  ");
		sBuilder.append(" where 1=1 ");

		if (score.getUser()!=null && score.getUser().getUser_id()!=null &&score.getUser().getUser_id()!=0) {
			sBuilder.append(" and s.user_id = " + score.getUser().getUser_id() +" ");
		}
		if (score.getUser()!=null && !StringUtil.isEmptyString(score.getUser().getReal_name())) {
			sBuilder.append(" and u.real_name like '%" + score.getUser().getReal_name() +"%' ");
		}
		if (score.getCourse()!=null && score.getCourse().getCourse_id()!=0) {
			sBuilder.append(" and s.course_id = " + score.getCourse().getCourse_id() +" ");
		}
		if (score.getUser()!=null && score.getUser().getClazz()!=null && score.getUser().getClazz().getClazz_id()!=null && score.getUser().getClazz().getClazz_id()!=0) {
			sBuilder.append(" and u.clazz_id = " + score.getUser().getClazz().getClazz_id() +" ");
		}
		if (score.getScore_year()!=null && score.getScore_year()!=0) {
			sBuilder.append(" and s.score_year = " + score.getScore_year() +" ");
		}
		if (score.getScore_year_half()!=null && score.getScore_year_half()!=0) {
			sBuilder.append(" and s.score_year_half = " + score.getScore_year_half() +" ");
		}
		if (score.getTeacher_id()!=0) {
			sBuilder.append(" and (s.course_id,s.score_year,s.score_year_half) in (select p.course_id,plan_year,plan_year_half from Plan p where p.user_id=" + score.getTeacher_id() +") ");
		}
		
		sBuilder.append(" group by u.real_name,u.user_sex,c.clazz_name,s.score_year,s.score_year_half) t ");

		List list = super.executeQueryMapSql(sBuilder.toString(), null);
		if (list!=null) {
			Map map = (Map) list.get(0);
			sum = Integer.parseInt(map.get("s_count").toString());
		}
		return sum;
	}


	public List<Integer> listAllYears(){
		SQLQuery query = super.getSession().createSQLQuery("SELECT DISTINCT plan_year FROM plan ORDER BY plan_year ASC");
		List list = query.list();
		return list;
	}
	public List<Integer> listAllClasses(){
		SQLQuery query = super.getSession().createSQLQuery("SELECT DISTINCT clazz_id,clazz_name FROM clazz ORDER BY clazz_id ASC");
		List list = query.list();
		return list;
	}

	public List listAllCourses() {
		SQLQuery query = super.getSession().createSQLQuery("SELECT course_id,course_name FROM course ORDER BY course_id ASC");
		List list = query.list();
		return list;
	}
	public List listSingleRanks(QueryParam queryParam) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT \n" +
				"  user.`user_name`,\n" +
				"  user.`real_name`,\n" +
				"  clazz.`clazz_name`,\n" +
				"  course.`course_name`,\n" +
				"  score.`score_value`,\n" +
				"  score.`score_year`,\n" +
				"  score.`score_year_half` \n" +
				"FROM\n" +
				"  score \n" +
				"  INNER JOIN USER \n" +
				"    ON score.`user_id` = user.`user_id` \n" +
				"  INNER JOIN course \n" +
				"    ON score.`course_id` = course.`course_id` \n" +
				"  INNER JOIN clazz \n" +
				"    ON user.`clazz_id` = clazz.`clazz_id` ");
		sb.append("WHERE course.`course_id` = ").append(queryParam.getCourseId())
				.append(" ").append("AND score.`score_year` = ").append(queryParam.getYear())
				.append(" ").append("AND score.`score_year_half` = ").append(queryParam.getTerm())
				.append(" ").append("AND clazz.`clazz_id` = ").append(queryParam.getClassId());
		if (queryParam.getPageNum() != null && queryParam.getPageSize() != null){
			int offset = (queryParam.getPageNum() - 1) * queryParam.getPageSize();
			//成绩降序排列
			sb.append(" ORDER BY score.`score_value` DESC\n");
			sb.append(" ").append("LIMIT ").append(offset).append(",").append(queryParam.getPageSize());

		}
		System.out.println(sb.toString());
		SQLQuery query = super.getSession().createSQLQuery(sb.toString());
		List list = query.list();
		return list;
	}

	public Integer countRanks(QueryParam queryParam){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT count(*) \n" +
				"FROM\n" +
				"  score \n" +
				"  INNER JOIN USER \n" +
				"    ON score.`user_id` = user.`user_id` \n" +
				"  INNER JOIN course \n" +
				"    ON score.`course_id` = course.`course_id` \n" +
				"  INNER JOIN clazz \n" +
				"    ON user.`clazz_id` = clazz.`clazz_id` ");
		sb.append("WHERE course.`course_id` = ").append(queryParam.getCourseId())
				.append(" ").append("AND score.`score_year` = ").append(queryParam.getYear())
				.append(" ").append("AND score.`score_year_half` = ").append(queryParam.getTerm())
				.append(" ").append("AND clazz.`clazz_id` = ").append(queryParam.getClassId());
		SQLQuery query = super.getSession().createSQLQuery(sb.toString());
		//List result = query.list();
		super.getSession().close();
		return ((Number)query.uniqueResult()).intValue();
	}
}
