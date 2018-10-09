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
                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/changeRfidCtrl/addOrEditChangeRfid">
                        	<input type="hidden" id="changeId" name="changeId" value="${changeRfidModel.changeId}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">旧rfid：</label>
                                <div class="col-sm-8">
                                    <input id="oldRfid" name="oldRfid" class="form-control" type="text" value="${changeRfidModel.oldRfid}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">新rfid：</label>
                                <div class="col-sm-8">
                                    <input id="newRfid" name="newRfid" class="form-control" type="text" value="${changeRfidModel.newRfid}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">更新对象：</label>
                                <div class="col-sm-8">
                                    <select name="type" class="form-control">
                                        <option value="1" <#if changeRfidModel.type == 1>selected="selected"</#if>>用户</option>
                                        <option value="2" <#if changeRfidModel.type == 2>selected="selected"</#if>>书籍</option>
                                        <option value="3" <#if changeRfidModel.type == 3>selected="selected"</#if>>书柜</option>
                                        <option value="4" <#if changeRfidModel.type == 4>selected="selected"</#if>>书箱</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否更新完成：</label>
                                <div class="col-sm-8">
                                    <select name="isFinish" class="form-control">
                                        <option value="0" <#if changeRfidModel.isFinish == 0>selected="selected"</#if>>否</option>
                                        <option value="1" <#if changeRfidModel.isFinish == 1>selected="selected"</#if>>是</option>
                                    </select>
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
                oldRfid: {
    	            required: true
    	      },
                newRfid: {
                    required: true
                },
                isFinish: {
                    required: true
                },
                type: {
                    required: true
                }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
    	    	$.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/changeRfidCtrl/addOrEditChangeRfid",
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
