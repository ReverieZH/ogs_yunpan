<%@ page import="java.util.List" %>
<%@ page import="com.ogs.domain.Files" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
		<title></title>
        <link rel="stylesheet" href="<%=basePath%>static/layui/css/layui.css">
        <script src="<%=basePath%>static/layui/layui.js"></script>
		<style>
			/*.table_context{
			    font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
			    width:1050px;
			    border-collapse:collapse;
			}

			.table_context td,.table_context th{
			    font-size:1em;
			    padding:3px 7px 2px 7px;
			}
			.table_context th{
			    font-size:10px;
			    text-align:center;
			    padding-top:5px;
			    padding-bottom:4px;
			    background-color:#ccc;
			    color:#ffffff;
			}
			.table_context td{
				border-bottom: solid #ccc 1px;
			}*/
			input[type="checkbox"] {
			    width: 15px;
			    height: 15px;
			    display: inline-block;
			    text-align: center;
			    vertical-align: middle;
			    line-height: 18px;
			    margin-right: 10px;
			    position: relative;
            }
 
			input[type="checkbox"]::before {
			    content: "";
			    position: absolute;
			    top: 0;
			    left: 0;
			    background: #fff;
			    width: 100%;
			    height: 100%;
			    border: 1px solid #d9d9d9;
			}
 
			input[type="checkbox"]:checked::before {
			    content: "\2713";
			    background-color: #fff;
			    position: absolute;
			    top: 0;
			    left: 0;
			    width: 100%;
			    border: 1px solid #7D7D7D;
			    color: #7D7D7D;
			    font-size: 20px;
			    font-weight: bold;
			}

		</style>
		
		<style>
       		 .inner-container {
	            position: absolute; left: 0;
	            overflow-x: hidden;
	            overflow-y: scroll;
	        }
	        /* for Chrome ????????????????????????*/      
	          .inner-container::-webkit-scrollbar {
	            display: none;
	        }
    	</style>
    	<script>
    			function getiframe(url){
				layui.use('layer', function(){
				  var layer = layui.layer;
				  layer.open({
				  type: 2,
				      maxmin: true,
				      shadeClose: true, //?????????????????????
                      area : ['800px' , '200px'],
				  content: url //??????content??????????????????String
				});
				})
			    }

                function changeStatus(academicId,Status) {
                    ajaxRequest(academicId,Status);
                }

                function ajaxRequest( academicId,status) {
                    var ajax;
                    if(window.XMLHttpRequest){//??????
                        ajax=new XMLHttpRequest();
                    }else if(window.ActiveXObject){//ie
                        ajax=new ActiveXObject("Msxml2.XMLHTTP");
                    }
                    //??????onreadystatechange??????
                    ajax.onreadystatechange=function(){
                        //??????Ajax?????????
                        if(ajax.readyState==4){
                            //?????????????????????
                            if(ajax.status==200){
                                //??????????????????
                                var result=eval(ajax.responseText);
                                //alert(result);
                                //??????????????????
                                if(result){
                                    window.location.reload();
                                    //alert("??????");
                                }else{
                                    alert("????????????");
                                }
                                //??????????????????
                            }else if(ajax.status==404){
                                alert("?????????????????????")
                            }else if(ajax.status==500){
                                alert("???????????????")
                            }
                        }
                    }

                    ajax.open("post", "changeStatus.do");
                    ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                    ajax.send("academicId="+academicId+"&status="+status);
                }


                function changepagesize() {
                    var pageisze=document.getElementById("pageisze");
                    var index=pageisze.selectedIndex;
                    var limit = pageisze.options[index].value; // ?????????
                    location.href="main.do?limit="+limit;
                }


                function deleleAcademic(){
                    var checkbox = document.getElementsByName('academicId');
                    var url="";
                    for (var i = 0; i < checkbox.length; i++) {
                        if (checkbox[i].checked) {
                            url+="academicId="+checkbox[i].value+"&";
                        }
                    }
                    url=url.substring(0,url.length-1);
                    deleteajaxRequest(url);
                }
                function deleteajaxRequest(url) {
                    var ajax;
                    if(window.XMLHttpRequest){//??????
                        ajax=new XMLHttpRequest();
                    }else if(window.ActiveXObject){//ie
                        ajax=new ActiveXObject("Msxml2.XMLHTTP");
                    }
                    //??????onreadystatechange??????
                    ajax.onreadystatechange=function(){
                        //??????Ajax?????????
                        if(ajax.readyState==4){
                            //?????????????????????
                            if(ajax.status==200){
                                //??????????????????
                                var result=eval(ajax.responseText);
                                //alert(result);
                                //??????????????????
                                if(result){

                                    window.location.reload();
                                    alertmessage("????????????");
                                    //alert("??????");
                                }else{
                                    alertmessage("????????????");
                                }
                                //??????????????????
                            }else if(ajax.status==404){
                                alert("?????????????????????")
                            }else if(ajax.status==500){
                                alert("???????????????")
                            }
                        }
                    }

                    ajax.open("get", "deleteAcademic.do?"+url);
                    ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                    ajax.send();
                }

                function deleteOneAcademic(academicId){
                    deletajax(academicId);
                }

                function deletajax( academicId ) {
                    var ajax;
                    if(window.XMLHttpRequest){//??????
                        ajax=new XMLHttpRequest();
                    }else if(window.ActiveXObject){//ie
                        ajax=new ActiveXObject("Msxml2.XMLHTTP");
                    }
                    //??????onreadystatechange??????
                    ajax.onreadystatechange=function(){
                        //??????Ajax?????????
                        if(ajax.readyState==4){
                            //?????????????????????
                            if(ajax.status==200){
                                //??????????????????
                                var result=eval(ajax.responseText);
                                //alert(result);
                                //??????????????????
                                if(result){
                                    window.location.reload();
                                    //alert("??????");
                                }else{
                                    alert("????????????");
                                }
                                //??????????????????
                            }else if(ajax.status==404){
                                alert("?????????????????????")
                            }else if(ajax.status==500){
                                alert("???????????????")
                            }
                        }
                    }

                    ajax.open("post", "delete.do");
                    ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                    ajax.send("academicId="+academicId);
                }

                function alertmessage(inform){
                    layui.use('layer', function() {
                        var layer = layui.layer;
                        layer.msg(inform);
                    });
                }

                function checkAll() {
                    var checkALl=document.getElementById("check_all_box");
                    var checkbox = document.getElementsByName('academicId');
                    if(checkALl.checked) {
                        for (var i = 0; i < checkbox.length; i++) {
                            if (!checkbox[i].checked) {
                                checkbox[i].checked = true;
                            }
                        }
                    }else{
                        for (var i = 0; i < checkbox.length; i++) {
                            if (checkbox[i].checked) {
                                checkbox[i].checked = false;
                            }
                        }
                    }
                }
    	</script>
    	
	</head>
	<body style=" overflow-x:hidden;"  class="inner-container">
		<div style="color: #666;">
<%--            <a style="margin-top: 5px;" href="javascript:history.back()" ><i class="layui-icon layui-icon-next" style="color: #666;"></i><cite>??????????????????</cite></a>--%>
            <a style="width: 10px;height: 10px" href="javascript:location.reload()"><i class="layui-icon layui-icon-refresh"></i> </a>
            <button style="float: right;" class="layui-btn layui-btn-primary" onclick="deleleAcademic()">???????????????</button>
		    <button style="float: right;" class="layui-btn layui-btn-primary" onclick="getiframe('getaddAcademic.do')">????????????</button>
		
		<table class="layui-table" style="width: 1050px;" lay-skin="line">
		             <tr>
                        <td width="2%" height="20" align="center" > <input type="checkbox" onchange="checkAll()" id="check_all_box">??????</td>
                        <td width="10%" height="20" align="center" >????????????</td>
                        <td width="12%" height="20" align="center">??????</td>
                    </tr>
             </thead>

            <c:forEach items="${requestScope.filelist}" var="file">
                    <tr>
                        <td width="2%" height="50" align="center"><input  type="checkbox" name="academicId" value="${file.fid}"></td>
                        <c:choose>
                            <c:when test="${file.isDir==0}">
                                <td width="2%" height="20" align="center"><i class="layui-icon layui-icon-file" style="font-size: 30px;"></i>${file.fileName}</td>
                                <td height="20" align="center">
                                    <button class="layui-btn" onclick="download('${file.fileName}')">??????</button>
                                    <button class="layui-btn" onclick="download('${file.fileName}')">??????</button>
                                    <button class="layui-btn" onclick="download('${file.fileName}')">??????</button>
                                </td>
                            </c:when>
                            <c:when test="${file.isDir==1}">
                                <td width="2%" height="20" align="center"><i class="layui-icon layui-icon-file" style="font-size: 30px; color:#FFB800 "></i><a href="files/main?parentId=${file.fid}&parentDir=${requestScope.parentDir}/${file.fileName}"+>${file.fileName}</a></td>
                                <td height="20" align="center">&nbsp;&nbsp;
                                    <button class="layui-btn" onclick="download('${file.fileName}')">??????</button>
                                    <button class="layui-btn" onclick="download('${file.fileName}')">??????</button>
                                </td>
                            </c:when>
                        </c:choose>
                        <%--<td width="2%" height="50" align="center">${file.fileName}</td>
                        <td width="2%" height="50" align="center">${file.type}</td>--%>
                        <%--&lt;%&ndash;<td width="10%" height="50" align="center">
                            <%= pageBean.getList().get(i).getAcademicName()%><br>
                        </td>
                        <%
                            if(pageBean.getList().get(i).getStatus().equals("1")){
                        %>
                        <td width="5%" height="50" align="center" style="color: green" >?????????</td>
                        <%}else{%>
                        <td width="5%" height="50" align="center" style="color: red">?????????</td>
                        <%}%>&ndash;%&gt;
                        <td width="12%" height="50" align="center"><button class="layui-btn">??????</button> <button class="layui-btn layui-btn-warm"  >??????</button> <button class="layui-btn layui-btn-danger" onclick="deleteOneAcademic('')">??????</button></td>--%>
                    </tr>
            </c:forEach>
		</table>
		</div>
	</body>

</html>
