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
                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/brokenBookRecordCtrl/addOrEditBroken">
                        	<input type="hidden" id="brokenId" name="brokenId" value="${brokenBookRecordModel.brokenId}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">损坏书籍rfid：</label>
                                <div class="col-sm-8">
                                    <input id="bookRfid" name="bookRfid" class="form-control" type="text" value="${brokenBookRecordModel.bookRfid}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">损坏书籍id：</label>
                                <div class="col-sm-8">
                                    <input id="bookId" name="bookId" class="form-control" type="text" value="${brokenBookRecordModel.bookId}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">损坏用户id：</label>
                                <div class="col-sm-8">
                                    <input id="userId" name="userId" class="form-control" type="text" value="${brokenBookRecordModel.userId}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否需要赔偿：</label>
                                <div class="col-sm-8">
                                    <select id="needPay" name="needPay" class="form-control">
                                        <option value="0" <#if brokenBookRecordModel.needPay == 0>selected="selected"</#if>>否</option>
                                        <option value="1" <#if brokenBookRecordModel.needPay == 1>selected="selected"</#if>>是</option>
                                    </select>
                                </div>
                            </div>
                            <div id="dynamic1"></div>

                            <#--<div class="form-group">-->
                                <#--<label class="col-sm-3 control-label">赔偿金额：</label>-->
                                <#--<div class="col-sm-8">-->
                                    <#--<input id="payAmount" name="payAmount" class="form-control" type="text" value="${brokenBookRecordModel.payAmount}">-->
                                <#--</div>-->
                            <#--</div>-->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否下架：</label>
                                <div class="col-sm-8">
                                    <select name="offShelves" class="form-control">
                                        <option value="0" <#if brokenBookRecordModel.offShelves == 0>selected="selected"</#if>>否</option>
                                        <option value="1" <#if brokenBookRecordModel.offShelves == 1>selected="selected"</#if>>是</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">原因：</label>
                                <div class="col-sm-8">
                                    <input id="reason" name="reason" class="form-control" type="text" value="${brokenBookRecordModel.reason}">
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
                bookRfid: {
    	        required: true
    	      },
                userId: {
    	        required: true
    	      },
                needPay: {
    	        required: true
    	      },
                payAmount: {
    	            required: true,
                    number:true
    	      },
                reason: {
    	        required: true
    	      }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
    	    	$.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/brokenBookRecordCtrl/addOrEditBroken",
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
    $(function () {
        if($("#needPay").val()==1){
            select1();
        }else{
            select2();
        }
        $("#needPay").change(function () {
            var needPay=$("#needPay").val();
            if(needPay==1){
                select1();
            }else{
                select2();
            }
        });
    });
    function select1() {
        var dynamic1='<div id="dynamicNeedPay" class="form-group">\n' +
                '                                <label class="col-sm-3 control-label">赔偿金额：</label>\n' +
                '                                <div class="col-sm-8">\n' +
                '                                    <input id="payAmount" name="payAmount" class="form-control" type="text" value="${brokenBookRecordModel.payAmount}">\n' +
                '                                </div>\n' +
                '                            </div>';

        $("#dynamic1").after($(dynamic1));
    }
    function select2() {
        $("#dynamicNeedPay").remove();
    }
    </script>

</body>

</html>
