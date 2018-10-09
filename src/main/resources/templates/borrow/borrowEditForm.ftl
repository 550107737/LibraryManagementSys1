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
                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/borrowCtrl/editBorrow">
                        	<input type="hidden" id="borrowId" name="borrowId" value="${borrowModel.borrowId}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">借阅者ID：</label>
                                <div class="col-sm-8">
                                    <input id="userId" name="userId" class="form-control" type="text" value="${borrowModel.userId}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">借阅书籍RFID：</label>
                                <div class="col-sm-8">
                                    <input id="bookRfid" name="bookRfid" class="form-control" type="text" value="${borrowModel.bookRfid}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">借阅时间：</label>
                                <div class="col-sm-8">
                                    <input id="borrowTime" name="borrowTime" class="laydate-icon form-control layer-date" type="text" value="${borrowModel.borrowTime}" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">应还时间：</label>
                                <div class="col-sm-8">
                                    <input id="returnTime" name="returnTime" class="laydate-icon form-control layer-date" type="text" value="${borrowModel.returnTime}" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">应还书柜：</label>
                                <div class="col-sm-8">
                                    <select name="returnBookcaseId" class="form-control">
                                		<#list bookcaseModels as r>
                                            <option value="${r.bookcaseId}" <#if r.bookcaseId==borrowModel.returnBookcaseId>selected</#if>>
                                                ${r.location}
                                            </option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">应还书箱：</label>
                                <div class="col-sm-8">
                                    <select id="returnBoxId" name="returnBoxId" class="form-control">
                                        <option value="1" <#if returnBookboxModel.boxSid == 1 >selected="selected"</#if>>1层</option>
                                        <option value="2" <#if returnBookboxModel.boxSid == 2 >selected="selected"</#if>>2层</option>
                                        <option value="3" <#if returnBookboxModel.boxSid == 3 >selected="selected"</#if>>3层</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">归还状态：</label>
                                <div class="col-sm-8">
                                    <select name="status" class="form-control">
                                        <option value="0" <#if borrowModel.status == 0>selected="selected"</#if>>未归还</option>
                                        <option value="1" <#if borrowModel.status == 1>selected="selected"</#if>>已归还</option>
                                        <option value="2" <#if borrowModel.status == 1>selected="selected"</#if>>逾期</option>
                                        <option value="3" <#if borrowModel.status == 1>selected="selected"</#if>>损坏</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">实际归还时间：</label>
                                <div class="col-sm-8">
                                    <input id="actualTime" name="actualTime" class="laydate-icon form-control layer-date" value="${borrowModel.actualTime}" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">实际归还书柜：</label>
                                <div class="col-sm-8">
                                    <select name="actualBookcaseId" class="form-control">
                                        <option selected="selected"></option>
                                		<#list bookcaseModels as r>
                                            <option value="${r.bookcaseId}" <#if r.bookcaseId==borrowModel.actualBookcaseId>selected</#if>>
                                                ${r.location}
                                            </option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">实际归还书箱：</label>
                                <div class="col-sm-8">
                                    <select id="actualBoxId" name="actualBoxId" class="form-control">
                                        <option selected="selected"></option>
                                        <option value="1" <#if actualBookboxModel.boxSid == 1 >selected="selected"</#if>>1层</option>
                                        <option value="2" <#if actualBookboxModel.boxSid == 2 >selected="selected"</#if>>2层</option>
                                        <option value="3" <#if actualBookboxModel.boxSid == 3 >selected="selected"</#if>>3层</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否欠费：</label>
                                <div class="col-sm-8">
                                    <select name="isPay" class="form-control">
                                        <option value="0" <#if borrowModel.isPay == 0>selected="selected"</#if>>否</option>
                                        <option value="1" <#if borrowModel.isPay == 1>selected="selected"</#if>>是</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">欠款金额：</label>
                                <div class="col-sm-8">
                                    <input id="amount" name="amount" class="form-control" value="${borrowModel.amount}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">订单是否完成：</label>
                                <div class="col-sm-8">
                                    <select name="isFinish" class="form-control">
                                        <option value="0" <#if borrowModel.isFinish == 0>selected="selected"</#if>>否</option>
                                        <option value="1" <#if borrowModel.isFinish == 1>selected="selected"</#if>>是</option>
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
        laydate({
            elem: '#actualTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
        laydate({
            elem: '#borrowTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
        laydate({
            elem: '#returnTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
	  	
	    $("#frm").validate({
    	    rules: {
                userId: {
    	        required: true
    	      },
                bookRfid: {
                    required: true
                },
                borrowTime: {
    	            required: true,
                    date:true
    	      },
                returnTime: {
                    required: false,
                    date:true
    	      },
                returnBookcaseId: {
                    required: true
    	      },
                returnBoxId: {
                    required: true
    	      },
                status: {
    	            required: true
    	      },
                actualTime: {
                    required: false,
                    date:true
    	      },
                actualBookcaseId: {
                    required: false,
    	      },
                isPay: {
                    required: true
                },
                amount: {
                    required: true,
                    number:true
                },
                isFinish: {
                    required: true
                }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
    	    	$.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/borrowCtrl/editBorrow",
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
