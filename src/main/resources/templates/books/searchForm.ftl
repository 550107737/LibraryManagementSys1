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
                        <form class="form-horizontal m-t" id="frm" >
                            <div class="form-group">
                                <label class="col-sm-3 control-label">书名：</label>
                                <div class="col-sm-8">
                                    <input id="bookName" name="bookName" class="form-control" type="text"   >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">作者：</label>
                                <div class="col-sm-8">
                                    <input id="authors" name="authors" class="form-control" type="text"   >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">出版社：</label>
                                <div class="col-sm-8">
                                    <input id="publication" name="publication" class="form-control" type="text"   >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">分类：</label>
                                <div class="col-sm-8">
                                    <input id="classification" name="classification" class="form-control" type="text"   >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">书籍状态：</label>
                                <div class="col-sm-8">
                                    <select id="booksStatus" name="booksStatus" class="form-control">
                                        <option value="-1" selected="selected">全部</option>
                                        <option value="0" >可借</option>
                                        <option value="1" >已借出</option>
                                        <option value="2" >已下架</option>
                                    </select>
                                </div>
                            </div>
                            <@shiro.hasPermission name="system:book:add">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">查找逾期未还书籍：</label>
                                <div class="col-sm-8">
                                    <input type="checkbox" id="overdue"><i></i>
                                </div>
                            </div>
                            </@shiro.hasPermission>
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button class="btn btn-primary" type="button" onclick="try1()">提交</button>
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
	  	
	    $("#frm").validate({

    	    messages: {},
    	    submitHandler:function(){
                try1();
    	    	<#--$.ajax({-->
   	    		   <#--type: "POST",-->
   	    		   <#--dataType: "json",-->
   	    		   <#--url: "${ctx!}/bookCtrl/searchbook",-->
   	    		   <#--data: $(form).serialize(),-->
   	    		   <#--success: function(msg){-->
	   	    			<#--layer.msg(msg.message, {time: 2000},function(){-->
	   						<#--var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引-->
	   						<#--parent.layer.close(index); -->
	   					<#--});-->
   	    		   <#--}-->
   	    		<#--});-->
            } 
    	});
    });
    function try1() {
        var authors=$("#authors").val();
        parent.$("#authors").attr("value",authors);
        var bookName=$("#bookName").val();
        parent.$("#bookName").attr("value",bookName);
        var publication=$("#publication").val();
        parent.$("#publication").attr("value",publication);
        var classification=$("#classification").val();
        parent.$("#classification").attr("value",classification);
        var booksStatus=$("#booksStatus").val();
        parent.$("#booksStatus").attr("value",booksStatus);
        var overdue;
        if($('#overdue').is(':checked')) {
            overdue=1;
            parent.$("#booksStatus").attr("value",1);
        }else{
            overdue=0;
        }
        parent.$("#overdue").attr("value",overdue);
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    </script>

</body>

</html>
