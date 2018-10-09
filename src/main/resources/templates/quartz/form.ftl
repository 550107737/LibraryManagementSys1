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
                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/quartzCtrl/addOrEditQuartz">
                        	<input type="hidden" id="scheduleJobId" name="scheduleJobId" value="${scheduleJob.scheduleJobId}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任务名称：</label>
                                <div class="col-sm-8">
                                    <input id="jobName" name="jobName" class="form-control" type="text" value="${scheduleJob.jobName}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任务别名：</label>
                                <div class="col-sm-8">
                                    <input id="aliasName" name="aliasName" class="form-control" type="text" value="${scheduleJob.aliasName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任务分组：</label>
                                <div class="col-sm-8">
                                    <input id="jobGroup" name="jobGroup" class="form-control" type="text" value="${scheduleJob.jobGroup}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">触发器：</label>
                                <div class="col-sm-8">
                                    <input id="jobTrigger" name="jobTrigger" class="form-control" type="text" value="${scheduleJob.jobTrigger}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任务url：</label>
                                <div class="col-sm-8">
                                    <input id="url" name="url" class="form-control" type="text" value="${scheduleJob.url}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任务运行时间表达式：</label>
                                <div class="col-sm-8">
                                    <input id="cronExpression" name="cronExpression" class="form-control" type="text" value="${scheduleJob.cronExpression}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任务描述：</label>
                                <div class="col-sm-8">
                                    <input id="description" name="description" class="form-control" type="text" value="${scheduleJob.description}">
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

	  	
	    $("#frm").validate({
    	    rules: {
                jobName: {
    	            required: true
    	      },
                aliasName: {
    	            required: true
    	      },
                jobGroup: {
    	            required: true
    	      },
                jobTrigger: {
                    required: true
    	      },
                url: {
                    required: true
                },
                cronExpression: {
                    required: true
                },
                description: {
                    required: true
                }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
    	    	$.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/quartzCtrl/addOrEditQuartz",
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
