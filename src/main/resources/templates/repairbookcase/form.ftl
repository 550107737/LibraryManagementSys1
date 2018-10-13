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
    <input type="hidden" id="key" value="${Session.EncryptKey}"/>
    <div class="wrapper wrapper-content animated fadeInRight">

        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">

                    <div class="ibox-content">

                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/repairBookcaseCtrl/addOrEdit">
                        	<input type="hidden" id="repairId" name="repairId" value="${repairBookcaseModel.repairId}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">书柜rfid：</label>
                                <div class="col-sm-8">
                                    <input id="bookcaseRfid" name="bookcaseRfid" class="form-control" type="text" value="${repairBookcaseModel.bookcaseRfid}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否修复：</label>
                                <div class="col-sm-8">
                                    <select name="isRepair" class="form-control">
                                        <option value="0" <#if repairBookcaseModel.isRepair == 0>selected="selected"</#if>>是</option>
                                        <option value="1" <#if repairBookcaseModel.isRepair == 1>selected="selected"</#if>>否</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">故障时间：</label>
                                <div class="col-sm-8">
                                    <input id="brokenTime" name="brokenTime" class="form-control" type="text" value="${repairBookcaseModel.brokenTime}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">故障原因：</label>
                                <div class="col-sm-8">
                                    <input id="brokenReason" name="brokenReason" class="form-control" type="text" value="${repairBookcaseModel.brokenReason}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">修复时间：</label>
                                <div class="col-sm-8">
                                    <input id="repairTime" name="repairTime" class="form-control" type="text" value="${repairBookcaseModel.repairTime}">
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
    <!-- 加密js -->
    <script src="${ctx!}/assets/js/crypto-js/crypto-js.js"></script>
    <script type="text/javascript">
    $(document).ready(function () {
	  	//外部js调用

	  	
	    $("#frm").validate({
    	    rules: {
                stuMax: {
    	            required: true,
                    digits:true
    	      },
                stuDay: {
    	            required: true,
                    digits:true
    	      },
                teacherMax: {
    	            required: true,
                    digits:true
    	      },
                teacherDay: {
                    required: false,
                    digits:true
    	      },
                boxMax: {
    	            required: true,
                    digits:true
    	      }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
                var serializeForm=$(form).serialize();
                console.log(serializeForm);
                var data1=secret(serializeForm);
                var timestamp = (new Date()).valueOf();
                var data2=serializeForm+"&uukey="+key1;
    	    	$.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/repairBookcaseCtrl/addOrEdit",
   	    		   data: serializeForm,
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
    var key1 = $('#key').val();
    key = CryptoJS.enc.Utf8.parse(key1);
    function Encrypt(word){
        var srcs = CryptoJS.enc.Utf8.parse(word);
        var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
        return encrypted.toString();
    }
    function secret(str){
        var temp="";var flag=true;

        var equals=0,and=0,start=0;
        while(true){
            equals=str.indexOf("=",start);
            if(equals==-1){
                break;
            }
            and=str.indexOf("&",start);
            if(and==-1){
                and=str.length;
                flag=false;
            }
            temp+=str.substring(start,equals)+"=";
            if(flag){
                temp+=Encrypt(str.substring(equals+1,and))+"&";
            }else{
                temp+=Encrypt(str.substring(equals+1,and));
            }
            start=and+1;
        }
        console.log(temp);
        return temp;
    }
    </script>

</body>

</html>
