
  //去掉字串左边的空格
  function lTrim(str) {
    if (str.charAt(0) == " " || str.charAt(0) == "　") {
      str = str.slice(1);
      str = lTrim(str);
    }
    return str;
  }

  //去掉字串右边的空格
  function rTrim(str) {
    var iLength;
    iLength = str.length;
    if (str.charAt(iLength - 1) == " " || str.charAt(iLength - 1) == "　") {
      str = str.slice(0, iLength - 1);
      str = rTrim(str);
    }
    return str;
  }

  //去掉字串两边的空格
  function trim(str) {
    return lTrim(rTrim(str));
  }

  //验证表单域
  function MyCheck() {
    var sno = document.getElementById("sno").value;
    sno = trim(sno);
    if (sno == "") {
      layui.use('layer', function(){
        var layer = layui.layer;

        layer.msg('用户名不能为空');
      });
      return false;
    }

    var password = document.getElementById("password").value;
    password = trim(password);
    if (password == "") {
      layui.use('layer', function(){
        var layer = layui.layer;

        layer.msg('密码不能为空');
      });
      return false;
    }

     ajaxRequest(sno,password);
   /* var xmlHttp=new XMLHttpRequest("Msxml2.XMLHTTP");
    var url = "login.do?username="+userName+"&password="+password;
    xmlHttp.open("post", url, true);
    xmlHttp.onreadystatechange=function() {
      if (xmlHttp.readyState==4) {
            alert("hh");
      }
    }
    xmlHttp.send();*/
  }

  function ajaxRequest( sno, password) {
    var ajax;
    if(window.XMLHttpRequest){//火狐
      ajax=new XMLHttpRequest();
    }else if(window.ActiveXObject){//ie
      ajax=new ActiveXObject("Msxml2.XMLHTTP");
    }
    //复写onreadystatechange函数
    ajax.onreadystatechange=function(){
      //判断Ajax状态码
      if(ajax.readyState==4){
        //判断响应状态吗
        if(ajax.status==200){
          //获取响应内容
          var result=eval(ajax.responseText);
          //alert(result);
          //处理响应内容
          if(result){
            location.href="studentmain.do";
            //alert("正确");
          }else{
            alert("登陆失败");
          }
          //获取元素对象
        }else if(ajax.status==404){
          alert("请求资源不存在")
        }else if(ajax.status==500){
          alert("服务器繁忙")
        }
      }
    }

    ajax.open("post", "studentlogin.do");
    ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    ajax.send("sno="+sno+"&password="+password);
  }

