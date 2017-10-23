<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教学计划信息</title>
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var tempClassName="";
function tr_mouseover(obj) 
{ 
	tempClassName=obj.className;
	obj.className="list_mouseover";
}
function tr_mouseout(obj)      
{ 
	obj.className=tempClassName;
}      
function CheckAll(obj) 
{
	var checks=document.getElementsByName("chkid");
    for (var i=0;i<checks.length;i++)
	{
	    var e = checks[i];
	    e.checked = obj.checked;
	}
    
}

function serch()
{
   document.info.action="Admin_listPlans.action";
   document.info.submit();
}
function del()
{
	var checks=document.getElementsByName("chkid");
    var ids="";
	for (var i=0;i<checks.length;i++)
    {
        var e = checks[i];
		if(e.checked==true)
		{
		  if(ids=="")
		  {
		    ids=ids+e.value;
		  }
		  else
		  {
		    ids=ids+","+e.value;
		  }
		}
    }
    if(ids=="")
    {
       alert("请至少选择一个要删除的教学计划！");
       return false;
    }
    if(confirm('确认删除吗!?'))
    {
       document.info.action="Admin_delPlans.action?paramsPlan.ids="+ids;
       document.info.submit();
    }
    
}
function GoPage()
{
  var pagenum=document.getElementById("goPage").value;
  var patten=/^\d+$/;
  if(!patten.exec(pagenum))
  {
    alert("页码必须为大于0的数字");
    return false;
  }
  document.getElementById("pageNo").value=pagenum;
  document.info.action="Admin_listPlans.action";
  document.info.submit();
}
function ChangePage(pagenum)
{
  document.getElementById("pageNo").value=pagenum;
  document.info.action="Admin_listPlans.action";
  document.info.submit();
}
</script>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">教学计划&gt;&gt;教学计划查询</span>
</div>
<form name="info" id="info" action="Admin_listPlans.action" method="post">
<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="10px">&nbsp;</td></tr>
  <tr>
    <td width="">教学计划列表</td>
    <td width="" align="right">
            年份：
      <input type="text" style="width:50px;" id="paramsPlan.plan_year" name="paramsPlan.plan_year" value="${paramsPlan.plan_year}" class="inputstyle" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>&nbsp;
      <s:select name="paramsPlan.plan_year_half" id="paramsPlan.plan_year_half" list="#{1:'上半年',2:'下半年'}" value="%{#attr.paramsPlan.plan_year_half}" listKey="key" listValue="value" headerKey="0" headerValue="选择学期" cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
            班级：
      <s:select name="paramsPlan.clazz.clazz_id" id="paramsPlan.clazz.clazz_id" list="#attr.clazzs" value="%{#attr.paramsPlan.clazz.clazz_id}" listKey="clazz_id" listValue="clazz_name" headerKey="0" headerValue="选择班级"  cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
            课程：
      <s:select name="paramsPlan.course.course_id" id="paramsPlan.course.course_id" list="#attr.courses" value="%{#attr.paramsPlan.course.course_id}" listKey="course_id" listValue="course_name" headerKey="0" headerValue="选择课程"  cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
            姓名：
      <input type="text" style="width:80px;" id="paramsPlan.user.real_name" name="paramsPlan.user.real_name" value="${paramsPlan.user.real_name}" class="inputstyle"/>&nbsp;&nbsp;
      <input type="button" value="搜索" onclick="serch();" class="btnstyle"/>&nbsp;
      <input type="button" value="增加" onclick="window.location='Admin_addPlanShow.action';" class="btnstyle"/> &nbsp;
      <input type="button" value="删除" onclick="del();" class="btnstyle"/>
    </td>
  </tr>
  <tr><td colspan="2" height="2px"></td></tr>  
</table>
<table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr class="listtitle">
     <td width="40" align="center"><input type="checkbox" onclick="CheckAll(this);" style="vertical-align:text-bottom;" title="全选/取消全选"/></td>
     <td width="40" align="center">序号</td>
     <td width="" align="center">年份</td>
     <td width="" align="center">上/下半年</td>
     <td width="" align="center">班级</td>
     <td width="" align="center">课程</td>
     <td width="" align="center">姓名</td>
     <td width="" align="center">操作</td>
   </tr>
   <s:if test="#attr.plans!=null && #attr.plans.size()>0">
   <s:iterator value="#attr.plans" id="plan" status="status">
   <tr class="<s:if test='(#status.index + 1)%2==0'>list1</s:if><s:else>list0</s:else>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)"> 
     <td width="" align="center"><s:checkbox name="chkid" fieldValue="%{#plan.plan_id}" cssStyle="vertical-align:text-bottom;"/></td>
     <td width="" align="center"><s:property value="#status.index+#attr.paramsPlan.start+1"/></td>
     <td width="" align="center"><s:property value="#plan.plan_year"/></td>
     <td width="" align="center"><s:property value="#plan.plan_year_halfDesc"/>&nbsp;</td>
     <td width="" align="center"><s:property value="#plan.clazz.clazz_name"/></td>
     <td width="" align="center"><s:property value="#plan.course.course_name"/></td>
     <td width="" align="center"><s:property value="#plan.user.real_name"/></td>
     <td width="" align="center">
       <img src="images/edit.png"/>&nbsp;<s:a href="Admin_editPlan.action?paramsPlan.plan_id=%{#plan.plan_id}">编辑</s:a>
     </td>
   </tr> 
   </s:iterator>
   </s:if>
   <s:else>
   <tr>
     <td height="60" colspan="8" align="center"><b>&lt;不存在教学计划信息&gt;</b></td>
   </tr>
   </s:else>
   
</table>
<jsp:include page="page.jsp"></jsp:include>
</form> 
</body>
</html>