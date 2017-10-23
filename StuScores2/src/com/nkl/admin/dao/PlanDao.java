package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.Plan;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class PlanDao extends BaseDao {

	public void addPlan(Plan plan){
		super.add(plan);
	}

	public void delPlan(Integer plan_id){
		super.del(Plan.class, plan_id);
	}

	public void delPlans(String[] plan_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <plan_ids.length; i++) {
			sBuilder.append(plan_ids[i]);
			if (i !=plan_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Plan WHERE plan_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);	
	}

	public void updatePlan(Plan plan){
		Plan _plan = (Plan)super.get(Plan.class, plan.getPlan_id());
		if (plan.getPlan_year()!=null && plan.getPlan_year()!=0) {
			_plan.setPlan_year(plan.getPlan_year());
		}
		if (plan.getPlan_year_half()!=null && plan.getPlan_year_half()!=0) {
			_plan.setPlan_year_half(plan.getPlan_year_half());
		}
		if (plan.getClazz()!=null && plan.getClazz().getClazz_id()!=null) {
			_plan.setClazz(plan.getClazz());
		}
		if (plan.getCourse()!=null && plan.getCourse().getCourse_id()!=null) {
			_plan.setCourse(plan.getCourse());
		}
		if (plan.getUser()!=null && plan.getUser().getUser_id()!=null) {
			_plan.setUser(plan.getUser());
		}
	}

	@SuppressWarnings("rawtypes")
	public Plan getPlan(Plan plan){
		Plan _plan=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Plan p ");
		sBuilder.append("  join fetch p.clazz ");
		sBuilder.append("  join fetch p.course ");
		sBuilder.append("  join fetch p.user Where 1=1 ");
		if (plan.getPlan_id()!=null && plan.getPlan_id()!=0) {
			sBuilder.append(" and plan_id = " + plan.getPlan_id() +" ");
		}
		if (plan.getPlan_year()!=null && plan.getPlan_year()!=0) {
			sBuilder.append(" and plan_year = " + plan.getPlan_year() +" ");
		}
		if (plan.getPlan_year_half()!=null && plan.getPlan_year_half()!=0) {
			sBuilder.append(" and plan_year_half = " + plan.getPlan_year_half() +" ");
		}
		if (plan.getClazz()!=null && plan.getClazz().getClazz_id()!=null) {
			sBuilder.append(" and p.clazz.clazz_id = " + plan.getClazz().getClazz_id() +" ");
		}
		if (plan.getCourse()!=null && plan.getCourse().getCourse_id()!=null) {
			sBuilder.append(" and p.course.course_id = " + plan.getCourse().getCourse_id() +" ");
		}
		if (plan.getUser()!=null && plan.getUser().getUser_id()!=null) {
			sBuilder.append(" and p.user.user_id = " + plan.getUser().getUser_id() +" ");
		}

		List list = super.executeQueryHql(sBuilder.toString(), null);
		if (list != null && list.size() > 0) {
			 _plan = (Plan)list.get(0);
		}
		return _plan;
	}

	@SuppressWarnings("rawtypes")
	public List<Plan>  listPlans(Plan plan){
		List<Plan> plans = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Plan p ");
		sBuilder.append("  join fetch p.clazz ");
		sBuilder.append("  join fetch p.course ");
		sBuilder.append("  join fetch p.user Where 1=1 ");
		
		if (plan.getPlan_id()!=null && plan.getPlan_id()!=0) {
			sBuilder.append(" and plan_id = " + plan.getPlan_id() +" ");
		}
		if (plan.getPlan_year()!=null && plan.getPlan_year()!=0) {
			sBuilder.append(" and plan_year = " + plan.getPlan_year() +" ");
		}
		if (plan.getPlan_year_half()!=null && plan.getPlan_year_half()!=0) {
			sBuilder.append(" and plan_year_half = " + plan.getPlan_year_half() +" ");
		}
		if (plan.getClazz()!=null && plan.getClazz().getClazz_id()!=null && plan.getClazz().getClazz_id()!=0) {
			sBuilder.append(" and p.clazz.clazz_id = " + plan.getClazz().getClazz_id() +" ");
		}
		if (plan.getCourse()!=null && plan.getCourse().getCourse_id()!=null && plan.getCourse().getCourse_id()!=0) {
			sBuilder.append(" and p.course.course_id = " + plan.getCourse().getCourse_id() +" ");
		}
		if (plan.getUser()!=null && plan.getUser().getUser_id()!=null && plan.getUser().getUser_id()!=0) {
			sBuilder.append(" and p.user.user_id = " + plan.getUser().getUser_id() +" ");
		}
		if (plan.getUser()!=null && !StringUtil.isEmptyString(plan.getUser().getReal_name())) {
			sBuilder.append(" and p.user.real_name like '%" + plan.getUser().getReal_name() +"%' ");
		}
		sBuilder.append(" order by plan_id asc");

		if (plan.getStart() != -1) {
			sBuilder.append(" limit " + plan.getStart() + "," + plan.getLimit());
		}

		List list = null;
		if (plan.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), null, plan.getStart(), plan.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), null);
		}
		if (list != null && list.size() > 0) {
			plans = new ArrayList<Plan>();
			for (Object object : list) {
				plans.add((Plan)object);
			}
		}
		return plans;
	}

	public int  listPlansCount(Plan plan){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Plan p Where 1=1 ");

		if (plan.getPlan_id()!=null && plan.getPlan_id()!=0) {
			sBuilder.append(" and plan_id = " + plan.getPlan_id() +" ");
		}
		if (plan.getPlan_year()!=null && plan.getPlan_year()!=0) {
			sBuilder.append(" and plan_year = " + plan.getPlan_year() +" ");
		}
		if (plan.getPlan_year_half()!=null && plan.getPlan_year_half()!=0) {
			sBuilder.append(" and plan_year_half = " + plan.getPlan_year_half() +" ");
		}
		if (plan.getClazz()!=null && plan.getClazz().getClazz_id()!=null && plan.getClazz().getClazz_id()!=0) {
			sBuilder.append(" and p.clazz.clazz_id = " + plan.getClazz().getClazz_id() +" ");
		}
		if (plan.getCourse()!=null && plan.getCourse().getCourse_id()!=null && plan.getCourse().getCourse_id()!=0) {
			sBuilder.append(" and p.course.course_id = " + plan.getCourse().getCourse_id() +" ");
		}
		if (plan.getUser()!=null && plan.getUser().getUser_id()!=null && plan.getUser().getUser_id()!=0) {
			sBuilder.append(" and p.user.user_id = " + plan.getUser().getUser_id() +" ");
		}
		if (plan.getUser()!=null && !StringUtil.isEmptyString(plan.getUser().getReal_name())) {
			sBuilder.append(" and p.user.real_name like '%" + plan.getUser().getReal_name() +"%' ");
		}

		long count = (Long)super.executeQueryCountHql(sBuilder.toString(), null);
		sum = (int)count;
		return sum;
	}

}
