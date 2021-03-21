<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ogs.domain.Files" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

     <script type="text/javascript">
         function sc() {
             $("#file").click().change(function () {
                 var filename = $("img").attr("src").substring(7);
                 //alert("图片名称："+photo_name);
                 $.ajax({
                     type: "post",
                     url: "files/upload?filename=" + filename,
                     //dataType:"json",
                     enctype: "multipart/form-data",
                     data: new FormData($("#uploadForm")[0]),
                     processData: false, //data的值是FormData对象，不需要对数据进行处理
                     contentType: false,//FormData对象由form表单构建
                     cache: false,
                     success: function (msg) {
                         layui.use('layer', function(){
                             var layer = layui.layer;

                             layer.msg("回传的:"+msg);
                         });
                         // alert("回传的:"+msg);
                         /* $("img").attr("src","upload/"+msg);  */
                         // if (!msg.endsWith(".jpg") && !msg.endsWith(".jpeg")) {
                         //     //$("span").html(msg);
                         //     $("img").attr("src", "images/" + photo_name);
                         // } else {
                         //     $("img").attr("src", "images/" + msg);
                         // }
                     }
                 });
                 //}
             });
         }


         function download(fileName){
                location.href="/files/download?fileName="+fileName;
         }
     </script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <span style="color:red;"> ${registSuccess}</span>
	   <button class="layui-btn" style="margin: 3px 3px" onclick="modifyUserinfoById('${user.userid}')">chexk</button>

<%--    <form enctype="multipart/form-data" id="uploadForm">--%>
<%--&lt;%&ndash;        <img src="images/default.jpg" style="width: 50px;height: 50px;" onclick="sc()"/>&ndash;%&gt;--%>

<%--        <input type="file" name="file" id="file" style="display: none">--%>
<%--    </form>--%>

    <h3>文件上传</h3>
    <form action="/files/upload" enctype="multipart/form-data" method="post">
        <input type="file" name="uploadfile">
        <input type="submit">
    </form>

    <h3>文件夹创建</h3>
    <form action="/files/mkdir">
        <input type="hidden" name="isDir" value="1">
        <input type="hidden" name="type" value="dir">
        <input type="hidden" name="isDel" value="0">
        <input type="hidden" name="url" value="wwww">
        <input type="hidden" name="size" value="1024">
        <input type="hidden" name="parentId" value="${sessionScope.parentId}">
        <input name="fileName">
        <input type="submit">
    </form>

    <h3>filelist</h3>
    <table>
        <thead>
        <tr><th>filename</th><th>operation</th></tr>
        </thead>
        <tbody>
        <c:forEach items="${filelist}" var="file" >
            <tr id="tr${file.fid}">
                <c:choose>
                    <c:when test="${file.isDir==0}">
                        <th>${file.fileName}</th>
                        <th>
                            <button onclick="download('${file.fileName}')">download</button> &nbsp;&nbsp;&nbsp;&nbsp;
                        </th>
                    </c:when>
                    <c:when test="${file.isDir==1}">
                        <th><a href="files/main?parentId=${file.fid}&parentDir=${requestScope.parentDir}">${file.fileName}</a></th>
                        <th>&nbsp;&nbsp;&nbsp;
                        </th>
                    </c:when>
                </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
  </body>
</html>
