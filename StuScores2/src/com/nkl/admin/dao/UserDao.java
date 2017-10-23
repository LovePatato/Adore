package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.User;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class UserDao extends BaseDao {

	public void addUser(User user){
		super.add(user);
	}
	
	public void delUser(Integer user_id){
		super.del(User.class, user_id);
	}

	public void delUsers(String[] user_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <user_ids.length; i++) {
			sBuilder.append(user_ids[i]);
			if (i !=user_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM User WHERE user_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);	
	}

	public void updateUser(User user){
		User _user = (User)super.get(User.class, user.getUser_id());
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			_user.setUser_pass(user.getUser_pass());
		}
		if (!StringUtil.isEmptyString(user.getReal_name())) {
			_user.setReal_name(user.getReal_name());
		}
		if (user.getUser_sex()!=null && user.getUser_sex()!=0) {
			_user.setUser_sex(user.getUser_sex());
		}
		if (user.getUser_age()!=null && user.getUser_age()!=0) {
			_user.setUser_age(user.getUser_age());
		}
		if (user.getClazz()!=null && user.getClazz().getClazz_id()!=null && user.getClazz().getClazz_id()!=0) {
			_user.setClazz(user.getClazz());
		}
	}

	@SuppressWarnings("rawtypes")
	public User getUser(User user){
		User _user=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM User u left join fetch u.clazz WHERE 1=1");
		if (user.getUser_id()!=null && user.getUser_id()!=0) {
			sBuilder.append(" and user_id = " + user.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			sBuilder.append(" and user_pass ='" + user.getUser_pass() +"' ");
		}
		if (!StringUtil.isEmptyString(user.getUser_name())) {
			sBuilder.append(" and user_name ='" + user.getUser_name() +"' ");
		}

		List list = super.executeQueryHql(sBuilder.toString(), null);
		if (list != null && list.size() > 0) {
			 _user = (User)list.get(0);
		}
		return _user;
	}

	@SuppressWarnings("rawtypes")
	public List<User>  listUsers(User user){
		List<User> users = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM User u left join fetch u.clazz WHERE 1=1");

		if (user.getUser_id()!=null && user.getUser_id()!=0) {
			sBuilder.append(" and user_id = " + user.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			sBuilder.append(" and u.user_pass ='" + user.getUser_pass() +"' ");
		}
		if (!StringUtil.isEmptyString(user.getUser_name())) {
			sBuilder.append(" and u.user_name like '%" + user.getUser_name() +"%' ");
		}
		if (!StringUtil.isEmptyString(user.getReal_name())) {
			sBuilder.append(" and u.real_name like '%" + user.getReal_name() +"%' ");
		}
		if (user.getClazz()!=null && user.getClazz().getClazz_id()!=null && user.getClazz().getClazz_id()!=0) {
			sBuilder.append(" and u.clazz.clazz_id =" + user.getClazz().getClazz_id() +" ");
		}
		if (user.getUser_type()!=null && user.getUser_type()!=0) {
			sBuilder.append(" and u.user_type =" + user.getUser_type() +" ");
		}
		if (user.getCourse_id()!=0) {
			sBuilder.append(" and u.clazz.clazz_id in (select p.clazz.clazz_id from Plan p where p.course.course_id= " + user.getCourse_id());
			sBuilder.append(" and p.plan_year= " + user.getScore_year() +" and p.plan_year_half= " + user.getScore_year_half() +") ");
		}
		
		sBuilder.append(" and u.user_type!=3 order by u.user_id asc ");

		List list = null;
		if (user.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), null, user.getStart(), user.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), null);
		}

		if (list != null && list.size() > 0) {
			users = new ArrayList<User>();
			for (Object object : list) {
				users.add((User)object);
			}
		}
		return users;
	}

	public int  listUsersCount(User user){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM User u WHERE 1=1");

		if (user.getUser_id()!=null && user.getUser_id()!=0) {
			sBuilder.append(" and user_id = " + user.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			sBuilder.append(" and u.user_pass ='" + user.getUser_pass() +"' ");
		}
		if (!StringUtil.isEmptyString(user.getUser_name())) {
			sBuilder.append(" and u.user_name like '%" + user.getUser_name() +"%' ");
		}
		if (!StringUtil.isEmptyString(user.getReal_name())) {
			sBuilder.append(" and u.real_name like '%" + user.getReal_name() +"%' ");
		}
		if (user.getClazz()!=null && user.getClazz().getClazz_id()!=null && user.getClazz().getClazz_id()!=0) {
			sBuilder.append(" and u.clazz.clazz_id =" + user.getClazz().getClazz_id() +" ");
		}
		if (user.getUser_type()!=null && user.getUser_type()!=0) {
			sBuilder.append(" and u.user_type =" + user.getUser_type() +" ");
		}
		if (user.getCourse_id()!=0) {
			sBuilder.append(" and u.clazz.clazz_id in (select p.clazz.clazz_id from Plan p where p.course.course_id= " + user.getCourse_id());
			sBuilder.append(" and p.plan_year= " + user.getScore_year() +" and p.plan_year_half= " + user.getScore_year_half() +") ");
		}
		
		sBuilder.append(" and u.user_type!=3 ");

		long count = (Long)super.executeQueryCountHql(sBuilder.toString(), null);
		sum = (int)count;
		return sum;
	}

}
