<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成绩信息</title>
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
   document.info.action="Admin_listScoresSum.action";
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
       alert("请至少选择一个要删除的成绩！");
       return false;
    }
    if(confirm('确认删除吗!?'))
    {
       document.info.action="Admin_delScores.action?paramsScore.ids="+ids;
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
  document.info.action="Admin_listScoresSum.action";
  document.info.submit();
}
function ChangePage(pagenum)
{
  document.getElementById("pageNo").value=pagenum;
  document.info.action="Admin_listScoresSum.action";
  document.info.submit();
}
$(document).ready(function(){
	$("#export").bind('click',function(){
		var aQuery = $("#info").serialize();  
		$("#info").attr('target','_blank').attr('action','exportScoresSum.action').submit();;
	});
});
</script>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">总分成绩&gt;&gt;总分成绩查询</span>
</div>
<form name="info" id="info" action="Admin_listScoresSum.action" method="post">
<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="10px">&nbsp;</td></tr>
  <tr>
    <td width="">总分成绩列表</td>
    <td width="" align="right">
            年份：
      <input type="text" style="width:50px;" id="paramsScore.score_year" name="paramsScore.score_year" value="<%--${paramsScore.score_year}--%>" class="inputstyle" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>&nbsp;
      <s:select name="paramsScore.score_year_half" id="paramsScore.score_year_half" list="#{1:'上半年',2:'下半年'}" value="" listKey="key" listValue="value" headerKey="0" headerValue="选择学期" cssClass="selectstyle" cssStyle="width:70px"></s:select>&nbsp;
      <s:select name="paramsScore.user.clazz.clazz_id" id="paramsScore.user.clazz.clazz_id" list="<%--#attr.clazzs--%>" value="" listKey="clazz_id" listValue="clazz_name" headerKey="0" headerValue="选择班级"  cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
      <s:select name="paramsScore.course.course_id" id="paramsScore.course.course_id" list="<%--#attr.courses--%>" value="<%--{#attr.paramsScore.course.course_id}--%>" listKey="course_id" listValue="course_name" headerKey="0" headerValue="选择课程"  cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
            姓名：
      <input type="text" style="width:70px;" id="paramsScore.user.real_name" name="paramsScore.user.real_name" value="<%--${paramsScore.user.real_name}--%>" class="inputstyle"/>&nbsp;
      <input type="button" value="搜索" onclick="serch();" class="btnstyle"/>&nbsp;
      <input type="button" value="导出" id="export" class="btnstyle"/>
    </td>
  </tr>
  <tr><td colspan="2" height="2px"></td></tr>  
</table>
<table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr class="listtitle">
     <td width="40" align="center">序号</td>
     <td width="" align="center">姓名</td>
     <td width="" align="center">班级</td>
     <td width="" align="center">年份</td>
     <td width="" align="center">学期</td>
     <td width="" align="center">总分成绩</td>
   </tr>
   <s:if test="#attr.scores!=null && #attr.scores.size()>0">
   <s:iterator value="" id="score" status="status">
   <tr class="<s:if test='(#status.index + 1)%2==0'>list1</s:if><s:else>list0</s:else>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)"> 
     <td width="" align="center"><s:property value="<%--#status.index+#attr.paramsScore.start+1--%>"/></td>
     <td width="" align="center"><s:property value="<%--#score.real_name--%>"/></td>
     <td width="" align="center"><s:property value="<%--#score.clazz_name--%>"/></td>
     <td width="" align="center"><s:property value="<%--#score.score_year--%>"/></td>
     <td width="" align="center"><s:property value="<%--#score.score_year_halfDesc--%>"/>&nbsp;</td>
     <td width="" align="center"><s:property value="<%--#score.score_value--%>"/></td>
   </tr> 
   </s:iterator>
   </s:if>
   <s:else>
   <tr>
     <td height="60" colspan="13" align="center"><b>&lt;不存在总分成绩信息&gt;</b></td>
   </tr>
   </s:else>
   
</table>
<jsp:include page="page.jsp"></jsp:include>
</form> 
</body>
</html>