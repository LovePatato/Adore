<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>单科成绩分布</title>
    <script src="/admin/jquery/jquery.js" type="application/javascript"></script>
    <script src="/admin/bootstrap-3.3.7/js/bootstrap.js" type="application/javascript"></script>
    <script type="application/javascript" src="/admin/echarts/echarts.min.js"></script>
    <link href="/admin/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        .form-control{
            border-radius: 0px;
        }
    </style>
</head>
<body class="container">
<div class="row base-margin" id="query" >
    <ol class="breadcrumb" style="margin-bottom: 0px">
        <li><strong><span style="color: #288CC8">单科成绩统计</span></strong></li>
    </ol>
    <form class="form-inline" role="form" style="float: left; width: 100%" method="post" id="queryForm" style="border-radius: 0px">
        <div class="form-group" style="padding-left: 190px">
            <label for="year">学年:</label>
            <select class="form-control" id="year" name="year">
                <option value="0000">===请选择===</option>
            </select>
        </div>
        <div class="form-group">
            <label for="term">学期</label>
            <select class="form-control" id="term" name="term">
                <option value="-1">===请选择===</option>
                <option value="1">上学期</option>
                <option value="0">下学期</option>
            </select>
        </div>
        <div class="form-group">
            <label>班级:</label>
            <select class="form-control" id="class" name="class">
                <option value="0">===请选择===</option>
            </select>
        </div>
        <div class="form-group">
            <label>课程:</label>
            <select class="form-control" id="course" name="course" style="border-radius: 0px">
                <option value="0" name="course">===请选择===</option>
            </select>
        </div>
        <div class="form-group">
            <button type="button" id="queryBtn" onclick="doQuery();" class="btn btn-primary btn-sm"
                    style="background-color: #288CC8">查询
            </button>
        </div>
    </form>
</div>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="data" style="margin-top : 20px;height:400px;border: 2px solid #288CC8" class="row pull-left col-md-6"></div>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="jigelv" style="margin-top : 20px;height:400px;border: 2px solid #288CC8"  class="row pull-right col-md-6"></div>
<p class="text-info pull-left">班级平均分:<span id="avarage"></span></p>
<p class="text-info pull-right">班级及格率:<span id="passRatio"></span></p>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts图表
    var myChart1 = echarts.init(document.getElementById('data'));
    var myChart2 = echarts.init(document.getElementById('jigelv'));

    option1 = {
        title: {
            text: '',
            subtext: '',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: []
        },
        series: [
            {
                name: '人数比例',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: 0, name: '90+'},
                    {value: 0, name: '80 - 89'},
                    {value: 0, name: '70 - 79'},
                    {value: 0, name: '60 - 69'},
                    {value: 0, name: '60-'},
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    option2 = {
        title: {
            text: '',
            subtext: '',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: []
        },
        series: [
            {
                name: '人数比例',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: 0, name: '及格'},
                    {value: 0, name: '不及格'},
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

</script>
<script type="text/javascript">
    function doQuery() {
        $.ajax('/admin/SingleRanks.action', {
            type: 'get',
            dataType: 'json',
            contentType: "application/x-www-form-urlencoded",
            data: {
                year: $("#year").val(),
                term: $("#term").val(),
                classId: $("#class").val(),
                courseId: $("#course").val(),
            },
            success: function (data) {
                console.log(data);
                option1.title.text = $("#class").find("option:selected").text() + '-' + $('#course').find("option:selected").text();
                //opt2
                option2.title.text = $("#class").find("option:selected").text() + '-' + $('#course').find("option:selected").text();

                option1.title.subtext = '单科成绩分布图';
                //opt2
                option2.title.subtext = '单科成绩及格率';

                var arrays1 = ['60分以下', '60 - 69分', '70 -79', '80 - 89', '90+'];
                //opt2
                var arrays2= ['及格', '不及格'];

                option1.legend.data.push(arrays1);
                //opt2
                option2.legend.data.push(arrays2);
                var rows = data.rows;
                var low60 = 0;
                var b6070 = 0;
                var b7080 = 0;
                var b8090 = 0;
                var over90 = 0;

                var totalScore = 0;
                var non_pass = 0;
                for (var i in rows) {
                    var row = rows[i];
                    if (row['value'] < 60) {
                        //option.series[0].data[0].value =
                        totalScore += row['value'];
                        non_pass ++;
                        low60++;
                    }
                    if (row['value'] >= 60 && row['value'] < 70) {
                        //option.series[0].data[0].value =
                        totalScore += row['value'];
                        b6070++;
                    }
                    if (row['value'] >= 70 && row['value'] < 80) {
                        //option.series[0].data[0].value =
                        totalScore += row['value'];
                        b7080++;
                    }
                    if (row['value'] >= 80 && row['value'] < 90) {
                        //option.series[0].data[0].value =
                        totalScore += row['value'];
                        b8090++;
                    }
                    if (row['value'] >= 90) {
                        totalScore += row['value'];
                        over90++;
                    }
                }
                option1.series[0].data[0].value = over90;
                option1.series[0].data[1].value = b8090;
                option1.series[0].data[2].value = b7080;
                option1.series[0].data[3].value = b6070;
                option1.series[0].data[4].value = low60;

                option2.series[0].data[0].value = over90 + b8090 + b7080 + b6070;
                option2.series[0].data[1].value = low60;
                // 为echarts对象加载数据
                myChart1.setOption(option1);
                myChart2.setOption(option2);
                var avarage = totalScore / rows.length;
                avarage = avarage.toFixed(2);
                $('#avarage').text( avarage);
                var passRatio = (rows.length - non_pass)/rows.length;
                passRatio = passRatio.toFixed(2);
                $('#passRatio').text( passRatio* 100 + '%');
            }
        })
    }


    function queryParams(params) {
        var param = {
            year: $("#year").val(),
            term: $("#term").val(),
            classId: $("#class").val(),
            courseId: $("#course").val(),
            limit: this.limit, // 页面大小
            offset: this.offset, // 页码
            pageindex: this.pageNumber,
            pageSize: this.pageSize
        }
        return param;
    }

    // 用于server 分页，表格数据量太大的话 不想一次查询所有数据，可以使用server分页查询，数据量小的话可以直接把sidePagination: "server"  改为 sidePagination: "client" ，同时去掉responseHandler: responseHandler就可以了，
    function responseHandler(res) {
        if (res) {
            return {
                "rows": res.rows,
                "total": res.total
            };
        } else {
            return {
                "rows": [],
                "total": 0
            };
        }
    }
    $(function () {
        //加载 学年
        $.ajax('/admin/listYears.action', {
            type: 'get',
            dataType: 'json',
            success: function (data) {
                for (var i in data) {
                    $('<option value="' + data[i] + '">' + data[i] + '</option>').appendTo('#year');
                }
            }
        });
        //加载 班级
        $.ajax('/admin/listClasses.action', {
            type: 'get',
            dataType: 'json',
            success: function (data) {
                for (var i in data) {
                    var d = data[i];
                    $('<option value="' + d.classId + '">' + d.className + '</option>').appendTo('#class');
                }
            }
        });
        //加载 课程
        $.ajax('/admin/listCourses.action', {
            type: 'get',
            dataType: 'json',
            success: function (data) {
                for (var i in data) {
                    var d = data[i];
                    $('<option value="' + d.courseId + '">' + d.courseName + '</option>').appendTo('#course');
                }
            }
        });
    })
</script>
</body>
</html>