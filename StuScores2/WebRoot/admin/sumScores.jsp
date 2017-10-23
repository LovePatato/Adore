<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生总分排名</title>
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
<%--var tempClassName="";
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
	var score_year = document.getElementById("paramsScore.score_year").value;
    var score_year_half = document.getElementById("paramsScore.score_year_half").value;
    if(score_year=='' || score_year_half=='0'){
	   alert("年份、学期不能为空");
	   return;
    }
   document.info.action="Admin_listSumScores.action";
   document.info.submit();
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
  document.info.action="Admin_listSumScores.action";
  document.info.submit();
}
function ChangePage(pagenum)
{
  document.getElementById("pageNo").value=pagenum;
  document.info.action="Admin_listSumScores.action";
  document.info.submit();
}
</script>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">${admin.user_type==1?'成绩查询>>总分排名':'成绩统计>>学生总分排名'}</span>
</div>
<form name="info" id="info" action="Admin_listSumScores.action" method="post">
<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="10px">&nbsp;</td></tr>
  <tr>
    <td width="">总分排名（所有科目平均分：<s:property value="#attr.sumAvgScores"/>）</td>
    <td width="" align="right">
             年份：
      <input type="text" style="width:50px;" id="paramsScore.score_year" name="paramsScore.score_year" value="${paramsScore.score_year}" class="inputstyle" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>&nbsp;
      <s:select name="paramsScore.score_year_half" id="paramsScore.score_year_half" list="#{1:'上半年',2:'下半年'}" value="%{#attr.paramsScore.score_year_half}" listKey="key" listValue="value" headerKey="0" headerValue="选择学期" cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
      <s:if test="#attr.admin.user_type!=1">
      <s:select name="paramsScore.major_id" id="paramsScore.major_id" list="#attr.majors" value="%{#attr.paramsScore.major_id}" listKey="major_id" listValue="major_name" headerKey="0" headerValue="选择专业"  cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
      <s:select name="paramsScore.classes_id" id="paramsScore.classes_id" list="#attr.classess" value="%{#attr.paramsScore.classes_id}" listKey="classes_id" listValue="classes_name" headerKey="0" headerValue="选择班级"  cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
            姓名：
      <input type="text" style="width:70px;" id="paramsScore.real_name" name="paramsScore.real_name" value="${paramsScore.real_name}" class="inputstyle"/>&nbsp;
      </s:if>&nbsp;
      <input type="button" value="搜索" onclick="serch();" class="btnstyle"/>&nbsp;
    </td>
  </tr>
  <tr><td colspan="2" height="2px"></td></tr>  
</table>
<table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr class="listtitle">
     <td width="80" align="center">${admin.user_type==1?'班级排名':'总分排名'}</td>
     <td width="" align="center">姓名</td>
     <td width="" align="center">专业</td>
     <td width="" align="center">班级</td>
     <td width="" align="center">成绩</td>
     <td width="" align="center">年份</td>
     <td width="" align="center">上/下半年</td>
   </tr>
   <s:if test="#attr.scores!=null && #attr.scores.size()>0">
   <s:iterator value="#attr.scores" id="score" status="status">
   <tr class="<s:if test='(#status.index + 1)%2==0'>list1</s:if><s:else>list0</s:else>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)"> 
     <td width="" align="center"><s:property value="#score.score_index"/></td>
     <td width="" align="center" <s:if test="#score.real_name==#attr.admin.real_name">style="color:red"</s:if>>
     	 <s:property value="#score.real_name"/> 
     </td>
     <td width="" align="center"><s:property value="#score.major_name"/></td>
     <td width="" align="center"><s:property value="#score.classes_name"/></td>
     <td width="" align="center"><s:property value="#score.score_sum"/></td>  
     <td width="" align="center"><s:property value="#score.score_year"/></td>
     <td width="" align="center"><s:property value="#score.score_year_halfDesc"/></td>
   </tr> 
   </s:iterator>
   </s:if>
   <s:else>
   <tr>
     <td height="60" colspan="7" align="center"><b>&lt;不存在成绩信息&gt;</b></td>
   </tr>
   </s:else>
   
</table>
<jsp:include page="page.jsp"></jsp:include>
</form> --%>
</body>
</html>