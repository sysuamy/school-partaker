<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'returnPage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	
  </head>
  <script type="text/javascript" src="<%=path%>/js/jquery-1.6.min.js"></script> 
  <script type="text/javascript"> 
  	function buttonClick (){
  		var xxx = $("#test").val();
		window.alert(xxx);
		var url = "<%=path%>/my/userJson";
		jQuery.get(url,function(jsonData){
			window.alert(jsonData.suser);
		},"json");

	}
  </script>
  <body>
   这是返回页面<input name="test" id="test" value="xxx">
   <input value="按钮" type="button" onclick="buttonClick();"/>
  </body>
</html>
