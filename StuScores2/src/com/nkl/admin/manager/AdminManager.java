package com.nkl.admin.manager;

import com.nkl.admin.dao.*;
import com.nkl.admin.domain.*;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.Md5;
import com.nkl.common.util.StringUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminManager {

	ClazzDao clazzDao;
	CourseDao courseDao;
	ScoreDao scoreDao;
	UserDao userDao;
	PlanDao planDao;

	/**
	 * @Title: listUsers
	 * @Description: 用户查询
	 * @param user
	 * @return List<User>
	 */
	public List<User> listUsers(User user, int[] sum) {
		
		if (sum != null) {
			sum[0] = userDao.listUsersCount(user);
		}
		List<User> users = userDao.listUsers(user);

		
		return users;
	}

	/**
	 * @Title: queryUser
	 * @Description: 用户查询
	 * @param user
	 * @return User
	 */
	public User queryUser(User user) {
		
		User _user = userDao.getUser(user);
		
		return _user;
	}

	/**
	 * @Title: addUser
	 * @Description: 添加用户
	 * @param user
	 * @return void
	 */
	public void addUser(User user) {
		
		user.setUser_pass(Md5.makeMd5(user.getUser_pass()));
		userDao.addUser(user);
		
	}
	
	/**
	 * @Title: addUserBatch
	 * @Description: 添加用户
	 * @param List<Score>
	 * @return void
	 * @throws SQLException 
	 */
	public void addUserBatch(List<User> users) throws SQLException {
		for (int i = 0; i < users.size(); i++) {
			userDao.addUser(users.get(i));
		}
	}

	/**
	 * @Title: updateUser
	 * @Description: 更新用户信息
	 * @param user
	 * @return void
	 */
	public void updateUser(User user) {
		
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			user.setUser_pass(Md5.makeMd5(user.getUser_pass()));
		}
		userDao.updateUser(user);
		
	}

	/**
	 * @Title: delUsers
	 * @Description: 删除用户信息
	 * @param user
	 * @return void
	 */
	public void delUsers(User user) {
		
		userDao.delUsers(user.getIds().split(","));
		
	}

	/**
	 * @Title: listClazzs
	 * @Description: 班级查询
	 * @param clazz
	 * @return List<Clazz>
	 */
	public List<Clazz> listClazzs(Clazz clazz, int[] sum) {
		
		if (sum != null) {
			sum[0] = clazzDao.listClazzsCount(clazz);
		}
		List<Clazz> clazzs = clazzDao.listClazzs(clazz);

		
		return clazzs;
	}

	/**
	 * @Title: queryClazz
	 * @Description: 班级查询
	 * @param clazz
	 * @return Clazz
	 */
	public Clazz queryClazz(Clazz clazz) {
		
		Clazz _clazz = clazzDao.getClazz(clazz);
		
		return _clazz;
	}

	/**
	 * @Title: addClazz
	 * @Description: 添加班级
	 * @param clazz
	 * @return void
	 */
	public void addClazz(Clazz clazz) {
		
		clazzDao.addClazz(clazz);
		
	}

	/**
	 * @Title: updateClazz
	 * @Description: 更新班级信息
	 * @param clazz
	 * @return void
	 */
	public void updateClazz(Clazz clazz) {
		
		clazzDao.updateClazz(clazz);
		
	}

	/**
	 * @Title: delClazzs
	 * @Description: 删除班级信息
	 * @param clazz
	 * @return void
	 */
	public void delClazzs(Clazz clazz) {
		
		clazzDao.delClazzs(clazz.getIds().split(","));
		
	}

	/**
	 * @Title: listCourses
	 * @Description: 课程查询
	 * @param course
	 * @return List<Course>
	 */
	public List<Course> listCourses(Course course, int[] sum) {
		
		if (sum != null) {
			sum[0] = courseDao.listCoursesCount(course);
		}
		List<Course> courses = courseDao.listCourses(course);

		
		return courses;
	}

	/**
	 * @Title: queryCourse
	 * @Description: 课程查询
	 * @param course
	 * @return Course
	 */
	public Course queryCourse(Course course) {
		
		Course _course = courseDao.getCourse(course);
		
		return _course;
	}

	/**
	 * @Title: addCourse
	 * @Description: 添加课程
	 * @param course
	 * @return void
	 */
	public void addCourse(Course course) {
		
		courseDao.addCourse(course);
		
	}

	/**
	 * @Title: updateCourse
	 * @Description: 更新课程信息
	 * @param course
	 * @return void
	 */
	public void updateCourse(Course course) {
		
		courseDao.updateCourse(course);
		
	}

	/**
	 * @Title: delCourses
	 * @Description: 删除课程信息
	 * @param course
	 * @return void
	 */
	public void delCourses(Course course) {
		
		courseDao.delCourses(course.getIds().split(","));
		
	}
	
	/**
	 * @Title: listPlans
	 * @Description: 教学计划查询
	 * @param plan
	 * @return List<Plan>
	 */
	public List<Plan> listPlans(Plan plan, int[] sum) {
		
		if (sum != null) {
			sum[0] = planDao.listPlansCount(plan);
		}
		List<Plan> plans = planDao.listPlans(plan);

		
		return plans;
	}
	
	/**
	 * @Title: queryPlan
	 * @Description: 教学计划查询
	 * @param plan
	 * @return Plan
	 */
	public Plan queryPlan(Plan plan) {
		
		Plan _plan = planDao.getPlan(plan);
		
		return _plan;
	}

	/**
	 * @Title: addPlan
	 * @Description: 添加教学计划
	 * @param plan
	 * @return void
	 */
	public void addPlan(Plan plan) {
		
		planDao.addPlan(plan);
		
	}

	/**
	 * @Title: updatePlan
	 * @Description: 更新教学计划信息
	 * @param plan
	 * @return void
	 */
	public void updatePlan(Plan plan) {
		
		planDao.updatePlan(plan);
		
	}

	/**
	 * @Title: delPlans
	 * @Description: 删除教学计划信息
	 * @param plan
	 * @return void
	 */
	public void delPlans(Plan plan) {
		
		planDao.delPlans(plan.getIds().split(","));
		
	}
	
	/**
	 * @Title: listScores
	 * @Description: 成绩查询
	 * @param score
	 * @return List<Score>
	 */
	public List<Score> listScores(Score score, int[] sum) {
		
		if (sum != null) {
			sum[0] = scoreDao.listScoresCount(score);
		}
		List<Score> scores = scoreDao.listScores(score);

		
		return scores;
	}
	
	/**
	 * @Title: listScoresSum
	 * @Description: 成绩查询
	 * @param score
	 * @return List<Score>
	 */
	public List<Score> listScoresSum(Score score, int[] sum) {
		
		if (sum != null) {
			sum[0] = scoreDao.listScoresSumCount(score);
		}
		List<Score> scores = scoreDao.listScoresSum(score);

		
		return scores;
	}
	
	/**
	 * @Title: queryScore
	 * @Description: 成绩查询
	 * @param score
	 * @return Score
	 */
	public Score queryScore(Score score) {
		
		Score _score = scoreDao.getScore(score);
		
		return _score;
	}

	/**
	 * @Title: addScore
	 * @Description: 添加成绩
	 * @param score
	 * @return void
	 */
	public void addScore(Score score) {
		
		scoreDao.addScore(score);
		
	}

	/**
	 * @Title: addScoreBatch
	 * @Description: 添加成绩
	 * @param List<Score>
	 * @return void
	 * @throws SQLException 
	 */
	public void addScoreBatch(List<Score> scores) throws SQLException {
		for (int i = 0; i < scores.size(); i++) {
			scoreDao.importScore(scores.get(i));
		}
	}
	
	/**
	 * @Title: updateScore
	 * @Description: 更新成绩信息
	 * @param score
	 * @return void
	 */
	public void updateScore(Score score) {
		
		scoreDao.updateScore(score);
		
	}

	/**
	 * @Title: delScores
	 * @Description: 删除成绩信息
	 * @param score
	 * @return void
	 */
	public void delScores(Score score) {
		
		scoreDao.delScores(score.getIds().split(","));
		
	}
	

	public ClazzDao getClazzDao() {
		return clazzDao;
	}

	public void setClazzDao(ClazzDao clazzDao) {
		this.clazzDao = clazzDao;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public ScoreDao getScoreDao() {
		return scoreDao;
	}

	public void setScoreDao(ScoreDao scoreDao) {
		this.scoreDao = scoreDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public PlanDao getPlanDao() {
		return planDao;
	}

	public void setPlanDao(PlanDao planDao) {
		this.planDao = planDao;
	}

	/*//add start
	*//**
	 * @Title: listSingleScoresStudent
	 * @Description: 查询学生各科成绩
	 * @param score
	 * @return List<Score>
	 *//*
	public List<Score> listSingleScoresStudent(Score score) {
		Connection conn = BaseDao.getConnection();
		List<Score> scores = scoreDao.listScores(score, conn);
		if (scores!=null && scores.size()>0) {
			//查询各科成绩排名
			for (Score temp : scores) {
				score.setCourse_id(temp.getCourse_id());
				Map<Integer,Score> map = scoreDao.listSingleScoresIndex(score, conn);
				temp.setScore_index(map.get(Integer.valueOf(score.getUser_id())).getScore_index());
			}
		}

		BaseDao.closeDB(null, null, conn);
		return scores;
	}

	*//**
	 * @Title: listSingleScores
	 * @Description: 单科成绩排名
	 * @param score
	 * @return List<Score>
	 *//*
	public List<Score> listSingleScores(Score score,int[] sum) {
		Connection conn = BaseDao.getConnection();
		if (sum!=null) {
			sum[0] = scoreDao.listScoresCount(score, conn);
		}
		List<Score> scores = scoreDao.listScores(score, conn);
		if (scores!=null && scores.size()>0) {
			//查询单科成绩排名索引
			Map<Integer,Score> map = scoreDao.listSingleScoresIndex(score, conn);
			//查询单科成绩排名
			for (Score score2 : scores) {
				score2.setScore_index(map.get(score2.getUser_id()).getScore_index());
			}
		}

		BaseDao.closeDB(null, null, conn);
		return scores;
	}

	*//**
	 * @Title: listSumScores
	 * @Description: 总分成绩
	 * @param score
	 * @return List<Score>
	 *//*
	public List<Score> listSumScores(Score score,int[] sum) {
		Connection conn = BaseDao.getConnection();
		if (sum!=null) {
			sum[0] = scoreDao.listSumScoresCount(score, conn);
		}
		List<Score> scores = scoreDao.listSumScores(score, conn);

		if (scores!=null && scores.size()>0) {
			//查询总分成绩排名索引
			Map<Integer,Score> map = scoreDao.listSumScoresIndex(score, conn);
			//查询总分成绩排名
			for (Score score2 : scores) {
				score2.setScore_index(map.get(score2.getUser_id()).getScore_index());
			}
		}

		BaseDao.closeDB(null, null, conn);
		return scores;
	}

	*//**
	 * @Title: listSingleScoresIndex
	 * @Description: 单科成绩排名索引
	 * @param score
	 * @return Map<Integer,Score>
	 *//*
	public Map<Integer,Score> listSingleScoresIndex(Score score) {
		Connection conn = BaseDao.getConnection();
		Map<Integer,Score> maps = scoreDao.listSingleScoresIndex(score, conn);

		BaseDao.closeDB(null, null, conn);
		return maps;
	}

	*//**
	 * @Title: listSumScoresIndex
	 * @Description: 总分成绩排名索引
	 * @param score
	 * @return Map<Integer,Score>
	 *//*
	public Map<Integer,Score> listSumScoresIndex(Score score) {
		Connection conn = BaseDao.getConnection();
		Map<Integer,Score> maps = scoreDao.listSumScoresIndex(score, conn);

		BaseDao.closeDB(null, null, conn);
		return maps;
	}

	*//**
	 * @Title: listSingleAvgScores
	 * @Description: 查询单科平均分
	 * @param score
	 * @return Map<Integer,Score>
	 *//*
	public double listSingleAvgScores(Score score) {
		Connection conn = BaseDao.getConnection();
		double avg = scoreDao.listSingleAvgScores(score, conn);

		BaseDao.closeDB(null, null, conn);
		return avg;
	}

	*//**
	 * @Title: listSumAvgScores
	 * @Description: 查询所有科目平均分
	 * @param score
	 * @return Map<Integer,Score>
	 *//*
	public double listSumAvgScores(Score score) {
		Connection conn = BaseDao.getConnection();
		double avg = scoreDao.listSumAvgScores(score, conn);

		BaseDao.closeDB(null, null, conn);
		return avg;
	}

	*//**
	 * @Title: listSingleScoresSection
	 * @Description: 查询单科成绩分布
	 * @param score
	 * @return List<Score>
	 *//*
	public List<Score> listSingleScoresSection(Score score) {
		Connection conn = BaseDao.getConnection();
		List<Score> scores = scoreDao.listSingleScoresSection(score, conn);

		BaseDao.closeDB(null, null, conn);
		return scores;
	}

	*//**
	 * @Title: listSumScoresSection
	 * @Description: 查询总分成绩分布
	 * @param score
	 * @return List<Score>
	 *//*
	public List<Score> listSumScoresSection(Score score) {
		Connection conn = BaseDao.getConnection();
		List<Score> scores = scoreDao.listSumScoresSection(score, conn);

		BaseDao.closeDB(null, null, conn);
		return scores;
	}*/
	//add end
}
