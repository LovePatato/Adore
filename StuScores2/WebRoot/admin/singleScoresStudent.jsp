<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>折线图</title>
    <script src="/admin/jquery/jquery.js" type="application/javascript"></script>
    <script src="/admin/bootstrap-3.3.7/js/bootstrap.js" type="application/javascript"></script>
    <script type="application/javascript" src="/admin/echarts/echarts.min.js"></script>
    <link href="/admin/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
    </script>
    <style type="text/css">
        .form-control{
            border-radius: 0px;
        }
    </style>
</head>
<body class="container">
<div class="row base-margin" id="query">
    <ol class="breadcrumb" style="margin-bottom: 0px">
        <li><strong><span style="color: #288CC8">单科成绩统计(折线,柱状)</span></strong></li>
    </ol>
    <form class="form-inline" role="form" style="float: left; width: 100%" method="post" id="queryForm">
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
            <select class="form-control" id="course" name="course">
                <option value="0" name="course">===请选择===</option>
            </select>
        </div>
        <div class="form-group">
            <button type="button" id="queryBtn" onclick="doQuery();" class="btn  btn-primary btn-sm"
                    style="background-color: #288CC8">查询
            </button>
        </div>
    </form>
</div>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="data" style="margin-top : 20px;height:400px;border: 2px solid #288CC8"></div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('data'));
    option = {
        title: {
            text: '某某班级',
            subtext: '单科成绩'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['分数']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    formatter: '{value}分'
                }
            }
        ],
        series: [
            {
                name: '分数',
                type: 'line',
                data: [],
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        }
                    }
                },
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
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
                option.title.text = $("#class").find("option:selected").text() + '-' + $('#course').find("option:selected").text();

                option.title.subtext = '单科成绩分布图';
                var rows = data.rows;
                var x = [];
                var y = [];
                for (var i in rows) {
                    var row = rows[i];
                    x.push(row['realName']);
                    y.push(row['value']);
                }
                option.xAxis[0].data = x;
                option.series[0].data = y;
                // 为echarts对象加载数据
                myChart.setOption(option);

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