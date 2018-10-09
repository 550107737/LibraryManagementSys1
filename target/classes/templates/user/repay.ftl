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
        此处应有二维码

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
        var userClass=$("#userClass").val();//访问父页面元素值
        parent.$("#userClass").attr("value",userClass);
        var isOverdue=$("#isOverdue").val();//访问父页面元素值
        parent.$("#isOverdue").attr("value",isOverdue);
        var overdueTotalAmount=$("#overdueTotalAmount").val();//访问父页面元素值
        parent.$("#overdueTotalAmount").attr("value",overdueTotalAmount);
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    </script>

</body>

</html>
