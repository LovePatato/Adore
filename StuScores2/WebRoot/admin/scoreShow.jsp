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
   var num = /^\d+(\.\d+)?$/;
   var score_valueMin = document.getElementById("score_valueMin").value;
   var score_valueMax = document.getElementById("score_valueMax").value;
   if(score_valueMin!='' && !num.exec(score_valueMin)){
	   alert('成绩范围必须都是数字');
	   return;
   }
   if(score_valueMax!='' && !num.exec(score_valueMax)){
	   alert('成绩范围必须都是数字');
	   return;
   }
   if(score_valueMin!='' && score_valueMax!='' && score_valueMin>score_valueMax){
	   alert('成绩范围设置有误');
	   return;
   }
   if(score_valueMin==''){
	   document.getElementById("score_valueMin").value=0;
   }
   if(score_valueMax==''){
	   document.getElementById("score_valueMax").value=0;
   }
   document.info.action="Admin_listScores.action";
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
  var score_valueMin = document.getElementById("score_valueMin");
  var score_valueMax = document.getElementById("score_valueMax");
  if(score_valueMin.value==''){
	  score_valueMin.value=0;
  }
  if(score_valueMax.value==''){
	  score_valueMax.value=0;
  }
  document.info.action="Admin_listScores.action";
  document.info.submit();
}
function ChangePage(pagenum)
{
  document.getElementById("pageNo").value=pagenum;
  var score_valueMin = document.getElementById("score_valueMin");
  var score_valueMax = document.getElementById("score_valueMax");
  if(score_valueMin.value==''){
	  score_valueMin.value=0;
  }
  if(score_valueMax.value==''){
	  score_valueMax.value=0;
  }
  document.info.action="Admin_listScores.action";
  document.info.submit();
}
$(document).ready(function(){
	$("#export").bind('click',function(){
		var aQuery = $("#info").serialize();  
		$("#info").attr('target','_blank').attr('action','exportScores.action').submit();;
	});
});
</script>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">成绩管理&gt;&gt;成绩查询</span>
</div>
<form name="info" id="info" action="Admin_listScores.action" method="post">
<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="10px">&nbsp;</td></tr>
  <tr>
    <td width="">成绩列表</td>
    <td width="" align="right">
            年份：
      <input type="text" style="width:50px;" id="paramsScore.score_year" name="paramsScore.score_year" value="${paramsScore.score_year}" class="inputstyle" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>&nbsp;
      <s:select name="paramsScore.score_year_half" id="paramsScore.score_year_half" list="#{1:'上半年',2:'下半年'}" value="%{#attr.paramsScore.score_year_half}" listKey="key" listValue="value" headerKey="0" headerValue="选择学期" cssClass="selectstyle" cssStyle="width:70px"></s:select>&nbsp;
      <s:select name="paramsScore.user.clazz.clazz_id" id="paramsScore.user.clazz.clazz_id" list="#attr.clazzs" value="%{#attr.paramsScore.user.clazz.clazz_id}" listKey="clazz_id" listValue="clazz_name" headerKey="0" headerValue="选择班级"  cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
      <s:select name="paramsScore.course.course_id" id="paramsScore.course.course_id" list="#attr.courses" value="%{#attr.paramsScore.course.course_id}" listKey="course_id" listValue="course_name" headerKey="0" headerValue="选择课程"  cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
            姓名：
      <input type="text" style="width:70px;" id="paramsScore.user.real_name" name="paramsScore.user.real_name" value="${paramsScore.user.real_name}" class="inputstyle"/>&nbsp;
            成绩：
      <input type="text" style="width:40px;" id="score_valueMin" name="paramsScore.score_valueMin" value="${paramsScore.score_valueMin==0?'':paramsScore.score_valueMin}" class="inputstyle"/>
      -
      <input type="text" style="width:40px;" id="score_valueMax" name="paramsScore.score_valueMax" value="${paramsScore.score_valueMax==0?'':paramsScore.score_valueMax}" class="inputstyle"/>&nbsp;&nbsp;
      <input type="button" value="搜索" onclick="serch();" class="btnstyle"/>&nbsp;
      <s:if test="#attr.admin.user_type==2">
      <input type="button" value="增加" onclick="window.location='Admin_addScoreShow.action';" class="btnstyle"/>&nbsp; 
      <input type="button" value="删除" onclick="del();" class="btnstyle"/>&nbsp;
      </s:if>
      <input type="button" value="导出" id="export" class="btnstyle"/>
    </td>
  </tr>
  <tr><td colspan="2" height="2px"></td></tr>  
</table>
<table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr class="listtitle">
     <td width="40" align="center"><input type="checkbox" onclick="CheckAll(this);" style="vertical-align:text-bottom;" title="全选/取消全选"/></td>
     <td width="40" align="center">序号</td>
     <td width="" align="center">姓名</td>
     <td width="" align="center">班级</td>
     <td width="" align="center">年份</td>
     <td width="" align="center">学期</td>
     <td width="" align="center">课程</td>
     <td width="" align="center">成绩</td>
     <s:if test="#attr.admin.user_type==2">
     <td width="" align="center">操作</td>
     </s:if>
<%--       <s:else>
           <td width="" align="center">查看平均分</td>
       </s:else>--%>
   </tr>
   <s:if test="#attr.scores!=null && #attr.scores.size()>0">
   <s:iterator value="#attr.scores" id="score" status="status">
   <tr class="<s:if test='(#status.index + 1)%2==0'>list1</s:if><s:else>list0</s:else>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)"> 
     <td width="" align="center"><s:checkbox name="chkid" fieldValue="%{#score.score_id}" cssStyle="vertical-align:text-bottom;"/></td>
     <td width="" align="center"><s:property value="#status.index+#attr.paramsScore.start+1"/></td>
     <td width="" align="center"><s:property value="#score.user.real_name"/></td>
     <td width="" align="center"><s:property value="#score.user.clazz.clazz_name"/></td>
     <td width="" align="center"><s:property value="#score.score_year"/></td>
     <td width="" align="center"><s:property value="#score.score_year_halfDesc"/>&nbsp;</td>
     <td width="" align="center"><s:property value="#score.course.course_name"/></td>
     <td width="" align="center"><s:property value="#score.score_value"/></td>  
     <s:if test="#attr.admin.user_type==2">
     <td width="" align="center">
       <img src="images/edit.png"/>&nbsp;<s:a href="Admin_editScore.action?paramsScore.score_id=%{#score.score_id}">编辑</s:a>
     </td>
      </s:if>
<%--       <s:else>
           <td width="100" align="center"><button id="showAverage">查看班级平均</button></td>
       </s:else>--%>
   </tr> 
   </s:iterator>
   </s:if>
   <s:else>
   <tr>
     <td height="60" colspan="10" align="center"><b>&lt;不存在成绩信息&gt;</b></td>
   </tr>
   </s:else>
   
</table>
<jsp:include page="page.jsp"></jsp:include>
</form> 
</body>

<script>
    function showAverage() {
        
    }
</script>
</html>