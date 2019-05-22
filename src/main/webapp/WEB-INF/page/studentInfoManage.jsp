<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title></title>
    <link rel="stylesheet" href="${ctx}/css/pintuer.css">
    <link rel="stylesheet" href="${ctx}/css/admin.css">
    <!-- font-awesome图标 -->
    <script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
    <!-- 开发环境版本，包含了有帮助的命令行警告 -->
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <!--element样式和库-->
    <!-- 引入样式 -->
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <!-- 引入组件库 -->
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
</head>
<body>
<div id="app">
    <el-button type="success" @click="alert">添加学生</el-button>
    <el-table
            ref="multipleTable"
            :data="tableData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
        <el-table-column
                type="selection"
                width="55">
        </el-table-column>
        <el-table-column
                label="姓名"
                width="120">
            <template slot-scope="scope">{{ scope.row.date }}</template>
        </el-table-column>
        <el-table-column
                prop="name"
                label="学号"
                width="120">
        </el-table-column>
        <el-table-column
                prop="address"
                label="班级"
                show-overflow-tooltip>
        </el-table-column>
        <el-table-column
                prop="address"
                label="任课老师"
                show-overflow-tooltip>
        </el-table-column>
        <el-table-column
                prop="address"
                label="专业"
                show-overflow-tooltip>
        </el-table-column>
        <el-table-column
                align="right">
            <template slot="header" slot-scope="scope">
                <el-input
                        v-model="search"
                        size="mini"
                        placeholder="输入关键字搜索"/>
            </template>
            <template slot-scope="scope">
                <el-button
                        size="mini">编辑
                </el-button>
                <el-button
                        size="mini"
                        type="danger">删除
                </el-button>
            </template>
        </el-table-column>
    </el-table>
    <div style="margin-top: 20px;text-align: center;word-spacing: 60px">
        <a href="#">首页</a> <a href="">1</a> <a href="">2</a>
        <a href="">2</a> <a href="">3</a> <a href="">4</a>.....
        <a href="#">尾页</a>
    </div>

    <el-dialog title="添加学生" :visible.sync="dialogFormVisible">
        <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="学生姓名:">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="学生性别:">
                <el-select v-model="form.region" placeholder="请选择学生性别">
                    <el-option label="男" value="shanghai"></el-option>
                    <el-option label="女" value="beijing"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="学生专业:">
                <el-input v-model="form.input" placeholder="请输入学生专业名称"></el-input>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="onSubmit">确定</el-button>
                <el-button>重置</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>

</div>

<script type="text/javascript">
    new Vue({
        el: '#app',
        data: {
            tableData: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: '王晓天',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: '李晓天',
                address: '上海市普陀区金沙江路 1519 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }],
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: '',
                input: ''
            },
            dialogFormVisible: false
        },
        methods:
            {
                alert() {
                    this.dialogFormVisible = true
                }
            }
    })
</script>
</body>
</html>