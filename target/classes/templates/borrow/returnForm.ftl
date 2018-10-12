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
                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/borrowCtrl/returnBorrow">

                            <div class="form-group">
                                <label class="col-sm-3 control-label">借阅者RFID：</label>
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
                                <label class="col-sm-3 control-label">归还目的地：</label>
                                <div class="col-sm-8">
                                    <select id="inBox" name="inBox" class="form-control">
                                        <option value="1" >图书馆</option>
                                        <option value="0" >移动书柜</option>
                                    </select>
                                </div>
                            </div>
                            <div id="dynamic1"></div>

                            <#--<div class="form-group">-->
                                <#--<label class="col-sm-3 control-label">实际归还书柜：</label>-->
                                <#--<div class="col-sm-8">-->
                                    <#--<select id="actualBookcaseId" name="actualBookcaseId" class="form-control">-->
                                            <#--<#list bookcaseList as bcList>-->
                                                <#--<option value="${bcList.bookcaseId}" <#if bcList.bookcaseId == borrowModel.returnBoxId>selected="selected"</#if>>-->
                                                    <#--${bcList.location}-->
                                                <#--</option>-->
                                            <#--</#list>-->
                                    <#--</select>-->
                                <#--</div>-->
                            <#--</div>-->
                            <#--<div class="form-group">-->
                                <#--<label class="col-sm-3 control-label">实际归还书箱层号：</label>-->
                                <#--<div class="col-sm-8">-->
                                    <#--<select id="actualBoxId" name="actualBoxId" class="form-control">-->
                                        <#--<option value="1" >1层</option>-->
                                        <#--<option value="2" >2层</option>-->
                                        <#--<option value="3" >3层</option>-->
                                    <#--</select>-->
                                <#--</div>-->
                            <#--</div>-->
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
        var map='${map}';
        console.log(map);

        $("#frm").validate({
    	    rules: {
                userId: {
    	        required: true
    	      },
                bookRfid: {
                    required: true
                },
                boxId: {
                    required: true
                }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
    	    	$.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/borrowCtrl/returnBorrow",
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
        if($("#inBox").val()==0){
            select1();
        }else{
            select2();
        }
        $("#inBox").change(function () {
            var inbox=$("#inBox").val();
            if(inbox==0){
                select1();
            }else{
                select2();
            }
        });
    });
    function select1() {
        var dynamic1='<div id="dynamicActualBookcaseId" class="form-group">\n' +
                '                                <label class="col-sm-3 control-label">实际归还书柜：</label>\n' +
                '                                <div class="col-sm-8">\n' +
                '                                    <select id="actualBookcaseId" name="actualBookcaseId" class="form-control">\n' +
                '                                            <#list bookcaseList as bcList>\n'+
'                                                <option value="${bcList.bookcaseId}" <#if bcList.bookcaseId == borrowModel.returnBoxId>selected="selected"</#if>>\n' +
'                                                    ${bcList.location}\n' +
'                                                </option>\n' +
'                                            </#list>\n' +
                '                                    </select>\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div id="dynamicActualBoxId" class="form-group">\n' +
                '                                <label class="col-sm-3 control-label">实际归还书箱层号：</label>\n' +
                '                                <div class="col-sm-8">\n' +
                '                                    <select id="actualBoxId" name="actualBoxId" class="form-control">\n' +
                '                                        <option value="1" >1层</option>\n' +
                '                                        <option value="2" >2层</option>\n' +
                '                                        <option value="3" >3层</option>\n' +
                '                                    </select>\n' +
                '                                </div>\n' +
                '                            </div>';

        $("#dynamic1").after($(dynamic1));
        $("#dynamicLocation").remove();
    }
    function select2() {
        var dynamic2='<div id="dynamicLocation" class="form-group">\n' +
                '                                <label class="col-sm-3 control-label">图书馆中的位置：</label>\n' +
                '                                <div class="col-sm-8">\n' +
                '                                    <input id="libraryLocation" name="libraryLocation" class="form-control">\n' +
                '                                </div>\n' +
                '                            </div>';
        $("#dynamic1").after($(dynamic2));
        $("#dynamicActualBookcaseId").remove();
        $("#dynamicActualBoxId").remove();
    }
    </script>

</body>

</html>
