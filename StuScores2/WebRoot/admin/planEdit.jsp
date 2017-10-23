<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:if test="#attr.plan!=null && #attr.plan.plan_id!=0">编辑</s:if><s:else>添加</s:else>教学计划信息</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	
	var num = /^\d+(\.\d+)?$/;
	 $("#addBtn").bind('click',function(){
		if($("#paramsPlan\\.plan_year").val()==''){
			alert('年份不能为空');
			return;
		}
		if($("#paramsPlan\\.clazz\\.clazz_id").val()=='0'){
			alert('班级不能为空');
			return;
		}
		if($("#paramsPlan\\.course\\.course_id").val()=='0'){
			alert('课程不能为空');
			return;
		}
		if($("#paramsPlan\\.user\\.user_id").val()=='0'){
			alert('教师不能为空');
			return;
		}
		$("#paramsPlan\\.plan_id").val(0);
		$("#info").attr('action','Admin_addPlan.action').submit();
		 
	 });
	 
	 $("#editBtn").bind('click',function(){
		if($("#paramsPlan\\.plan_year").val()==''){
			alert('年份不能为空');
			return;
		}
		if($("#paramsPlan\\.clazz\\.clazz_id").val()=='0'){
			alert('班级不能为空');
			return;
		}
		if($("#paramsPlan\\.course\\.course_id").val()=='0'){
			alert('课程不能为空');
			return;
		}
		if($("#paramsPlan\\.user\\.user_id").val()=='0'){
			alert('教师不能为空');
			return;
		}
		$("#info").attr('action','Admin_savePlan.action').submit();
			 
	});
	
});
</script>
<style type="text/css">
</style>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">教学计划&gt;&gt;<s:if test="#attr.plan!=null && #attr.plan.plan_id!=0">编辑</s:if><s:else>添加</s:else>教学计划</span>
</div>
<form id="info" name="info" action="Admin_addPlan.action" method="post">   
<s:hidden id="paramsPlan.plan_id" name="paramsPlan.plan_id" value="%{#attr.plan.plan_id}" /> 
<table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
  <tr> 
     <td height="24">
       <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%"> 
            <TR>
              <TD height="24" class="edittitleleft">&nbsp;</TD>
              <TD class="edittitle"><s:if test="#attr.plan!=null && #attr.plan.plan_id!=0">编辑</s:if><s:else>添加</s:else>教学计划</TD>
              <TD class="edittitleright">&nbsp;</TD>
            </TR>
        </TABLE>
     </td>
   </tr>
   <tr>
     <td height="1" bgcolor="#8f8f8f"></td>
   </tr>
   <tr>
     <td >
     <table width="100%" align="center" cellpadding="1" cellspacing="1" class="editbody">
       <tr>
          <td width="35%" align="right" style="padding-right:5px"><font color="red">*</font> 年份：</td>
          <td width="65%"> 
           <s:textfield name="paramsPlan.plan_year" id="paramsPlan.plan_year" value="%{#attr.plan.plan_year}" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>
          </td> 
	   </tr>
	   <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 学期：</td>
          <td>
         	<s:select name="paramsPlan.plan_year_half" id="paramsPlan.plan_year_half" list="#{1:'上半年',2:'下半年'}"   value="%{#attr.plan.plan_year_half}"
         		listKey="key" listValue="value" emptyOption="false"  cssStyle="width:155px"></s:select>
          </td>
	   </tr>
	   <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 班级：</td>
          <td>
          	<s:select id="paramsPlan.clazz.clazz_id" name="paramsPlan.clazz.clazz_id" list="#attr.clazzs" listKey="clazz_id" value="%{#attr.plan.clazz.clazz_id}"
          		listValue="clazz_name" headerKey="0" headerValue="请选择" cssStyle="width:155px"></s:select>
          </td>
	   </tr>
	   <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 课程：</td>
          <td>
          	<s:select name="paramsPlan.course.course_id" id="paramsPlan.course.course_id" list="#attr.courses" value="%{#attr.plan.course.course_id}" 
          		listKey="course_id" listValue="course_name" headerKey="0" headerValue="请选择" cssStyle="width:155px"></s:select>
          </td>
        </tr>
	    <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 教师：</td>
          <td>
          	<s:select name="paramsPlan.user.user_id" id="paramsPlan.user.user_id" list="#attr.users" value="%{#attr.plan.user.user_id}" 
          		listKey="user_id" listValue="real_name" headerKey="0" headerValue="请选择" cssStyle="width:155px"></s:select>
          </td>
        </tr>
        <tr>
          <td align="right" style="padding-right:5px">备注：</td>
          <td>
          	<s:textarea name="paramsPlan.note" id="paramsPlan.note" value="%{#attr.plan.note}" cssStyle="width:300px;height:60px;">
          	</s:textarea>
          </td>
        </tr> 
     </table>
     </td>
   </tr>  
   <tr>
     <td>
       <table width="100%" align="center" cellpadding="0" cellspacing="0" class="editbody">
        <tr class="editbody">
          <td align="center" height="30">
          	<s:if test="#attr.plan!=null && #attr.plan.plan_id!=0">
          	<input type="button" id="editBtn" Class="btnstyle" value="编 辑"/> 
          	</s:if>
          	<s:else>
          	<input type="button" id="addBtn" Class="btnstyle" value="添 加" />
          	</s:else>
            &nbsp;<label style="color:red">${tip}</label>
          </td>
        </tr>
      </table>
     </td>
   </tr>
</table>
</form>
</body>
</html>