<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title> SPPanAdmin- 主页</title>

    <meta name="keywords" content="">
    <meta name="description" content="">

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx!}/assets/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                                    <span class="block m-t-xs" style="font-size:20px;">
                                        <i class="fa fa-area-chart"></i>
                                        <strong class="font-bold">图书管理系统</strong>
                                    </span>
                                </span>
                            </a>
                        </div>
                        <div class="logo-element">图书管理系统
                        </div>
                    </li>
                    <li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
                        <span class="ng-scope">分类</span>
                    </li>
                    <li>
                        <a class="J_menuItem" href="${ctx!}/admin/welcome">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">主页</span>
                        </a>
                    </li>
                    <@shiro.hasPermission name="system:borrow:studentonly">
                        <li>
                            <a class="J_menuItem" href="${ctx!}/userCtrl/index_student">
                                <i class="fa fa fa-cog"></i>
                                我的用户信息
                            </a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${ctx!}/bookCtrl/books">
                                <i class="fa fa fa-cog"></i>
                                书籍检索查询
                            </a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${ctx!}/borrowCtrl/borrow_student">
                                <i class="fa fa fa-cog"></i>
                                我的当前借阅
                            </a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${ctx!}/borrowHistoryCtrl/borrowHistory_student">
                                <i class="fa fa fa-cog"></i>
                                我的借阅历史
                            </a>
                        </li>
                    </@shiro.hasPermission>

                    <@shiro.hasPermission name="system:borrow:bothadmin">
                        <li>
                            <a href="#">
                                <i class="fa fa fa-cog"></i>
                                <span class="nav-label">系统管理</span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/userCtrl/index">用户管理</a>
                                </li>
                                <@shiro.hasPermission name="system:borrow:adminonly">
                                    <li>
                                        <a class="J_menuItem" href="${ctx!}/roleCtrl/role">角色管理</a>
                                    </li>
                                    <li>
                                        <a class="J_menuItem" href="${ctx!}/admin/resource/index">资源管理</a>
                                    </li>
                                </@shiro.hasPermission>
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/configCtrl/config">默认设置</a>
                                </li>
                            </ul>
                        </li>

                        <li>
                            <a href="#">
                                <i class="fa fa fa-cog"></i>
                                <span class="nav-label">图书管理</span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/bookCtrl/books">书籍查询</a>
                                </li>
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/bookcaseCtrl/bookcase">书柜管理</a>
                                </li>
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/bookboxCtrl/bookbox">书箱管理</a>
                                </li>
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/brokenBookRecordCtrl/index">损坏登记</a>
                                </li>
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/changeRfidCtrl/index">更换rfid</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">
                                <i class="fa fa fa-cog"></i>
                                <span class="nav-label">借阅管理</span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/borrowCtrl/borrow">当前借阅列表</a>
                                </li>
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/borrowHistoryCtrl/borrowHistory">借阅历史列表</a>
                                </li>
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/booksCheckCtrl/booksCheck">书籍盘点结果</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">
                                <i class="fa fa fa-cog"></i>
                                <span class="nav-label">任务管理</span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a class="J_menuItem" href="${ctx!}/quartzCtrl/index">定时任务</a>
                                </li>
                            </ul>
                        </li>
                    </@shiro.hasPermission>





                    <li class="line dk"></li>
                </ul>
            </div>
        </nav>

        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-info " href="#"><i class="fa fa-bars"></i> </a>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-user"></i> <span class="label label-primary"></span>【<@shiro.principal type="net.sppan.base.entity.UserModel" property="userName"/>】
                            </a>
                            <ul class="dropdown-menu dropdown-alerts">
                                <li>
                                    <a onclick="updatePwd()">
                                        <div>
                                            <i class="fa fa-refresh"></i> 修改密码
                                            <span class="pull-right text-muted small"><@shiro.principal type="net.sppan.base.entity.UserModel" property="userSid"/></span>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx!}/admin/logout">
                                        <div>
                                            <i class="fa fa-remove"></i> 注销
                                            <span class="pull-right text-muted small"><@shiro.principal type="net.sppan.base.entity.UserModel" property="userSid"/></span>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe id="J_iframe" width="100%" height="100%" src="${ctx!}/admin/welcome" frameborder="0" data-id="index_v1.html" seamless></iframe>
            </div>
        </div>
        <!--右侧部分结束-->
    </div>

    <!-- 全局js -->
    <script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctx!}/assets/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${ctx!}/assets/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx!}/assets/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${ctx!}/assets/js/plugins/layer/layer.min.js"></script>

    <!-- 自定义js -->
    <script src="${ctx!}/assets/js/hAdmin.js?v=4.1.0"></script>
    <script type="text/javascript" src="${ctx!}/assets/js/index.js"></script>
    <script type="text/javascript">
    function updatePwd(){
		layer.open({
	      type: 2,
	      title: '修改密码',
	      shadeClose: true,
	      shade: false,
	      area: ['893px', '600px'],
	      content: '${ctx!}/userCtrl/updatePwd',
	      end: function(index){
	    	  window.location.reload();
	    	 }
	    });
	}
</script>
</body>

</html>
