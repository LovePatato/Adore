package com.nkl.admin.action;

import com.nkl.admin.domain.*;
import com.nkl.admin.manager.AdminManager;
import com.nkl.common.action.BaseAction;
import com.nkl.common.util.BeanLocator;
import com.nkl.common.util.Param;

import java.util.Date;
import java.util.List;

public class AdminAction  extends BaseAction {

	private static final long serialVersionUID = 1L;
	AdminManager adminManager = (AdminManager)BeanLocator.getInstance().getBean("adminManager");
	
	//抓取页面参数
	User paramsUser;
	Clazz paramsClazz;
	Course paramsCourse;
	Score paramsScore;
	Plan paramsPlan;
	
	String tip;
	
	/**
	 * @Title: saveAdmin
	 * @Description: 保存修改个人信息
	 * @return String
	 */
	public String saveAdmin(){
		try {
			//验证学生会话是否失效
			if (!validateAdmin()) {
				return "loginTip";
			}
			 //保存修改个人信息
			adminManager.updateUser(paramsUser);
			//更新session
			User admin = new User();
			admin.setUser_id(paramsUser.getUser_id());
			admin = adminManager.queryUser(admin);
			Param.setSession("admin", admin);
			
			setSuccessTip("编辑成功", "modifyInfo.jsp");
			
		} catch (Exception e) {
			setErrorTip("编辑异常", "modifyInfo.jsp");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: saveAdminPass
	 * @Description: 保存修改个人密码
	 * @return String
	 */
	public String saveAdminPass(){
		try {
			//验证学生会话是否失效
			if (!validateAdmin()) {
				return "loginTip";
			}
			 //保存修改个人密码
			//paramsUser.setUser_pass(Md5.makeMd5(paramsUser.getUser_pass()));
			adminManager.updateUser(paramsUser);
			//更新session
			User admin = (User)Param.getSession("admin");
			if (admin!=null) {
				admin.setUser_pass(paramsUser.getUser_pass());
				Param.setSession("admin", admin);
			}
			
			setSuccessTip("修改成功", "modifyPwd.jsp");
		} catch (Exception e) {
			setErrorTip("修改异常", "modifyPwd.jsp");
		}
		
		return "infoTip";
	}
	
	
	/**
	 * @Title: listUsers
	 * @Description: 查询学生
	 * @return String
	 */
	public String listUsers(){
		try {
			if (paramsUser==null) {
				paramsUser = new User();
			}
			//查看学生信息
			paramsUser.setUser_type(1);
			
			//设置分页信息
			setPagination(paramsUser);
			//总的条数
			int[] sum={0};
			//查询学生列表
			List<User> users = adminManager.listUsers(paramsUser,sum); 
			
			Param.setAttribute("users", users);
			setTotalCount(sum[0]);
			
		} catch (Exception e) {
			setErrorTip("查询学生异常", "main.jsp");
			return "infoTip";
		}
		
		return "userShow";
	}
	
	/**
	 * @Title: queryCourse
	 * @Description: 查询课程
	 * @return String
	 */
	public String queryCourse(){
		try {
			if (paramsCourse==null) {
				paramsCourse = new Course();
			}
			User admin = (User)Param.getSession("admin");
			paramsCourse.setStart(-1);
			paramsCourse.setUser_id(admin.getUser_id());
			//查询学生列表
			List<Course> courses = adminManager.listCourses(paramsCourse,null); 
			
			setResult("courses", courses);
			
		} catch (Exception e) {
			setErrorReason("查询课程信息失败，服务器异常！",e);
			return "error";
		}
		
		return "success";
	}
	
	/**
	 * @Title: queryStudent
	 * @Description: 查询学生
	 * @return String
	 */
	public String queryStudent(){
		try {
			if (paramsUser==null) {
				paramsUser = new User();
			}
			paramsUser.setUser_type(1);
			paramsUser.setStart(-1);
			//查询学生列表
			List<User> users = adminManager.listUsers(paramsUser,null); 
			
			setResult("users", users);
			
		} catch (Exception e) {
			setErrorReason("查询学生信息失败，服务器异常！",e);
			return "error";
		}
		
		return "success";
	}
	
	/**
	 * @Title: addUserShow
	 * @Description: 显示添加学生页面
	 * @return String
	 */
	public String addUserShow(){
		//查询班级字典
		Clazz clazz = new Clazz();
		clazz.setStart(-1);
		List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
		Param.setAttribute("clazzs", clazzs);
		
		return "userEdit";
	}
	
	/**
	 * @Title: addUser
	 * @Description: 添加学生
	 * @return String
	 */
	public String addUser(){
		try {
			//检查学生是否存在
			User user = new User();
			user.setUser_name(paramsUser.getUser_name());
			user = adminManager.queryUser(user);
			if (user!=null) {
				tip="失败，该学号已经存在！";
				Param.setAttribute("user", paramsUser);
				
				//查询班级字典
				Clazz clazz = new Clazz();
				clazz.setStart(-1);
				List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
				Param.setAttribute("clazzs", clazzs);
				
				return "userEdit";
			}
			
			 //添加学生
			paramsUser.setUser_type(1);
			paramsUser.setReg_date(new Date());
			adminManager.addUser(paramsUser);
			
			setSuccessTip("添加学生成功", "Admin_listUsers.action");
		} catch (Exception e) {
			setErrorTip("添加学生异常", "Admin_listUsers.action");
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editUser
	 * @Description: 编辑学生
	 * @return String
	 */
	public String editUser(){
		try {
			 //得到学生
			User user = adminManager.queryUser(paramsUser);
			Param.setAttribute("user", user);
			
			//查询班级字典
			Clazz clazz = new Clazz();
			clazz.setStart(-1);
			List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
			Param.setAttribute("clazzs", clazzs);
			
		} catch (Exception e) {
			setErrorTip("查询学生异常", "Admin_listUsers.action");
			return "infoTip";
		}
		
		return "userEdit";
	}
	
	/**
	 * @Title: saveUser
	 * @Description: 保存编辑学生
	 * @return String
	 */
	public String saveUser(){
		try {
			 //保存编辑学生
			adminManager.updateUser(paramsUser);
			
			setSuccessTip("编辑成功", "Admin_listUsers.action");
		} catch (Exception e) {
			tip="编辑失败";
			Param.setAttribute("user", paramsUser);
			
			//查询班级字典
			Clazz clazz = new Clazz();
			clazz.setStart(-1);
			List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
			Param.setAttribute("clazzs", clazzs);
			
			return "userEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delUsers
	 * @Description: 删除学生
	 * @return String
	 */
	public String delUsers(){
		try {
			 //删除学生
			adminManager.delUsers(paramsUser);
			
			setSuccessTip("删除学生成功", "Admin_listUsers.action");
		} catch (Exception e) {
			setErrorTip("删除学生异常", "Admin_listUsers.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listTeachers
	 * @Description: 查询教师
	 * @return String
	 */
	public String listTeachers(){
		try {
			if (paramsUser==null) {
				paramsUser = new User();
			}
			paramsUser.setUser_type(2);
			
			//设置分页信息
			setPagination(paramsUser);
			//总的条数
			int[] sum={0};
			//查询教师列表
			List<User> users = adminManager.listUsers(paramsUser,sum); 
			
			Param.setAttribute("users", users);
			setTotalCount(sum[0]);
			
		} catch (Exception e) {
			setErrorTip("查询教师异常", "main.jsp");
			return "infoTip";
		}
		
		return "teacherShow";
	}
	
	/**
	 * @Title: addTeacherShow
	 * @Description: 显示添加教师页面
	 * @return String
	 */
	public String addTeacherShow(){
		return "teacherEdit";
	}
	
	/**
	 * @Title: addTeacher
	 * @Description: 添加教师
	 * @return String
	 */
	public String addTeacher(){
		try {
			//检查登录名是否存在
			User user = new User();
			user.setUser_name(paramsUser.getUser_name());
			user = adminManager.queryUser(user);
			if (user!=null) {
				tip="失败，该用户名已经存在！";
				Param.setAttribute("user", paramsUser);
				return "teacherEdit";
			}
			 //添加教师
			paramsUser.setUser_type(2);
			paramsUser.setReg_date(new Date());
			adminManager.addUser(paramsUser);
			
			setSuccessTip("添加成功", "Admin_listTeachers.action");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("添加教师异常", "Admin_listTeachers.action");
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editTeacher
	 * @Description: 编辑教师
	 * @return String
	 */
	public String editTeacher(){
		try {
			 //得到教师
			User user = adminManager.queryUser(paramsUser);
			Param.setAttribute("user", user);
			
		} catch (Exception e) {
			setErrorTip("查询教师异常", "Admin_listTeachers.action");
			return "infoTip";
		}
		
		return "teacherEdit";
	}
	
	/**
	 * @Title: saveTeacher
	 * @Description: 保存编辑教师
	 * @return String
	 */
	public String saveTeacher(){
		try {
			 //保存编辑教师
			adminManager.updateUser(paramsUser);
			
			setSuccessTip("编辑成功", "Admin_listTeachers.action");
		} catch (Exception e) {
			tip="编辑失败";
			Param.setAttribute("user", paramsUser);
			return "userEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delTeachers
	 * @Description: 删除教师
	 * @return String
	 */
	public String delTeachers(){
		try {
			 //删除教师
			adminManager.delUsers(paramsUser);
			
			setSuccessTip("删除教师成功", "Admin_listTeachers.action");
		} catch (Exception e) {
			setErrorTip("删除教师异常", "Admin_listTeachers.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listClazzs
	 * @Description: 查询班级
	 * @return String
	 */
	public String listClazzs(){
		try {
			if (paramsClazz==null) {
				paramsClazz = new Clazz();
			}
			//设置分页信息
			setPagination(paramsClazz);
			//总的条数
			int[] sum={0};
			//查询班级列表
			List<Clazz> clazzs = adminManager.listClazzs(paramsClazz,sum); 
			
			Param.setAttribute("clazzs", clazzs);
			setTotalCount(sum[0]);
			
		} catch (Exception e) {
			setErrorTip("查询班级异常", "main.jsp");
			return "infoTip";
		}
		
		return "clazzShow";
	}
	
	/**
	 * @Title: addClazzShow
	 * @Description: 显示添加班级页面
	 * @return String
	 */
	public String addClazzShow(){
		return "clazzEdit";
	}
	
	/**
	 * @Title: addClazz
	 * @Description: 添加班级
	 * @return String
	 */
	public String addClazz(){
		try {
			 //添加班级
			adminManager.addClazz(paramsClazz);
			
			setSuccessTip("添加成功", "Admin_listClazzs.action");
		} catch (Exception e) {
			setErrorTip("添加班级异常", "Admin_listClazzs.action");
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editClazz
	 * @Description: 编辑班级
	 * @return String
	 */
	public String editClazz(){
		try {
			 //得到班级
			Clazz clazz = adminManager.queryClazz(paramsClazz);
			Param.setAttribute("clazz", clazz);
		} catch (Exception e) {
			setErrorTip("查询班级异常", "Admin_listClazzs.action");
			return "infoTip";
		}
		
		return "clazzEdit";
	}
	
	/**
	 * @Title: saveClazz
	 * @Description: 保存编辑班级
	 * @return String
	 */
	public String saveClazz(){
		try {
			 //保存编辑班级
			adminManager.updateClazz(paramsClazz);
			
			setSuccessTip("编辑成功", "Admin_listClazzs.action");
		} catch (Exception e) {
			tip="编辑失败";
			Param.setAttribute("clazz", paramsClazz);
			return "clazzEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delClazzs
	 * @Description: 删除班级
	 * @return String
	 */
	public String delClazzs(){
		try {
			 //删除班级
			adminManager.delClazzs(paramsClazz);
			
			setSuccessTip("删除班级成功", "Admin_listClazzs.action");
		} catch (Exception e) {
			setErrorTip("删除班级异常", "Admin_listClazzs.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listCourses
	 * @Description: 查询课程
	 * @return String
	 */
	public String listCourses(){
		try {
			if (paramsCourse==null) {
				paramsCourse = new Course();
			}
			//设置分页信息
			setPagination(paramsCourse);
			//总的条数
			int[] sum={0};
			//查询课程列表
			List<Course> courses = adminManager.listCourses(paramsCourse,sum); 
			
			Param.setAttribute("courses", courses);
			setTotalCount(sum[0]);
			
		} catch (Exception e) {
			setErrorTip("查询课程异常", "main.jsp");
			return "infoTip";
		}
		
		return "courseShow";
	}
	
	/**
	 * @Title: addCourseShow
	 * @Description: 显示添加课程页面
	 * @return String
	 */
	public String addCourseShow(){
		return "courseEdit";
	}
	
	/**
	 * @Title: addCourse
	 * @Description: 添加课程
	 * @return String
	 */
	public String addCourse(){
		try {
			 //添加课程
			adminManager.addCourse(paramsCourse);
			
			setSuccessTip("添加成功", "Admin_listCourses.action");
		} catch (Exception e) {
			setErrorTip("添加课程异常", "Admin_listCourses.action");
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editCourse
	 * @Description: 编辑课程
	 * @return String
	 */
	public String editCourse(){
		try {
			 //得到课程
			Course course = adminManager.queryCourse(paramsCourse);
			Param.setAttribute("course", course);
		} catch (Exception e) {
			setErrorTip("查询课程异常", "Admin_listCourses.action");
			return "infoTip";
		}
		
		return "courseEdit";
	}
	
	/**
	 * @Title: saveCourse
	 * @Description: 保存编辑课程
	 * @return String
	 */
	public String saveCourse(){
		try {
			 //保存编辑课程
			adminManager.updateCourse(paramsCourse);
			
			setSuccessTip("编辑成功", "Admin_listCourses.action");
		} catch (Exception e) {
			tip="编辑失败";
			Param.setAttribute("course", paramsCourse);
			return "courseEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delCourses
	 * @Description: 删除课程
	 * @return String
	 */
	public String delCourses(){
		try {
			 //删除课程
			adminManager.delCourses(paramsCourse);
			
			setSuccessTip("删除课程成功", "Admin_listCourses.action");
		} catch (Exception e) {
			setErrorTip("删除课程异常", "Admin_listCourses.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listPlans
	 * @Description: 查询教学计划
	 * @return String
	 */
	public String listPlans(){
		try {
			if (paramsPlan==null) {
				paramsPlan = new Plan();
			}
			//设置分页信息
			setPagination(paramsPlan);
			//总的条数
			int[] sum={0};
			//查询教学计划列表
			List<Plan> plans = adminManager.listPlans(paramsPlan,sum); 
			
			Param.setAttribute("plans", plans);
			setTotalCount(sum[0]);
			
			//查询班级、课程字典
			Clazz clazz = new Clazz();
			clazz.setStart(-1);
			List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
			Param.setAttribute("clazzs", clazzs);
			
			Course course = new Course();
			course.setStart(-1);
			List<Course> courses = adminManager.listCourses(course, null);
			Param.setAttribute("courses", courses);
			
		} catch (Exception e) {
			setErrorTip("查询教学计划异常", "main.jsp");
			return "infoTip";
		}
		
		return "planShow";
	}
	
	/**
	 * @Title: addPlanShow
	 * @Description: 显示添加教学计划页面
	 * @return String
	 */
	public String addPlanShow(){
		//查询班级、课程字典
		Clazz clazz = new Clazz();
		clazz.setStart(-1);
		List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
		Param.setAttribute("clazzs", clazzs);
		
		Course course = new Course();
		course.setStart(-1);
		List<Course> courses = adminManager.listCourses(course, null);
		Param.setAttribute("courses", courses);
		
		//查询教师字典
		User user = new User();
		user.setStart(-1);
		user.setUser_type(2);
		List<User> users = adminManager.listUsers(user, null);
		Param.setAttribute("users", users);
		
		return "planEdit";
	}
	
	/**
	 * @Title: addPlan
	 * @Description: 添加教学计划
	 * @return String
	 */
	public String addPlan(){
		try {
			//判断教学计划是否已经添加
			Plan plan = adminManager.queryPlan(paramsPlan);
			if (plan!=null) {
				tip = "失败，该教师本次教学计划已经存在！";
				Param.setAttribute("plan", paramsPlan);
				
				//查询班级、课程字典
				Clazz clazz = new Clazz();
				clazz.setStart(-1);
				List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
				Param.setAttribute("clazzs", clazzs);
				
				Course course = new Course();
				course.setStart(-1);
				List<Course> courses = adminManager.listCourses(course, null);
				Param.setAttribute("courses", courses);
				
				//查询教师字典
				User user = new User();
				user.setStart(-1);
				user.setUser_type(2);
				List<User> users = adminManager.listUsers(user, null);
				Param.setAttribute("users", users);
				
				return "planEdit";
			}
			
			//添加教学计划
			adminManager.addPlan(paramsPlan);
			
			setSuccessTip("添加成功", "Admin_listPlans.action");
		} catch (Exception e) {
			setErrorTip("添加教学计划异常", "Admin_listPlans.action");
			e.printStackTrace();
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editPlan
	 * @Description: 编辑教学计划
	 * @return String
	 */
	public String editPlan(){
		try {
			 //得到教学计划
			Plan plan = adminManager.queryPlan(paramsPlan);
			Param.setAttribute("plan", plan);
			
			//查询班级、课程字典
			Clazz clazz = new Clazz();
			clazz.setStart(-1);
			List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
			Param.setAttribute("clazzs", clazzs);
			
			Course course = new Course();
			course.setStart(-1);
			List<Course> courses = adminManager.listCourses(course, null);
			Param.setAttribute("courses", courses);
			
			//查询教师字典
			User user = new User();
			user.setStart(-1);
			user.setUser_type(2);
			List<User> users = adminManager.listUsers(user, null);
			Param.setAttribute("users", users);
			
		} catch (Exception e) {
			setErrorTip("查询教学计划异常", "Admin_listPlans.action");
			return "infoTip";
		}
		
		return "planEdit";
	}
	
	/**
	 * @Title: savePlan
	 * @Description: 保存编辑教学计划
	 * @return String
	 */
	public String savePlan(){
		try {
			 //验证教学计划是否存在
			int plan_id = paramsPlan.getPlan_id();
			paramsPlan.setPlan_id(0);
			Plan plan = adminManager.queryPlan(paramsPlan);
			paramsPlan.setPlan_id(plan_id);
			if (plan!=null && plan.getPlan_id()!=plan_id) {
				 tip = "失败，该教师本次教学计划已经存在！";
				 Param.setAttribute("plan", paramsPlan);
				 
				//查询班级、课程字典
				Clazz clazz = new Clazz();
				clazz.setStart(-1);
				List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
				Param.setAttribute("clazzs", clazzs);
				
				Course course = new Course();
				course.setStart(-1);
				List<Course> courses = adminManager.listCourses(course, null);
				Param.setAttribute("courses", courses);
				
				//查询教师字典
				User user = new User();
				user.setStart(-1);
				user.setUser_type(2);
				List<User> users = adminManager.listUsers(user, null);
				Param.setAttribute("users", users);
				
				return "planEdit";
			}
			
			 //保存编辑教学计划
			adminManager.updatePlan(paramsPlan);
			
			setSuccessTip("编辑成功", "Admin_listPlans.action");
		} catch (Exception e) {
			tip="编辑失败";
			Param.setAttribute("plan", paramsPlan);
			
			//查询班级、课程字典
			Clazz clazz = new Clazz();
			clazz.setStart(-1);
			List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
			Param.setAttribute("clazzs", clazzs);
			
			Course course = new Course();
			course.setStart(-1);
			List<Course> courses = adminManager.listCourses(course, null);
			Param.setAttribute("courses", courses);
			
			//查询教师字典
			User user = new User();
			user.setStart(-1);
			user.setUser_type(2);
			List<User> users = adminManager.listUsers(user, null);
			Param.setAttribute("users", users);
			
			return "planEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delPlans
	 * @Description: 删除教学计划
	 * @return String
	 */
	public String delPlans(){
		try {
			 //删除教学计划
			adminManager.delPlans(paramsPlan);
			
			setSuccessTip("删除教学计划成功", "Admin_listPlans.action");
		} catch (Exception e) {
			setErrorTip("删除教学计划异常", "Admin_listPlans.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listScores
	 * @Description: 查询成绩
	 * @return String
	 */
	public String listScores(){
		try {
			if (paramsScore==null) {
				paramsScore = new Score();
			}
			//设置分页信息
			setPagination(paramsScore);


			//总的条数
			int[] sum={0};
			//查询成绩列表
			User admin = (User)Param.getSession("admin");//查询当前用户
			if (admin.getUser_type()==2) {
				paramsScore.setTeacher_id(admin.getUser_id());//设置教师为当前用户
			}else if (admin.getUser_type()==1) {
				User user = new User();
				user.setUser_id(admin.getUser_id());
				paramsScore.setUser(user);//设置学生为当前用户
			}
			
			List<Score> scores = adminManager.listScores(paramsScore,sum); 
			
			Param.setAttribute("scores", scores);
			setTotalCount(sum[0]);
			
			//查询班级、课程字典
			Clazz clazz = new Clazz();
			clazz.setStart(-1);
			List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
			Param.setAttribute("clazzs", clazzs);
			
			Course course = new Course();
			course.setStart(-1);
			List<Course> courses = adminManager.listCourses(course, null);
			Param.setAttribute("courses", courses);
			
		} catch (Exception e) {
			setErrorTip("查询成绩异常", "main.jsp");
			return "infoTip";
		}
		
		return "scoreShow";
	}
	
	/**
	 * @Title: listScoresSum
	 * @Description: 查询成绩
	 * @return String
	 */
	public String listScoresSum(){
		try {
			if (paramsScore==null) {
				paramsScore = new Score();
			}
			//设置分页信息
			setPagination(paramsScore);
			//总的条数
			int[] sum={0};
			//查询成绩列表
			User admin = (User)Param.getSession("admin");//查询当前用户
			if (admin.getUser_type()==2) {
				paramsScore.setTeacher_id(admin.getUser_id());//设置教师为当前用户
			}else if (admin.getUser_type()==1) {
				User user = new User();
				user.setUser_id(admin.getUser_id());
				paramsScore.setUser(user);//设置学生为当前用户
			}
			
			List<Score> scores = adminManager.listScoresSum(paramsScore,sum); 
			
			Param.setAttribute("scores", scores);
			setTotalCount(sum[0]);
			
			//查询班级、课程字典
			Clazz clazz = new Clazz();
			clazz.setStart(-1);
			List<Clazz> clazzs = adminManager.listClazzs(clazz, null);
			Param.setAttribute("clazzs", clazzs);
			
			Course course = new Course();
			course.setStart(-1);
			List<Course> courses = adminManager.listCourses(course, null);
			Param.setAttribute("courses", courses);
			
		} catch (Exception e) {
			setErrorTip("查询成绩异常", "main.jsp");
			return "infoTip";
		}
		
		return "scoreSumShow";
	}
	
	/**
	 * @Title: addScoreShow
	 * @Description: 显示添加成绩页面
	 * @return String
	 */
	public String addScoreShow(){
		return "scoreEdit";
	}
	
	/**
	 * @Title: addScore
	 * @Description: 添加成绩
	 * @return String
	 */
	public String addScore(){
		try {
			//判断成绩是否已经添加
			Score score = adminManager.queryScore(paramsScore);
			if (score!=null) {
				tip = "失败，该学生本次成绩已经存在！";
				Param.setAttribute("score", paramsScore);
				
				return "scoreEdit";
			}
			
			//添加成绩
			adminManager.addScore(paramsScore);
			
			setSuccessTip("添加成功", "Admin_listScores.action");
		} catch (Exception e) {
			setErrorTip("添加成绩异常", "Admin_listScores.action");
			e.printStackTrace();
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editScore
	 * @Description: 编辑成绩
	 * @return String
	 */
	public String editScore(){
		try {
			 //得到成绩
			Score score = adminManager.queryScore(paramsScore);
			Param.setAttribute("score", score);
			
		} catch (Exception e) {
			setErrorTip("查询成绩异常", "Admin_listScores.action");
			return "infoTip";
		}
		
		return "scoreEdit";
	}
	
	/**
	 * @Title: saveScore
	 * @Description: 保存编辑成绩
	 * @return String
	 */
	public String saveScore(){
		try {
			//判断成绩是否已经添加
			int score_id = paramsScore.getScore_id();
			paramsScore.setScore_id(0);
			Score score = adminManager.queryScore(paramsScore);
			paramsScore.setScore_id(score_id);
			if (score!=null && score.getScore_id()!=score_id) {
				tip = "失败，该学生本次成绩已经存在！";
				Param.setAttribute("score", paramsScore);
				
				return "scoreEdit";
			}
			 //保存编辑成绩
			adminManager.updateScore(paramsScore);
			
			setSuccessTip("编辑成功", "Admin_listScores.action");
		} catch (Exception e) {
			tip="编辑失败";
			Param.setAttribute("score", paramsScore);
			
			return "scoreEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delScores
	 * @Description: 删除成绩
	 * @return String
	 */
	public String delScores(){
		try {
			 //删除成绩
			adminManager.delScores(paramsScore);
			
			setSuccessTip("删除成绩成功", "Admin_listScores.action");
		} catch (Exception e) {
			setErrorTip("删除成绩异常", "Admin_listScores.action");
		}
		
		return "infoTip";
	}
	
	
	/**
	 * @Title: validateAdmin
	 * @Description: admin登录验证
	 * @return boolean
	 */
	private boolean validateAdmin(){
		User admin = (User)Param.getSession("admin");
		if (admin!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	private void setErrorTip(String tip,String url){
		Param.setAttribute("tipType", "error");
		Param.setAttribute("tip", tip);
		Param.setAttribute("url1", url);
		Param.setAttribute("value1", "确 定");
	}
	
	private void setSuccessTip(String tip,String url){
		Param.setAttribute("tipType", "success");
		Param.setAttribute("tip", tip);
		Param.setAttribute("url1", url);
		Param.setAttribute("value1", "确 定");
	}

	public User getParamsUser() {
		return paramsUser;
	}

	public void setParamsUser(User paramsUser) {
		this.paramsUser = paramsUser;
	}

	public Clazz getParamsClazz() {
		return paramsClazz;
	}

	public void setParamsClazz(Clazz paramsClazz) {
		this.paramsClazz = paramsClazz;
	}

	public Course getParamsCourse() {
		return paramsCourse;
	}

	public void setParamsCourse(Course paramsCourse) {
		this.paramsCourse = paramsCourse;
	}

	public Score getParamsScore() {
		return paramsScore;
	}

	public void setParamsScore(Score paramsScore) {
		this.paramsScore = paramsScore;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public Plan getParamsPlan() {
		return paramsPlan;
	}

	public void setParamsPlan(Plan paramsPlan) {
		this.paramsPlan = paramsPlan;
	}
	
	 
	// add start
	/**
	 * @Title: listSingleScoresStudent
	 * @Description: 查询学生各科成绩
	 * @return String
	 */
	public String listSingleScoresStudent(){
		/*try {
			if(paramsScore!=null){
				//得到学生信息 作为限制条件
				User admin = (User)Param.getSession("admin");
				paramsScore.setUser_id(admin.getUser_id());
				paramsScore.setClasses_id(admin.getClasses_id());
				paramsScore.setMajor_id(admin.getMajor_id());
				paramsScore.setStart(-1);

				//查询学生各科成绩
				List<Score> scores = adminManager.listSingleScoresStudent(paramsScore);

				Param.setAttribute("scores", scores);
			}

		} catch (Exception e) {
			setErrorReason("查询学生各科成绩失败，服务器异常！",e);
			e.printStackTrace();
			return "error";
		}
*/
		return "singleScoresStudent";
	}

	/**
	 * @Title: listSingleScores
	 * @Description: 单科成绩排名
	 * @return String
	 */
	public String listSingleScores(){
/*		try {
			if(paramsScore!=null){
				//设置分页信息
				setPagination(paramsScore);
				//单科成绩排名
				int[] sum = {0};
				List<Score> scores = adminManager.listSingleScores(paramsScore,sum);

				Param.setAttribute("scores", scores);
				setTotalCount(sum[0]);

				//查询单科平均分
				double singleAvgScores = adminManager.listSingleAvgScores(paramsScore);
				Param.setAttribute("singleAvgScores", singleAvgScores);
			}

			//查询班级、专业、课程字典
			Classes classes = new Classes();
			classes.setStart(-1);
			List<Classes> classess = adminManager.listClassess(classes, null);
			Param.setAttribute("classess", classess);

			Major major = new Major();
			major.setStart(-1);
			List<Major> majors = adminManager.listMajors(major, null);
			Param.setAttribute("majors", majors);

			Course course = new Course();
			course.setStart(-1);
			List<Course> courses = adminManager.listCourses(course, null);
			Param.setAttribute("courses", courses);

		} catch (Exception e) {
			setErrorReason("查询学生单科排名失败，服务器异常！",e);
			return "error";
		}*/

		return "singleScores";
	}

	/**
	 * @Title: listSumScores
	 * @Description: 学生总分排名
	 * @return String
	 */
	public String listSumScores(){
	/*	try {
			if(paramsScore!=null){
				//如果是学生，需要加限制条件
				User admin = (User)Param.getSession("admin");
				if (admin.getUser_type()==1) {
					paramsScore.setClasses_id(admin.getClasses_id());
					paramsScore.setMajor_id(admin.getMajor_id());
				}

				//设置分页信息
				setPagination(paramsScore);
				//学生总分排名
				int[] sum = {0};
				List<Score> scores = adminManager.listSumScores(paramsScore,sum);

				Param.setAttribute("scores", scores);
				setTotalCount(sum[0]);

				//查询所有科目平均分
				double sumAvgScores = adminManager.listSumAvgScores(paramsScore);
				Param.setAttribute("sumAvgScores", sumAvgScores);
			}

			//查询班级、专业、课程字典
			Classes classes = new Classes();
			classes.setStart(-1);
			List<Classes> classess = adminManager.listClassess(classes, null);
			Param.setAttribute("classess", classess);

			Major major = new Major();
			major.setStart(-1);
			List<Major> majors = adminManager.listMajors(major, null);
			Param.setAttribute("majors", majors);

			Course course = new Course();
			course.setStart(-1);
			List<Course> courses = adminManager.listCourses(course, null);
			Param.setAttribute("courses", courses);


		} catch (Exception e) {
			setErrorReason("查询学生总分排名失败，服务器异常！",e);
			return "error";
		}*/

		return "sumScores";
	}

	/**
	 * @Title: listSingleScoresSection
	 * @Description: 查询单科成绩分布
	 * @return String
	 */
	public String listSingleScoresSectionShow(){
	/*	try {
			//查询班级、专业、课程字典
			Classes classes = new Classes();
			classes.setStart(-1);
			List<Classes> classess = adminManager.listClassess(classes, null);
			Param.setAttribute("classess", classess);

			Major major = new Major();
			major.setStart(-1);
			List<Major> majors = adminManager.listMajors(major, null);
			Param.setAttribute("majors", majors);

			Course course = new Course();
			course.setStart(-1);
			List<Course> courses = adminManager.listCourses(course, null);
			Param.setAttribute("courses", courses);

		} catch (Exception e) {
			setErrorReason("查询单科成绩分布失败，服务器异常！",e);
			return "error";
		}*/

		return "singleScoresSectionShow";
	}
	public String listSingleScoresSection(){
	/*	try {
			if(paramsScore!=null){
				//查询单科成绩分布
				List<Score> list = adminManager.listSingleScoresSection(paramsScore);
				Score _score = list.get(0);

				// 准备图形数据
				String[] x = new String[7];
				int[] y = new int[7];
				for (int i = 0; i < 7; i++) {
					if (i==0) {
						x[i] = "0-"+paramsScore.getScore_start();
					}else if (i==list.size()-1) {
						x[i] = (paramsScore.getScore_start()+paramsScore.getScore_sec()*(i-1)) + "-" + paramsScore.getScore_end();
					}else {
						x[i] = (paramsScore.getScore_start()+paramsScore.getScore_sec()*(i-1)) + "-" + (paramsScore.getScore_start()+paramsScore.getScore_sec()*i);
					}
					y[i] = _score.getSec_count(i+1);
				}

				setResult("x", x);
				setResult("y", y);
			}

		} catch (Exception e) {
			setErrorReason("查询单科成绩分布失败，服务器异常！",e);
			return "error";
		}*/

		return "success";
	}


	/**
	 * @Title: listSumScoresSection
	 * @Description: 查询总分成绩分布
	 * @return String
	 */
	public String listSumScoresSectionShow(){
		/*try {
			//查询班级、专业字典
			Classes classes = new Classes();
			classes.setStart(-1);
			List<Classes> classess = adminManager.listClassess(classes, null);
			Param.setAttribute("classess", classess);

			Major major = new Major();
			major.setStart(-1);
			List<Major> majors = adminManager.listMajors(major, null);
			Param.setAttribute("majors", majors);

		} catch (Exception e) {
			setErrorReason("查询总分成绩分布失败，服务器异常！",e);
			return "error";
		}*/

		return "sumScoresSectionShow";
	}
	public String listSumScoresSection(){
		/*try {
			if(paramsScore!=null){
				//查询总分成绩分布
				List<Score> list = adminManager.listSumScoresSection(paramsScore);
				Score _score = list.get(0);

				// 准备图形数据
				String[] x = new String[7];
				int[] y = new int[7];
				for (int i = 0; i < 7; i++) {
					if (i==0) {
						x[i] = "0-"+paramsScore.getScore_start();
					}else if (i==list.size()-1) {
						x[i] = (paramsScore.getScore_start()+paramsScore.getScore_sec()*(i-1)) + "-" + paramsScore.getScore_end();
					}else {
						x[i] = (paramsScore.getScore_start()+paramsScore.getScore_sec()*(i-1)) + "-" + (paramsScore.getScore_start()+paramsScore.getScore_sec()*i);
					}
					y[i] = _score.getSec_count(i+1);
				}


				setResult("x", x);
				setResult("y", y);
			}

		} catch (Exception e) {
			setErrorReason("查询总分成绩分布失败，服务器异常！",e);
			return "error";
		}*/

		return "success";
	}
	//add end
	//折线图
	public String singleScoresStudent(){
		return "singleScoresStudent";
	}
}
