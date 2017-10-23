<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>头部页面</title>
<link type="text/css" href="css/style.css" rel="stylesheet" />
<script language="javascript" type="text/javascript" src=""></script>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<link href="/admin/font-awesome-4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">

    <script language="javascript" type="text/javascript">

$(document).ready(function(){
	 
	 $("#logOut").bind('click',function(){
		if(confirm("确认退出？")){
			window.parent.location.href="login.jsp";
		}
		 
	 });
	
});	 
	
</script>
</head>

<body>
<div class="top">
	<div class="top1">
    	<div class="logo">
			<IMG src="images/logo3.png" width="480"/>
		</div>
        <div class="top_info"><a href="modifyInfo.jsp" target="MainFrame" class="fa fa-info-circle">个人信息</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="modifyPwd.jsp" target="MainFrame" class="fa fa-lock">修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a id="logOut" href="#" class="fa fa-reply-all">退出系统</a></div>
    </div>
    <div class="top2">
    	<div class="shijian" id="timeDiv"></div>
        <div class="user"><span class="fa fa-user">${admin.user_typeDesc }</span>：${admin.user_type==3?'admin':admin.real_name}</div>
    </div>
</div>
</body>
</html>
<script language="javascript">
function calender()
{
 var time=new Date();
 var year=time.getFullYear();
 var month=toPair(time.getMonth()+1);
 var day=toPair(time.getDate());
 var hour=toPair(time.getHours());
 var minute=toPair(time.getMinutes());
 var second=toPair(time.getSeconds());
 var dateweek;

switch(time.getDay())
{
	case 0:dateweek = "星期日";break;
	case 1:dateweek = "星期一";break;
	case 2:dateweek = "星期二";break;
	case 3:dateweek = "星期三";break;
	case 4:dateweek = "星期四";break;
	case 5:dateweek = "星期五";break;
	case 6:dateweek = "星期六";break;
}

 var timeDiv = document.getElementById('timeDiv');
 timeDiv.innerHTML = "<div class='fa fa-calendar' style='margin-top: 5px;padding-left: 30px'>"+year+"年"+month+"月"+day+"日　"+hour+":"+minute+":"+second+"　"+dateweek + "</div>";
 var mytime=setTimeout("calender()",1000);
}

calender();

function toPair(str){
	if(Number(str)<10){
		return "0"+str;
	}else{
		return str;
	}
}
</script>
