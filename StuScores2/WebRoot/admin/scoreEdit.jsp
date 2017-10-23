<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:if test="#attr.score!=null && #attr.score.score_id!=0">编辑</s:if><s:else>添加</s:else>成绩信息</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	
	var num = /^\d+(\.\d+)?$/;
	 $("#addBtn").bind('click',function(){
		
		if($("#score_year").val()==''){
			alert('年份不能为空');
			return;
		}
		if($("#course_id").val()=='0'){
			alert('课程不能为空');
			return;
		}
		if($("#paramsScore\\.user\\.user_id").val()=='0'){
			alert('学生不能为空');
			return;
		}
		if(!num.exec($("#paramsScore\\.score_value").val())){
			alert('成绩必须为数字');
			return;
		}
		$("#paramsScore\\.score_id").val(0);
		$("#info").attr('action','Admin_addScore.action').submit();
		 
	 });
	 
	 $("#editBtn").bind('click',function(){
		if($("#score_year").val()==''){
			alert('年份不能为空');
			return;
		}   
		if($("#course_id").val()=='0'){
			alert('课程不能为空');
			return;
		}
		if($("#paramsScore\\.user\\.user_id").val()=='0'){
			alert('学生不能为空');
			return;
		}
		if(!num.exec($("#paramsScore\\.score_value").val())){
			alert('成绩必须为数字');
			return;
		}
		$("#info").attr('action','Admin_saveScore.action').submit();
			 
	});
	
	var score_yearV = $("#score_year").val();
	$("#score_year").focus(function() {
		if($("#score_year").val()!=score_yearV){
			score_yearV = $("#score_year").val();
			var score_year = $("#score_year").val();
	    	var score_year_half = $("#score_year_half").val();
	    	loadCourse(score_year,score_year_half);
	    	
	    	var course_id = $("#course_id").val();
	    	loadStudent(score_year,score_year_half,course_id);
		}
	});
	 
	$("#score_year_half").change(function(){
    	var score_year = $("#score_year").val();
    	var score_year_half = $("#score_year_half").val();
    	loadCourse(score_year,score_year_half);
    }); 
	
	function loadCourse(score_year,score_year_half){
    	if(score_year!='' && score_year_half!='0'){
    		$("#course_id").empty();
    		$("#course_id").append("<option value='0'>请选择</option>"); 
    		$("#op1").show();
    		var postParams={'paramsCourse.score_year':score_year,'paramsCourse.score_year_half':score_year_half};
    		$.post('queryCourse.action',postParams,
  				function(responseObj) {
  						if(responseObj.success) {	
  							 var courses = responseObj.data.courses;
  							 if(courses!=null && courses.length>0){
  								for(var i=0;i<courses.length;i++){
  									if(course_id == courses[i].course_id){
  										$("#course_id").append("<option selected='selected' value='"+courses[i].course_id+"'>"+courses[i].course_name+"</option>"); 
  									}else{
  										$("#course_id").append("<option value='"+courses[i].course_id+"'>"+courses[i].course_name+"</option>"); 
  									}
  	  							 }
  							 }
  							 $("#op1").hide();
  						}else  if(responseObj.err.msg){
  							 alert('失败：'+responseObj.err.msg);
  							 $("#op1").hide();
  						}else{
  							 alert('失败：服务器异常！');
  							 $("#op1").hide();
  						}	
  			 },'json');
    	}
    }
	 
    $("#score_year_half,#course_id").change(function(){
    	var score_year = $("#score_year").val();
    	var score_year_half = $("#score_year_half").val();
    	var course_id = $("#course_id").val();
    	loadStudent(score_year,score_year_half,course_id);
    });
    
    function loadStudent(score_year,score_year_half,course_id){
    	if(score_year!='' && score_year_half!='0' && course_id!='0'){
    		$("#user_id").empty();
    		$("#user_id").append("<option value='0'>请选择</option>"); 
    		$("#op2").show();
    		var postParams={'paramsUser.score_year':score_year,'paramsUser.score_year_half':score_year_half,'paramsUser.course_id':course_id};
    		$.post('queryStudent.action',postParams,
  				function(responseObj) {
  						if(responseObj.success) {	
  							 var users = responseObj.data.users;
  							 if(users!=null && users.length>0){
  								for(var i=0;i<users.length;i++){
  									if(user_id == users[i].user_id){
  										$("#user_id").append("<option selected='selected' value='"+users[i].user_id+"'>"+users[i].real_name+"</option>"); 
  									}else{
  										$("#user_id").append("<option value='"+users[i].user_id+"'>"+users[i].real_name+"</option>"); 
  									}
  	  							 }
  							 }
  							 $("#op2").hide();
  						}else  if(responseObj.err.msg){
  							 alert('失败：'+responseObj.err.msg);
  							 $("#op2").hide();
  						}else{
  							 alert('失败：服务器异常！');
  							 $("#op2").hide();
  						}	
  			 },'json');
    	}
    }
    
    var score_year = "<s:property value='#attr.score.score_year'/>";
    var score_year_half = "<s:property value='#attr.score.score_year_half'/>";
 	var course_id = "<s:property value='#attr.score.course.course_id'/>";
 	var user_id = "<s:property value='#attr.score.user.user_id'/>";
 	loadCourse(score_year,score_year_half);
 	loadStudent(score_year,score_year_half,course_id);
	
});
</script>
<style type="text/css">
</style>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">成绩管理&gt;&gt;<s:if test="#attr.score!=null && #attr.score.score_id!=0">编辑</s:if><s:else>添加</s:else>成绩</span>
</div>
<form id="info" name="info" action="Admin_addScore.action" method="post">   
<s:hidden id="paramsScore.score_id" name="paramsScore.score_id" value="%{#attr.score.score_id}" /> 
<table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
  <tr> 
     <td height="24">
       <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%"> 
            <TR>
              <TD height="24" class="edittitleleft">&nbsp;</TD>
              <TD class="edittitle"><s:if test="#attr.score!=null && #attr.score.score_id!=0">编辑</s:if><s:else>添加</s:else>成绩</TD>
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
          <td width="35%"  align="right" style="padding-right:5px"><font color="red">*</font> 年份：</td>
          <td width="65%">
            <s:textfield name="paramsScore.score_year" id="score_year" value="%{#attr.score.score_year}" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>
          </td> 
       </tr>
       <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 学期：</td>
          <td>
          	<s:select name="paramsScore.score_year_half" id="score_year_half" list="#{1:'上半年',2:'下半年'}"  value="%{#attr.score.score_year_half}"
          		listKey="key" listValue="value" emptyOption="false"  cssStyle="width:155px"></s:select>
          </td>
       </tr>
       <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 课程：</td>
          <td>
          	<select id="course_id" name="paramsScore.course.course_id"  style="width:80px">
            	<option value="0">请选择</option>
            </select>&nbsp;
            <span id="op1" style="display:none;width:50px"><img src="images/loading001.gif"/></span>
          </td>
        </tr> 
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 学生：</td>
          <td colspan="3">
            <select id="user_id" name="paramsScore.user.user_id"  style="width:80px">
            	<option value="0">请选择</option>
            </select>&nbsp;
            <span id="op2" style="display:none;width:50px"><img src="images/loading001.gif"/></span>
          </td>
        </tr>   
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 成绩：</td>
          <td colspan="3">
            <s:textfield name="paramsScore.score_value" id="paramsScore.score_value" value="%{#attr.score.score_value}" />
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
          	<s:if test="#attr.score!=null && #attr.score.score_id!=0">
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