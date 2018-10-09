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
                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/bookCtrl/addBook">
                        	<input type="hidden" id="booksId" name="booksId" value="${bookModel.booksId}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">书籍Rfid标签号：</label>
                                <div class="col-sm-8">
                                    <input id="bookRfid" name="bookRfid" class="form-control" type="text" value="${bookModel.bookRfid}"  >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">书名：</label>
                                <div class="col-sm-8">
                                    <input id="bookName" name="bookName" class="form-control" type="text" value="${bookModel.bookName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">isbn：</label>
                                <div class="col-sm-8">
                                    <input id="isbn" name="isbn" class="form-control" type="text" value="${bookModel.isbn}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">作者：</label>
                                <div class="col-sm-8">
                                    <input id="authors" name="authors" class="form-control" type="text" value="${bookModel.authors}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">出版社：</label>
                                <div class="col-sm-8">
                                    <input id="publication" name="publication" class="form-control" type="text" value="${bookModel.publication}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">出版时间：</label>
                                <div class="col-sm-8">
                                    <input id="publicationTime" name="publicationTime" class="laydate-icon form-control layer-date" value="${bookModel.publicationTime}" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">分类：</label>
                                <div class="col-sm-8">
                                    <input id="classification" name="classification" class="form-control" value="${bookModel.classification}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">文献号：</label>
                                <div class="col-sm-8">
                                    <input id="document" name="document" class="form-control" value="${bookModel.document}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">定价：</label>
                                <div class="col-sm-8">
                                    <input id="amount" name="amount" class="form-control" value="${bookModel.amount}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">语言：</label>
                                <div class="col-sm-8">
                                    <input id="language" name="language" class="form-control" value="${bookModel.language}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">描述：</label>
                                <div class="col-sm-8">
                                    <input id="description" name="description" class="form-control" value="${bookModel.description}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">书本状态：</label>
                                <div class="col-sm-8">
                                    <select name="booksStatus" class="form-control">
                                        <option value="0" <#if bookModel.booksStatus == 0>selected="selected"</#if>>可借</option>
                                        <option value="1" <#if bookModel.booksStatus == 1>selected="selected"</#if>>已借出</option>
                                        <option value="2" <#if bookModel.booksStatus == 2>selected="selected"</#if>>已下架</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">默认所在地：</label>
                                <div class="col-sm-8">
                                    <select id="inBox" name="inBox" class="form-control">
                                        <option value="1" <#if bookModel.inBox == 1>selected="selected"</#if>>图书馆</option>
                                        <option value="0" <#if bookModel.inBox == 0>selected="selected"</#if>>移动书柜</option>
                                    </select>
                                </div>
                            </div>
                            <div id="dynamic1"></div>

                            <#--<div class="form-group">-->
                                <#--<label class="col-sm-3 control-label">当前所在书柜：</label>-->
                                <#--<div class="col-sm-8">-->
                                    <#--<select name="bookcaseId" class="form-control">-->
                                		<#--<#list bookcaseModels as r>-->
                                            <#--<option value="${r.bookcaseId}" >-->
                                                <#--${r.location}-->
                                            <#--</option>-->
                                        <#--</#list>-->
                                    <#--</select>-->
                                <#--</div>-->
                            <#--</div>-->
                            <#--<div class="form-group">-->
                                <#--<label class="col-sm-3 control-label">当前所在书箱：</label>-->
                                <#--<div class="col-sm-8">-->
                                    <#--<select id="boxId" name="boxId" class="form-control">-->
                                        <#--<option value="1" >1层</option>-->
                                        <#--<option value="2" >2层</option>-->
                                        <#--<option value="3" >3层</option>-->
                                    <#--</select>-->
                                <#--</div>-->
                            <#--</div>-->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">缩略图地址：</label>
                                <div class="col-sm-8">
                                    <input id="imgurl" name="imgurl" class="form-control" value="${bookModel.imgurl}">
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
            elem: '#publicationTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
	  	
	    $("#frm").validate({
    	    rules: {
                bookRfid: {
    	        required: true
    	      },
                bookName: {
    	        required: true
    	      },
                isbn: {
    	        required: true
    	      },
                authors: {
    	        required: true
    	      },
                publication: {
    	        required: true
    	      },
                publicationTime: {
    	            required: true,
                    date:true
    	      },
                classification: {
    	        required: true
    	      },
                document: {
    	        required: false
    	      },
                amount: {
    	            required: true,
                    number:true
    	      },
                language: {
                    required: true
                },
                description: {
                    required: false
                },
                booksStatus: {
                    required: true
                },
                imgurl: {
                    required: true
                }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
    	    	$.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/bookCtrl/addBook",
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
        var dynamic1='<div id="dynamicBookcase" class="form-group">\n' +
                '                               <label class="col-sm-3 control-label">当前所在书柜：</label>\n' +
                '                                <div class="col-sm-8">\n' +
                '                                    <select name="bookcaseId" class="form-control">\n' +
                '                                \t\t<#list bookcaseModels as r>\n'+
                '                                            <option value="${r.bookcaseId}" <#if bookModel.belongBookcase == r.bookcaseId>selected="selected"</#if>>\n' +
                '                                                ${r.location}\n' +
                '                                            </option>\n' +
                '                                        </#list>\n' +
                '                                    </select>\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div id="dynamicBox" class="form-group">\n' +
                '                                <label class="col-sm-3 control-label">当前所在书箱：</label>\n' +
                '                                <div class="col-sm-8">\n' +
                '                                    <select id="boxId" name="boxId" class="form-control">\n' +
                '                                        <option value="1" <#if bookboxModel.boxSid == 1 >selected="selected"</#if>>1层</option>\n' +
                '                                        <option value="2" <#if bookboxModel.boxSid == 2 >selected="selected"</#if>>2层</option>\n' +
                '                                        <option value="3" <#if bookboxModel.boxSid == 3 >selected="selected"</#if>>3层</option>\n' +
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
                '                                    <input id="libiaryLocation" name="libraryLocation" class="form-control" >\n' +
                '                                </div>\n' +
                '                            </div>';
        $("#dynamic1").after($(dynamic2));
        $("#dynamicBox").remove();
        $("#dynamicBookcase").remove();
    }
    </script>

</body>

</html>
