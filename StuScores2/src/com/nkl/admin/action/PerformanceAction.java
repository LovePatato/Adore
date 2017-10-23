package com.nkl.admin.action;

import com.nkl.admin.domain.Score;
import com.nkl.admin.domain.User;
import com.nkl.admin.domain.vo.ClassVo;
import com.nkl.admin.domain.vo.CourseVo;
import com.nkl.admin.domain.vo.QueryParam;
import com.nkl.admin.domain.vo.RankVo;
import com.nkl.admin.manager.AdminManager;
import com.nkl.common.util.BeanLocator;
import com.nkl.common.util.Param;
import com.nkl.common.util.ResultUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Zhoufan [https://git.oschina.net/fantazy/].
 * @Date 2017/5/10
 * @Description:
 */
public class PerformanceAction extends ActionSupport {
    AdminManager adminManager = (AdminManager) BeanLocator.getInstance().getBean("adminManager");

    public String listSingleScores(){

        Score score = new Score();
        score.setReal_name("老王");
        score.setClazz_name("离散数学");
        score.setScore_id(1);
        score.setScore_value(100.0);
        score.setScore_valueMin(0.0);
        score.setScore_year(2017);
        score.setScore_year_half(1);
        //adminManager.list
        List<Integer> years = new ArrayList<>();
        years.add(2015);
        years.add(2016);
        Param.setAttribute("year",years);
        try {
            ResultUtils.toJson(ServletActionContext.getResponse(),score);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public String listYears(){

        //adminManager.list
        User user = (User) Param.getSession("admin");
        System.out.println(user.toString());
        List years = adminManager.getScoreDao().listAllYears();
       // Param.setAttribute("year",years);
        try {
            ResultUtils.toJson(ServletActionContext.getResponse(),years);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public String listClasses(){

        User user = (User) Param.getSession("admin");
        System.out.println(user.toString());
        List classes = adminManager.getScoreDao().listAllClasses();
        List<ClassVo> classVos = new ArrayList<>();
        for(int i = 0 ;i < classes.size() ; i++)
        {
            Object datas[] = (Object[]) classes.get(i);
            ClassVo classVo = new ClassVo();
            classVo.setClassId((Integer) datas[0]);
            classVo.setClassName((String) datas[1]);
            classVos.add(classVo);
        }
       // Param.setAttribute("year",years);
        try {
            ResultUtils.toJson(ServletActionContext.getResponse(),classVos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public String listCourses(){

        List courses = adminManager.getScoreDao().listAllCourses();
        List<CourseVo> courseVos = new ArrayList<>();
        for(int i = 0 ;i < courses.size() ; i++)
        {
            Object datas[] = (Object[]) courses.get(i);
            CourseVo courseVo = new CourseVo();
            courseVo.setCourseId((Integer) datas[0]);
            courseVo.setCourseName((String) datas[1]);
            courseVos.add(courseVo);
        }
       // Param.setAttribute("year",years);
        try {
            ResultUtils.toJson(ServletActionContext.getResponse(),courseVos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public String SingleRanks(){
        HttpServletRequest request = ServletActionContext.getRequest();
        Integer year = Integer.valueOf(request.getParameter("year"));
        Integer term = Integer.valueOf(request.getParameter("term"));
        Integer classId = Integer.valueOf(request.getParameter("classId"));
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        QueryParam queryParam = new QueryParam();
        queryParam.setYear(year);
        queryParam.setTerm(term);
        queryParam.setCourseId(courseId);
        queryParam.setClassId(classId);
        if (request.getParameter("pageindex") != null){
            Integer pageNum = Integer.valueOf(request.getParameter("pageindex"));
            queryParam.setPageNum(pageNum);
        }
        if (request.getParameter("pageSize") != null){
            Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
            queryParam.setPageSize(pageSize);
        }
        int total = adminManager.getScoreDao().countRanks(queryParam);
        List<RankVo> rankVos = new ArrayList<>();
        List ranks = adminManager.getScoreDao().listSingleRanks(queryParam);
        for (int i = 0 ; i < ranks.size() ; i++){
            Object data[] = (Object[]) ranks.get(i);
            RankVo rankVo = new RankVo();
            rankVo.setUserNo((String) data[0]);
            rankVo.setRealName((String) data[1]);
            rankVo.setClassName((String) data[2]);
            rankVo.setCourseName((String) data[3]);
            rankVo.setValue((Double) data[4]);
            rankVo.setYear((Integer) data[5]);
            rankVo.setTerm((Integer) data[6]);
            rankVos.add(rankVo);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("rows",rankVos);
        result.put("total",total);
        try {
            ResultUtils.toJson(ServletActionContext.getResponse(),result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
