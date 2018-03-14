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
    <script type="text/javascript" src="public/jquery/jquery-1.11.3.js"></script>
    <script type="text/javascript">
    	var rootUrl="<%=WwSystem.getRoot(request)%>";    	
    </script>
    <script type="text/javascript">
    	function wwClick(){
			$.ajax({url:rootUrl+"/getJson",
				type:'post',
				dataType:'json',
				success:function(a,b,c){
					alert(b);
				},
				complete:function(a,b){
					alert(b);
				}
			});
		}

    </script>
</head>
<body style="text-align: center">
    <br/><br/>
    <input type="button" value="testJson" onclick="wwClick()">
</body>
</html>
