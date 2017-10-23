<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生总分分布</title>
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="highcharts/js/modules/exporting.js"></script>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/Common.js"></script>
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

$(document).ready(function(){
	var chart1 = null;
	var num = /^\d+(\.\d+)?$/;
	$("#search").bind("click",function(){
		var score_year = $("#paramsScore\\.score_year").val();
	    var score_year_half = $("#paramsScore\\.score_year_half").val();
	    var score_start = $("#paramsScore\\.score_start").val();
	    var score_end = $("#paramsScore\\.score_end").val();
	    var major_id = $("#paramsScore\\.major_id").val();
	    var classes_id = $("#paramsScore\\.classes_id").val();
	    if(score_year=='' || score_year_half=='0'){
		   alert("年份、学期不能为空");
		   return;
	    }
	    if(!num.exec(score_start) || !num.exec(score_end)){
	       alert("及格分和满分必须为非空数字");
		   return;
	    }
	    if(chart1!=null){
			chart1.destroy();
		}
	    $("#op").show();
	    
	    var postParams={
	    		'paramsScore.score_year':score_year,
	    		'paramsScore.score_year_half':score_year_half,
	    		'paramsScore.major_id':major_id,
	    		'paramsScore.classes_id':classes_id,
	    		'paramsScore.score_start':score_start,
	    		'paramsScore.score_end':score_end
	    };
	    $.post('listSumScoresSection.action',postParams,
  				function(responseObj) {
  						if(responseObj.success) {	
  							var x=responseObj.data.x;
  						    var y=responseObj.data.y;
  						    var options1 = getDisasterOptions();
	  						options1.chart.renderTo="Chart";
	  						options1.chart.borderWidth=0;
	  						options1.title.text="学生总分分布情况";
	  						options1.xAxis.max = x.length+4;
	  						options1.xAxis.labels.formatter = function(){  
	  							var arr = x;  
	  							if(this.value>=arr.length || this.value<0)return "";
	  							return arr[this.value]; 
	  						}
	  						options1.yAxis.title.text="学生人数（人）";
	  						options1.series=[{
	  							name:'学生总分分布情况统计',
	  							data:y
	  						}];
	  						options1.plotOptions.column.dataLabels.formatter = function(){  
								return this.y; 
							} 
	  						
	  						var pieData = [];
	  						for(var i=0;i<x.length;i++){
	  							pieData[i] = {
	  									name:x[i],
	  									x:i,
	  									y:y[i]
	  								};
	  						}
	  						options1.series[options1.series.length]={
  								type: 'pie',
  								name: 'Total consumption',
  								zIndex:1000,
  								data:pieData,
  								center: ["75%", 150],
  								size: 150,
  								showInLegend: false,
  								dataLabels: {
  									enabled: true,
  									formatter:function(){  
  										return this.point.name+"<br/>"+to2bits(this.percentage)+"%";  
  									} 
  								}
  							};
	  						
	  						options1.tooltip.formatter = function(){  
								if(this.series.name=="Total consumption"){
									return '<b>'+ this.point.name +'</b><br/>学生人数: '+ this.y+"人"; 
								}
								var arr = x;  
								return '<b>'+ arr[this.x] +'</b><br/>'+'学生人数: '+ this.y+"人";
							} 
	  						
							options1.legend.enabled=false;
							options1.plotOptions.column.pointWidth=20;
							
							$("#op").hide();
					  		chart1 = new Highcharts.Chart(options1);
	  						
  						}else  if(responseObj.err.msg){
  							 alert('失败：'+responseObj.err.msg);
  							 $("#op").hide();
  						}else{
  							 alert('失败：服务器异常！');
  							 $("#op").hide();
  						}	
  		},'json');
	});
});
</script>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">成绩统计&gt;&gt;学生总分分布</span>
</div>
<form name="info" id="info" action="Admin_listSingleScoresSection.action" method="post">
<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="10px">&nbsp;</td></tr>
  <tr>
    <td width="">学生总分分布</td>
    <td width="" align="right">
             年份：
      <input type="text" style="width:50px;" id="paramsScore.score_year" name="paramsScore.score_year" value="${paramsScore.score_year}" class="inputstyle" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>&nbsp;
      <s:select name="paramsScore.score_year_half" id="paramsScore.score_year_half" list="#{1:'上半年',2:'下半年'}" value="%{#attr.paramsScore.score_year_half}" listKey="key" listValue="value" headerKey="0" headerValue="选择学期" cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
      <s:select name="paramsScore.major_id" id="paramsScore.major_id" list="#attr.majors" value="%{#attr.paramsScore.major_id}" listKey="major_id" listValue="major_name" headerKey="0" headerValue="选择专业"  cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
      <s:select name="paramsScore.classes_id" id="paramsScore.classes_id" list="#attr.classess" value="%{#attr.paramsScore.classes_id}" listKey="classes_id" listValue="classes_name" headerKey="0" headerValue="选择班级"  cssClass="selectstyle" cssStyle="width:80px"></s:select>&nbsp;
             及格分：
      <input type="text" style="width:35px;" id="paramsScore.score_start" name="paramsScore.score_start" value="${paramsScore.score_start}" class="inputstyle"/>&nbsp;
             满分：
      <input type="text" style="width:35px;" id="paramsScore.score_end" name="paramsScore.score_end" value="${paramsScore.score_end}" class="inputstyle"/>&nbsp;&nbsp;
      <input id="search" type="button" value="搜索" class="btnstyle"/>&nbsp;
    </td>
  </tr>
  <tr><td colspan="2" height="2px"></td></tr>  
</table>
<table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr>
   	<td id="Chart" width="100%" height="350px" align="center">
   		<span id="op" style="display:none;><img sec="images/loading004.gif"/></span>
   	</td>
   </tr>
</table>
</form>
</body>--%>
</html>