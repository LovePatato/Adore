<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>success</title>
    <script src="/admin/jquery/jquery.js" type="application/javascript"></script>
    <script src="/admin/bootstrap-3.3.7/js/bootstrap.js" type="application/javascript"></script>
    <script src="/admin/bootstrap-table/bootstrap-table.js" type="application/javascript"></script>
    <script src="/admin/bootstrap-table/bootstrap-table-zh-CN.js" type="application/javascript"></script>
    <link href="/admin/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/admin/bootstrap-table/bootstrap-table.css" rel="stylesheet" type="text/css">

    <style>
        .form-control{
            border-radius: 0px;
        }
    </style>
</head>
<body>
<div class="row base-margin" id="query">
    <ol class="breadcrumb" style="margin-bottom: 0px">
        <li><strong><span style="color: #288CC8">单科成绩排名</span></strong></li>
    </ol>
    <form class="form-inline" role="form" style="float: left; width: 100%" method="post" id="queryForm">
        <div class="form-group" style="padding-left: 380px">
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
            <button type="button" id="queryBtn" onclick="doQuery();" class="btn btn-primary btn-sm" style="background-color: #288CC8">查询</button>
        </div>
    </form>
</div>
<div class="container" style="width: 100%">
    <table id="demo-table">
    </table>
</div>
</body>

<script type="text/javascript">
    $(function () {
        initTable();
    });

    function doQuery(params) {
        $('#demo-table').bootstrapTable('refresh');    //刷新表格
    }

    function initTable() {
        var url = '/admin/SingleRanks.action';
        $('#demo-table').bootstrapTable({
            method: 'get',
            dataType: 'json',
            contentType: "application/x-www-form-urlencoded",
            cache: false,
            striped: true,                              //是否显示行间隔色
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            url: url,
            height: $(window).height() - 110,
            width: $(window).width(),
            //showColumns: true,
            pagination: true,
            queryParams: queryParams,
            minimumCountColumns: 2,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 50,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showExport: false,
            exportDataType: 'all',
            responseHandler: responseHandler,
            columns: [
                {
                    field: '',
                    title: '排名',
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },
                {
                    field: 'userNo',
                    title: '学号',
                    align: 'center',
                    valign: 'middle',
                    sortable: true
                }, {
                    field: 'realName',
                    title: '姓名',
                    align: 'center',
                    valign: 'middle',
                    sortable: true
                }, {
                    field: 'className',
                    title: '班级',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'courseName',
                    title: '课程名称',
                    align: 'center',
                    valign: 'middle',
                    sortable: true
                }, {
                    field: 'value',
                    title: '分数',
                    align: 'center',
                    valign: 'middle',
                    formatter:function (value, row, index) {
                        if (value < 60){
                            return '<font style="color:red">' + value +'</font>'
                        }else{
                            return value;
                        }
                    }
                }, {
                    field: 'year',
                    title: '学年',
                    align: 'center',
                    valign: 'middle'
                },{
                    field: 'term',
                    title: '学期',
                    align: 'center',
                    valign: 'middle',
                    formatter:function (value, row, index) {
                        if (value == 1){
                            return '上学期';
                        }else{
                            return '下学期';
                        }
                    }
                }]
        });
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
/*    function doQuery() {
        //alert($('#year').val());
        //alert($('#term').val());
        //alert($('#class').val());
        //alert($('#course').val());
        $.ajax('/admin/SingleRanks.action', {
            type: 'get',
            dataType: 'json',
            contentType: "application/x-www-form-urlencoded",
            data: {
                year: $('#year').val(),
                term: $('#term').val(),
                classId: $('#class').val(),
                courseId: $('#course').val(),
            },
            success: function (data) {
                alert(data);
            }
        })
    }*/
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
</html>
