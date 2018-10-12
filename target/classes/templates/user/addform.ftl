<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 表单验证 jQuery Validation</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx!}/assets/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">

                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/userCtrl/addOrEditUser">
                        	<input type="hidden" id="id" name="id" value="${user.id}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户RFID证件号：</label>
                                <div class="col-sm-8">
                                    <input id="userId" name="userId" class="form-control" type="text" value="${user.userId}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">学号/工号：</label>
                                <div class="col-sm-8">
                                    <input id="userSid" name="userSid" class="form-control" type="text" value="${user.userSid}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">身份：</label>
                                <div class="col-sm-8">
                                	<select name="userRole" class="form-control">
                                		<option value="0" <#if user.userRole == 0>selected="selected"</#if>>学生</option>
                                		<option value="1" <#if user.userRole == 1>selected="selected"</#if>>教职工</option>
                                	</select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">姓名：</label>
                                <div class="col-sm-8">
                                    <input id="userName" name="userName" class="form-control" type="text" value="${user.userName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">性别：</label>
                                <div class="col-sm-8">
                                    <select name="userSex" class="form-control">
                                        <option value="0" <#if user.userSex == 0>selected="selected"</#if>>女</option>
                                        <option value="1" <#if user.userSex == 1>selected="selected"</#if>>男</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">年级/部门：</label>
                                <div class="col-sm-8">
                                    <input id="userClass" name="userClass" class="form-control" value="${user.userClass}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">当前借书总数：</label>
                                <div class="col-sm-8">
                                    <input id="borrowNum" name="borrowNum" class="form-control"
                                           <#if user.borrowNum!=null>value="${user.borrowNum}"
                                           <#else> value="0"</#if>>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否存在逾期书籍：</label>
                                <div class="col-sm-8">
                                	<select name="isOverdue" class="form-control">
                                		<option value="0" <#if user.isOverdue == 0>selected="selected"</#if>>否</option>
                                		<option value="1" <#if user.isOverdue == 1>selected="selected"</#if>>是</option>
                                	</select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">欠款金额：</label>
                                <div class="col-sm-8">
                                    <input id="overdueTotalAmount" name="overdueTotalAmount" class="form-control"
                                           <#if user.overdueTotalAmount!=null>value="${user.overdueTotalAmount}"
                                            <#else> value="0"</#if>>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">证件有效期：</label>
                                <div class="col-sm-8">
                                    <input id="sidTime" name="sidTime" class="laydate-icon form-control layer-date" value="${user.sidTime}" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户状态：</label>
                                <div class="col-sm-8">
                                    <select name="userStatus" class="form-control">
                                        <option value="0" <#if user.userStatus == 0>selected="selected"</#if>>正常</option>
                                        <option value="1" <#if user.userStatus == 1>selected="selected"</#if>>冻结</option>
                                        <option value="2" <#if user.userStatus == 2>selected="selected"</#if>>锁定</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户密码：</label>
                                <div class="col-sm-8">
                                    <input id="userPassword" name="userPassword" class="form-control"
                                           <#if user.userPassword!=null>value="${user.userPassword}"
                                           <#else> value="111111"</#if>>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button class="btn btn-primary" type="submit">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>


    <!-- 全局js -->
    <script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctx!}/assets/js/bootstrap.min.js?v=3.3.6"></script>

    <!-- 自定义js -->
    <script src="${ctx!}/assets/js/content.js?v=1.0.0"></script>

    <!-- jQuery Validation plugin javascript-->
    <script src="${ctx!}/assets/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx!}/assets/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${ctx!}/assets/js/plugins/layer/layer.min.js"></script>
    <script src="${ctx!}/assets/js/plugins/layer/laydate/laydate.js"></script>
    <script type="text/javascript">
    $(document).ready(function () {
        //外部js调用
        laydate({
            elem: '#sidTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });

	  	
	    $("#frm").validate({
    	    rules: {
                userId: {
    	            required: true,
                    minlength: 4,
                    maxlength: 30
    	      },
                userSid: {
    	            required: true,
                    minlength: 4,
                    maxlength: 20,
                    digits:true
    	      },
                userRole: {
    	        required: true
    	      },
                userName: {
    	            required: true,
                    minlength: 2,
                    maxlength: 10
    	      },
                userSex: {
    	        required: true
    	      },
                userClass: {
    	        required: true
    	      },
                borrowNum: {
    	            required: true,
                    digits:true
    	      },
                isOverdue: {
                    required: true
                },
                overdueTotalAmount: {
                    required: true,
                    number:true
                },
                sidTime: {
                    date:true,
                    required: true
                },
                userStatus: {
                    required: true
                },
                userPassword: {
                    required: true,
                    minlength: 6,
                    maxlength: 20
                }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
    	    	$.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/userCtrl/addOrEditUser",
   	    		   data: $(form).serialize(),
   	    		   success: function(msg){
	   	    			layer.msg(msg.message, {time: 2000},function(){
	   						var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	   						parent.layer.close(index); 
	   					});
   	    		   }
   	    		});
            } 
    	});
    });
    </script>

</body>

</html>
