<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=WwSystem.getRoot(request)%>">
    <meta charset="UTF-8">
    <title>WDP_平台</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
    	var rootUrl="<%=WwSystem.getRoot(request)%>";
    </script>
    
  </head>  
  <body>
  <form action="doUploadImage" method="post" enctype="multipart/form-data">
	    <span>上传文件：</span><input type="file" name="upload" style="width:400px;" >   
	    <input type="submit" value="提    交"> 
	    <img src="${imageName}"/>
  </form>
  </body>
</html>
