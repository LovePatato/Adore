package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.Clazz;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class ClazzDao extends BaseDao{

	public void addClazz(Clazz clazz){
		super.add(clazz);
	}

	public void delClazz(Integer clazz_id){
		super.del(Clazz.class, clazz_id);
	}

	public void delClazzs(String[] clazz_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <clazz_ids.length; i++) {
			sBuilder.append(clazz_ids[i]);
			if (i !=clazz_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Clazz WHERE clazz_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);	
	}

	public void updateClazz(Clazz clazz){
		Clazz _clazz = (Clazz)super.get(Clazz.class, clazz.getClazz_id());
		if (!StringUtil.isEmptyString(clazz.getClazz_name())) {
			_clazz.setClazz_name(clazz.getClazz_name());
		}
		if (!StringUtil.isEmptyString(clazz.getNote())) {
			_clazz.setNote(clazz.getNote());
		}
	}

	@SuppressWarnings("rawtypes")
	public Clazz getClazz(Clazz clazz){
		Clazz _clazz=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Clazz WHERE 1=1");
		if (clazz.getClazz_id()!=null && clazz.getClazz_id()!=0) {
			sBuilder.append(" and clazz_id = " + clazz.getClazz_id() +" ");
		}
		if (!StringUtil.isEmptyString(clazz.getClazz_name())) {
			sBuilder.append(" and clazz_name = '" + clazz.getClazz_name() +"' ");
		}

		List list = super.executeQueryHql(sBuilder.toString(), null);
		if (list != null && list.size() > 0) {
			 _clazz = (Clazz)list.get(0);
		}
		return _clazz;
	}

	@SuppressWarnings("rawtypes")
	public List<Clazz>  listClazzs(Clazz clazz){
		List<Clazz> clazzs = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Clazz WHERE 1=1");

		if (clazz.getClazz_id()!=null && clazz.getClazz_id()!=0) {
			sBuilder.append(" and clazz_id = " + clazz.getClazz_id() +" ");
		}
		if (!StringUtil.isEmptyString(clazz.getClazz_name())) {
			sBuilder.append(" and clazz_name like '%" + clazz.getClazz_name() +"%' ");
		}
		sBuilder.append(" order by clazz_id asc ");

		List list = null;
		if (clazz.getStart() != -1) {
			list = super.findByPageHql(sBuilder.toString(), null, clazz.getStart(), clazz.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), null);
		}

		if (list != null && list.size() > 0) {
			clazzs = new ArrayList<Clazz>();
			for (Object object : list) {
				clazzs.add((Clazz)object);
			}
		}
		return clazzs;
	}

	public int  listClazzsCount(Clazz clazz){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Clazz WHERE 1=1");

		if (clazz.getClazz_id()!=null && clazz.getClazz_id()!=0) {
			sBuilder.append(" and clazz_id = " + clazz.getClazz_id() +" ");
		}
		if (!StringUtil.isEmptyString(clazz.getClazz_name())) {
			sBuilder.append(" and clazz_name like '%" + clazz.getClazz_name() +"%' ");
		}

		long count = (Long)super.executeQueryCountHql(sBuilder.toString(), null);
		sum = (int)count;
		return sum;
	}

}
