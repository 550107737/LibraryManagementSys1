<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx!}/assets/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="${ctx!}/assets/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">

    <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">

    <style>
        #product_right{
            width: 300px;
            height: 500px;
            float: right;
            margin: 20px;
        }
        #product_down{
            width: 80%;
            height: 100%;
            float: left;

        }
        #big1{
            width: 544px;
            height: 248px;
            float: right;
            margin: 20px;

        }
        #big2{
            width: 800px;
            height: 248px;
            margin: 20px;
            float:left;
            border: 1px solid red;

        }
        #big3{
            width: 544px;
            height: 248px;
            float: left;
            margin: 20px;


        }
        #big4{
            width: 544px;
            height: 248px;
            float: left;
            margin: 20px;


        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight" id="father">
    <!--4.最新商品-->
    <div id="product">
        <div id="product_bottom" >
            <div id="product_right" class="white-bg">
                <p style="color: grey;font-size: 18px;">热门书籍：</p>
                <span style="color: lightskyblue;font-size: 20px;" id="popularBook"></span>
            </div>
            <div>
                <div id="big1" >
                    <div id="container1" style="min-width: 310px; height: 100%; margin: 0 auto"></div>
                </div>
                <div style="height: 270px">
                    <div class="white-bg" style="width: 200px; height: 124px;text-align: center;margin: 20px;float: left">
                        <span style="color: lightskyblue;font-size: 36px;" id="totalBorrow">0</span>
                    <#--<p style="color: lightskyblue;font: 36sp;" id="totalBorrow">0</p>-->
                        <p style="color: gray;font-size: 18px;">书籍总借阅数量</p>

                    </div>
                    <div class="white-bg" style="width: 200px; height: 124px;text-align: center;margin: 20px;float: left">
                        <span style="color: lightskyblue;font-size: 36px;" id="totalBook">0</span>
                        <p style="color: gray;font-size: 18px;">总书籍数量</p>
                    </div>
                    <div class="clear" ></div>
                    <div style="width: 200px; height: 124px;text-align: center;margin: 20px;" class="white-bg">
                        <span style="color: lightskyblue;font-size: 36px;" id="totalPeople">0</span>
                        <p style="color: gray;font-size: 18px;">总借阅人数</p>
                    </div>
                </div>
                <div>
                    <div id="big2" >
                        <div id="container2" style="min-width: 310px; height: 100%; margin: 0 auto"></div>
                    </div>
                    <div class="white-bg" style="width: 350px; height: 248px;text-align: center;margin: 20px;float: right">
                        <p style="color: grey;font-size: 16px;">活跃用户前十名：</p>
                        <span style="color: lightskyblue;font-size: 18px;" id="userTop10"></span>
                    </div>
                </div>


            </div>
            <div id="product_down">
                <div id="big3" class="white-bg">
                    <p style="color: grey;font-size: 18px;">热门书柜：</p>
                    <span style="color: lightskyblue;font-size: 20px;" id="popularBookcase"></span>
                </div>
                <div id="big4" class="white-bg" >
                    <p style="color: grey;font-size: 18px;">行为分析：</p>
                    <span style="color: lightskyblue;font-size: 20px;" id="">暂无</span>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 全局js -->
<script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
<!-- 自定义js -->
<script src="${ctx!}/assets/js/highcharts.js"></script>
<script>
    var data1,totalBorrow,totalBook,totalPeople;
    var popularBook;
    $(function () {
        getData1();
        getData2();
        getChartData();
    });
    function getData1() {
        $.ajax({
            async:false,//同步
            url:'${ctx}/bookCtrl/getData1',
            type:'POST',
            dataType:'json',
            success:function(data){
                if(data.success){
                    totalBorrow=data.data[0];
                    totalBook=data.data[1];
                    totalPeople=data.data[2];
                    $('#totalBorrow').html(totalBorrow);
                    $('#totalBook').html(totalBook);
                    $('#totalPeople').html(totalPeople);
                    var userTop10str="";
                    for(var i=3;i<data.data.length;i++){
                        userTop10str+="第"+(i-2).toString()+"名:";
                        userTop10str+=data.data[i][0].substring(0,1)+"*"+data.data[i][0].substring(data.data[i][0].length-1,data.data[i][0].length)+" ";
                        userTop10str+="卡号后四位:******";
                        userTop10str+=data.data[i][1].substring(data.data[i][1].length-4,data.data[i][1].length);
                        userTop10str+='</br>';
                    }
                    $('#userTop10').html(userTop10str);
                }else{
                    console.log("数据库异常 ");
                }
            }
        });
    }
    function getData2() {
        $.ajax({
            async:false,//同步
            url:'${ctx}/bookCtrl/getData2',
            type:'POST',
            dataType:'json',
            success:function(data){
                if(data.success){
                    var array1=data.data[0];
                    var array2=data.data[1];

                    var popularBookStr="";
                    for(var i=0;i<array1.length;i++){
                        popularBookStr+=(i+1).toString()+". ";
                        popularBookStr+=array1[i];
                        popularBookStr+='</br>';
                    }
                    $('#popularBook').html(popularBookStr);
                    var popularBookcaseStr="";
                    for(i=0;i<array2.length;i++){
                        popularBookcaseStr+=(i+1).toString()+". ";
                        popularBookcaseStr+=array2[i];
                        popularBookcaseStr+='</br>';
                    }
                    $('#popularBookcase').html(popularBookcaseStr);

                }else{
                    console.log("数据库异常 ");
                }
            }
        });
    }
    function getChartData(){
        $.ajax({
            async:false,//同步
            url:'${ctx}/bookCtrl/getChartData',
            type:'POST',
            dataType:'json',
            success:function(data){
                if(data.success){
                    var array1=data.data[0];
                    var array2=data.data[1];
                    var dateArray1=new Array(),dataArray1=new Array();
                    //var array2=data.data[1];
                    for(var i=0;i<array1.length;i++){
                        dateArray1[i]=array1[i][0];
                        dataArray1[i]=array1[i][1];
                    }
                    showChart1(dateArray1,dataArray1);
                    var classificationArray2=new Array(),dataArray2=new Array();
                    for(var i=0;i<array2.length;i++){
                        classificationArray2[i]=array2[i][0];
                        dataArray2[i]=array2[i][1];
                    }
                    showChart2(classificationArray2,dataArray2);
                }else{
                    console.log("数据库异常 ");
                }
            }
        });
    }
    function showChart1(xAxis,data1) {
        new Highcharts.chart('container1', {
            chart: {
                type: 'line'
            },
            title: {
                text: '近6月借书统计图'
            },
            xAxis: {
                categories: xAxis
            },
            yAxis: {
                title: {
                    text: '（本）'
                }
            },
            credits: {
                enabled:false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            },legend: {
                enabled: false
            },

            series: [{
                name: '移动书柜',
                data: data1
            }]
        });
    }
    function showChart2(xAxis,data2) {
        new Highcharts.chart('container2', {
            chart: {
                type: 'column'
            },
            title: {
                text: '借阅书籍类型统计分布'
            },
            xAxis: {
                categories: xAxis
            },
            yAxis: {
                title: {
                    text: '（本）'
                }
            },
            credits: {
                enabled:false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            },legend: {
                enabled: false
            },

            series: [{
                name: '书籍分类',
                data: data2
            }]
        });
    }
</script>
</body>
</html>
