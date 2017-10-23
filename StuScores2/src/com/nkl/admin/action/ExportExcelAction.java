
package com.nkl.admin.action;

import java.util.HashMap;
import java.util.List;

import com.nkl.admin.domain.Score;
import com.nkl.admin.domain.User;
import com.nkl.admin.manager.AdminManager;
import com.nkl.common.action.BaseAction;
import com.nkl.common.util.BeanLocator;
import com.nkl.common.util.Param;

public class ExportExcelAction extends BaseAction{

	private static final long serialVersionUID = 8143993190677252109L;
	AdminManager adminManager = (AdminManager)BeanLocator.getInstance().getBean("adminManager");
	
	//导出参数
	User paramsUser;
	Score paramsScore;
	
	HashMap<String, Object> report = new HashMap<String, Object>();

	/**
	 * @Title: exportUsers
	 * @Description: 导出学生信息
	 * @return String
	 */
	public String exportUsers(){
		try{
			if (paramsUser==null) {
				paramsUser = new User();
			}
			//设置分页信息
			paramsUser.setStart(-1);
			//查询学生列表
			paramsUser.setUser_type(1);
			List<User> users = adminManager.listUsers(paramsUser,null); 
			
			report.put("users", users);
			
			//设置导出文件名
			setExportExcelName("学生信息.xls");
		}
        catch(Exception e){
            setErrorReason("导出异常，请稍后再试", e);
            return ERROR;
        }
        return SUCCESS; 
	}
	
	/**
	 * @Title: exportScores
	 * @Description: 导出成绩信息
	 * @return String
	 */
	public String exportScores(){
		try{
			if (paramsScore==null) {
				paramsScore = new Score();
			}
			//设置分页信息
			paramsScore.setStart(-1);
			//查询成绩列表
			User admin = (User)Param.getSession("admin");//查询当前用户
			if (admin.getUser_type()==2) {
				paramsScore.setTeacher_id(admin.getUser_id());//设置教师为当前用户
			}else if (admin.getUser_type()==1) {
				User user = new User();
				user.setUser_id(admin.getUser_id());
				paramsScore.setUser(user);//设置学生为当前用户
			}
			//查询成绩列表
			List<Score> scores = adminManager.listScores(paramsScore,null); 
			
			report.put("scores", scores);
			
			//设置导出文件名
			setExportExcelName("成绩信息.xls");
		}
        catch(Exception e){
            setErrorReason("导出异常，请稍后再试", e);
            return ERROR;
        }
        return SUCCESS; 
	}
	
	/**
	 * @Title: exportScoresSum
	 * @Description: 导出综合成绩信息
	 * @return String
	 */
	public String exportScoresSum(){
		try{
			if (paramsScore==null) {
				paramsScore = new Score();
			}
			//设置分页信息
			paramsScore.setStart(-1);
			//查询成绩列表
			User admin = (User)Param.getSession("admin");//查询当前用户
			if (admin.getUser_type()==2) {
				paramsScore.setTeacher_id(admin.getUser_id());//设置教师为当前用户
			}else if (admin.getUser_type()==1) {
				User user = new User();
				user.setUser_id(admin.getUser_id());
				paramsScore.setUser(user);//设置学生为当前用户
			}
			//查询成绩列表
			List<Score> scores = adminManager.listScoresSum(paramsScore,null); 
			
			report.put("scores", scores);
			
			//设置导出文件名
			setExportExcelName("综合成绩信息.xls");
		}
        catch(Exception e){
            setErrorReason("导出异常，请稍后再试", e);
            return ERROR;
        }
        return SUCCESS; 
	}

	public User getParamsUser() {
		return paramsUser;
	}

	public void setParamsUser(User paramsUser) {
		this.paramsUser = paramsUser;
	}

	public HashMap<String, Object> getReport() {
		return report;
	}

	public void setReport(HashMap<String, Object> report) {
		this.report = report;
	}

	public Score getParamsScore() {
		return paramsScore;
	}

	public void setParamsScore(Score paramsScore) {
		this.paramsScore = paramsScore;
	}
	
	
}
