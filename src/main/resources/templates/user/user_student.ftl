<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>用户列表</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx!}/assets/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="${ctx!}/assets/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">

    <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">


</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox ">
                    <div class="ibox-title">
                        <h5>用户管理</h5>
                    </div>
                    <div class="ibox-content">
                        <p>
                        </p>
                        <hr>
                        <div class="row row-lg">
		                    <div class="col-sm-12">
		                        <!-- Example Card View -->
		                        <div class="example-wrap">
		                            <div class="example">
		                            	<table id="table_list"></table>
		                            </div>
		                        </div>
		                        <!-- End Example Card View -->
		                    </div>
	                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 全局js -->
    <script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctx!}/assets/js/bootstrap.min.js?v=3.3.6"></script>
    
	<!-- Bootstrap table -->
    <script src="${ctx!}/assets/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${ctx!}/assets/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="${ctx!}/assets/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

    <!-- Peity -->
    <script src="${ctx!}/assets/js/plugins/peity/jquery.peity.min.js"></script>

    <script src="${ctx!}/assets/js/plugins/layer/layer.min.js"></script>

    <!-- 自定义js -->
    <script src="${ctx!}/assets/js/content.js?v=1.0.0"></script>

    <!-- Page-Level Scripts -->
    <script>
        $(document).ready(function () {
        	//初始化表格,动态从服务器加载数据  
			$("#table_list").bootstrapTable({
			    //使用get请求到服务器获取数据  
			    method: "POST",
			    //必须设置，不然request.getParameter获取不到请求参数
			    contentType: "application/x-www-form-urlencoded",
			    //获取数据的Servlet地址  
			    url: "${ctx!}/userCtrl/list_student",
			    //表格显示条纹  
			    striped: true,
			    //启动分页  
			    pagination: true,
			    //每页显示的记录数  
			    pageSize: 10,
			    //当前第几页  
			    pageNumber: 1,
			    //记录数可选列表  
			    pageList: [5, 10, 15, 20, 25],
			    //是否启用查询  
			    search: false,
			    //是否启用详细信息视图
			    detailView:false,
			    detailFormatter:detailFormatter,
			    //表示服务端请求  
			    sidePagination: "server",
			    //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder  
			    //设置为limit可以获取limit, offset, search, sort, order  
			    queryParamsType: "undefined",
			    //json数据解析
			    responseHandler: function(res) {
			        return {
			            "rows": res.content,
			            "total": res.totalElements
			        };
			    },
			    //数据列
			    columns: [{
			        title: "实际ID",
			        field: "id",
			        sortable: true,
                    visible:false
			    },{
                    title: "ID",
                    field: "table_id",
                    formatter: function (value, row, index) {
                        return index+1;
                    }
                },{
			        title: "rfid标签号",
			        field: "userId"
			    },{
                    title: "学号/工号",
                    field: "userSid"
                },{
                    title: "角色权限",
                    field: "userSession",
                    formatter: function(value, row, index) {
                        if (value == '1')
                            return '<span class="label label-warning">超级管理员</span>';
                        if (value == '2')
                            return '<span class="label label-warning">高级管理员</span>';
                        return '<span class="label label-primary">普通用户</span>';
                    }
                },{
                    title: "职位",
                    field: "userRole",
                    formatter: function(value, row, index) {
                        if (value == '0')
                            return '<span class="label label-warning">学生</span>';
                        return '<span class="label label-primary">教职工</span>';
                    }
                },{
			        title: "姓名",
			        field: "userName"
			    },{
			        title: "性别",
			        field: "userSex",
			        formatter: function(value, row, index) {
                        if (value == '0') 
                        	return '<span class="label label-warning">女</span>';
                        return '<span class="label label-primary">男</span>';
                    }
			    },{
			        title: "部门",
			        field: "userClass"
			    },{
			        title: "当前借书",
			        field: "borrowNum"
			    },{
			        title: "当前可借",
			        field: "remainNum"
			    },{
			        title: "是否存在逾期书本",
			        field: "isOverdue",
                    formatter: function(value, row, index) {
                        if (value == '0')
                            return '<span class="label label-warning">否</span>';
                        return '<span class="label label-primary">是</span>';
                    }
			    },{
			        title: "欠费金额",
			        field: "overdueTotalAmount"
			    },{
			        title: "证件有效期",
			        field: "sidTime"
			    },{
                    title: "用户状态",
                    field: "userStatus",
                    formatter: function(value, row, index) {
                        if (value == '0')
                            return '<span class="label label-warning">正常</span>';
                        if (value == '1')
                            return '<span class="label label-warning">冻结</span>';
                        return '<span class="label label-primary">注销</span>';
                    }
                },{
                    title: "更新时间",
                    field: "updateTime"
                },{
                    title: "操作",
                    field: "empty",
                    formatter: function (value, row, index) {
                        var operateHtml="" ;
                        operateHtml = operateHtml + '<@shiro.hasPermission name="system:borrow:studentonly"><button class="btn btn-primary btn-xs" type="button" onclick="updatePwd()"><i class="fa fa-arrows"></i>&nbsp;修改密码</button></@shiro.hasPermission>';

                        if(row.overdueTotalAmount>0){
                            operateHtml = operateHtml + '<@shiro.hasPermission name="system:borrow:studentonly"><button class="btn btn-info btn-xs" type="button" onclick="selfrepay(\''+row.id+'\')"><i class="fa fa-arrows"></i>&nbsp;自助还款</button></@shiro.hasPermission>';
                        }
                        return operateHtml;
                    }
                }]
			});
        });
        
        function detailFormatter(index, row) {
	        var html = [];
	        html.push('<p><b>描述:</b> ' + row.description + '</p>');
	        return html.join('');
	    }
	    function selfrepay(id) {
            layer.open({
                type: 2,
                title: '扫码自助还款',
                shadeClose: true,
                shade: false,
                area: ['893px', '600px'],
                content: '${ctx!}/userCtrl/selfrepay/' + id,
                end: function(index){
                    $('#table_list').bootstrapTable("refresh");
                }
            });
        }
        function updatePwd(){
            layer.open({
                type: 2,
                title: '修改密码',
                shadeClose: true,
                shade: false,
                area: ['893px', '600px'],
                content: '${ctx!}/userCtrl/updatePwd',
                end: function(index){
                    window.location.reload();
                }
            });
        }
    </script>

    
    

</body>

</html>
