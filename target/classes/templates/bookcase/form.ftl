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
                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/bookcaseCtrl/addOrEditBookcase">
                        	<input type="hidden" id="bookcaseId" name="bookcaseId" value="${bookcaseModel.bookcaseId}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">书柜RFID：</label>
                                <div class="col-sm-8">
                                    <input id="bookcaseRfid" name="bookcaseRfid" class="form-control" type="text" value="${bookcaseModel.bookcaseRfid}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">书柜位置：</label>
                                <div class="col-sm-8">
                                    <input id="location" name="location" class="form-control" type="text" value="${bookcaseModel.location}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否故障：</label>
                                <div class="col-sm-8">
                                    <select name="isBroken" class="form-control">
                                        <option value="0" <#if bookcaseModel.isBroken == 0>selected="selected"</#if>>否</option>
                                        <option value="1" <#if bookcaseModel.isBroken == 1>selected="selected"</#if>>是</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">故障时间：</label>
                                <div class="col-sm-8">
                                    <input id="brokenTime" name="brokenTime" class="laydate-icon form-control layer-date" type="text" value="${bookcaseModel.brokenTime}"  readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">维修时间：</label>
                                <div class="col-sm-8">
                                    <input id="repairTime" name="repairTime" class="laydate-icon form-control layer-date" type="text" value="${bookcaseModel.repairTime}"  readonly="readonly">
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
            elem: '#brokenTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
        laydate({
            elem: '#repairTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
	  	
	    $("#frm").validate({
    	    rules: {
                bookcaseRfid: {
    	        required: true
    	      },
                location: {
                    required: true
                },
                isBroken: {
                    required: true
                },
                brokenTime: {
                    required: false,
                    date:true
                },
                repairTime: {
                    required: false,
                    date:true
                }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
    	    	$.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/bookcaseCtrl/addOrEditBookcase",
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
