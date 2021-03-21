<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>疫站报</title>
	<link rel="stylesheet" href="<%=basePath%>static/layui/css/layui.css">
	<script src="<%=basePath%>static/layui/layui.js"></script>
  	<style>
  		#left{
  			width: 225px;	
  			height: 800px;
  			background-color: #393D49;
  		}
  		#left .div{
				margin-left:20px ;
			}	
			#imgdiv {
				position:absolute;
			  border-width:0px;
			  left:1000px;
			  top:5px;
			  width:300px;
			  height:40px;
			  font-family:'微软雅黑 Bold', '微软雅黑 Regular', '微软雅黑';
			  font-style:normal;
			  color:#FFFFFF;
			  text-align:left;
			  line-height:32px;

			}
			#logodiv{
				position:absolute;
				left:50px;
				top: 5px;
				width: 35px;
				height: 35px;
			}
			
			#logo {
				  border-width:0px;
				  left:0px;
				  top:0px;
				  width:35px;
				  height:35px;
      }
         
      #word {
							  border-width:0px;
							  position:absolute;
							  left:100px;
							  top:5px;
							  width:200px;
							  word-wrap:break-word;
							  color: #FFFFFF;
        }
        	   
        #right{
				    position:absolute;
				    margin-left: 20px;
				    margin-top: 10px;
				    left: 220px;
				    top:70px;
						width: 1050px;
						height: 600px;
						border: solid #ccc 1px ;
						display: inline;
						box-shadow: 0px 2px 5px #888888;

						padding: 10px;
						 overflow: hidden;
			}
			#right iframe{
				width: 1050px;
				height: 550px;
			
			}
			i{
				font-size: 23px;
         	color: #FFFFFF;
         	margin-right: 5px;
			}
  	</style>
  	
		<script>
		//一般直接写在一个js文件中
		function getiframe(url){
				layui.use('layer', function(){
				  var layer = layui.layer;
				  layer.open({
				  type: 2,
				      title: '修改任务',
				      maxmin: true,
				      shadeClose: true, //点击遮罩关闭层
				      area : ['800px' , '520px'],
				  content: url //这里content是一个普通的String
				});
				})
			}
				//注意：导航 依赖 element 模块，否则无法进行功能性操作
				layui.use('element', function(){
				  var element = layui.element;
				  
				  //…
				});

		function start(fid) {
			var inframe=document.getElementById("inframe");
			inframe.src="files/main?parentId="+fid;
		}
		</script> 
</head>
<body onload="start('<%=request.getSession().getAttribute("fid")%>')">
			<div id="top">
						<ul class="layui-nav" lay-filter="">
<%--					  <li class="layui-nav-item "><a href="../main.do">业务管理</a></li>--%>
<%--					  <li class="layui-nav-item layui-this"><a href="">数据管理</a></li>--%>
					 
					  <li class="layui-nav-item">
					    <a href=""><img src="//t.cn/RCzsdCq" class="layui-nav-img">我</a>
					    <dl class="layui-nav-child">
					      <dd><a >修改信息</a></dd>
					      <dd><a href="javascript:;">安全管理</a></dd>
					      <dd><a href="javascript:;">退了</a></dd>
					    </dl>
					  </li>
						</ul>
						
						<div id="imgdiv">
								<div id="logodiv" >
								    <img id="logo" class="img" src="<%=basePath%>static/img/u1167.svg"/>
								</div>
								<div id="word"  >
									<p style="font-size:18px;"><span style="font-family:'微软雅黑 Bold', '微软雅黑 Regular', '微软雅黑';font-weight:700;">OGS云盘</span></p>
								</div>
					   </div>
			</div>
			
		<div id="content">

			<div id="left" >
							<ul id="side_nav" class="layui-nav layui-nav-tree " style="margin-right: 10px;">
								<div class="div" >
									 <li class="layui-nav-item layui-nav-itemed"><span>文件管理</span><div style="float:right;"><i class="fa fa-angle-down" aria-hidden="true"></i></div>
									  <ul>
									   <li class="layui-this" ><a href="service_taskmainui.jsp"><i class="layui-icon layui-icon-spread-left"></i> 全部文件</a></li>
									   <li><a href="#"><i class="layui-icon layui-icon-component"></i>文档</a></li>
									   <li><a href="#"><i class="layui-icon layui-icon-component"></i>音乐</a></li>
									   <li><a href="#"><i class="layui-icon layui-icon-component"></i>影音</a></li>
									   <li><a href="#"><i class="layui-icon layui-icon-component"></i>压缩包</a></li>
									  </ul>
									 </li>
								 </div>
								<%-- <div class="div" >
									 <li class="layui-nav-item layui-nav-itemed"><span>首页门户管理</span><div style="float:right;"><i class="fa fa-angle-down" aria-hidden="true"></i></div>
									  <ul>
									   <li><a href="service_applymainui.html"><i class="layui-icon layui-icon-more"></i> 门户应用类型</a></li>
									   <li><a href="service_applymanageui.html"><i class="layui-icon layui-icon-template-1"></i>门户应用管理</a></li>
									  </ul>
									 </li>
							     </div>	
							     <div class="div" >
									 <li class="layui-nav-item layui-nav-itemed"><span>扫码签到</span><div style="float:right;"><i class="fa fa-angle-down" aria-hidden="true"></i></div>
									  <ul>
									   <li><a href="service_QRui.html"><i class="layui-icon layui-icon-auz"></i> 签到任务设置</a></li>
									  </ul>
									 </li>
								 </div>--%>
							</ul>
				  </div>
				
					<div id="right">
    			        <iframe id="inframe"  marginWidth=0 marginHeight=0  width="1050px" height="600px" scrolling="auto" frameBorder=0 style="float: right;right: 300px;"></iframe>
			  	    </div>
			</div>
		<script type="text/javascript">
		 (function(){
			  var navWrap=document.getElementById("side_nav");
			  var nav1s=navWrap.getElementsByTagName("span");
			  var nav2s=navWrap.getElementsByTagName("ul");
			  for(var i=0,len=nav1s.length;i<len;i++){
			   nav1s[i].onclick=(function(i){
			    return function(){
			     for(var j=0;j<len;j++){
			      nav2s[j].style.display="none";
			     }
			     nav2s[i].style.display="block";
			    }
			   })(i)
			  }
			 })()
		</script>
			
</body>
</html>