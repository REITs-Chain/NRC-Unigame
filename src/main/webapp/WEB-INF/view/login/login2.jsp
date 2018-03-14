<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=WwSystem.getRoot(request)%>">    
    <title>WDP</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<link rel="stylesheet" type="text/css" href="public/css/login.css" >
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div class="content">
        <span class="title">WDP 登陆</span>
        <div class="login_panel">
            <div class="login_title">登陆</div>
            <input type="text" id="username" name="username" title="用户名/手机号/邮箱" placeholder="请填写用户名/手机号码/邮箱" />
            <input type="password" id="password" name="password" title="密码" placeholder="请填写密码" />
            <div class="ww_button">登陆</div>
            <div class="ww_button">注册</div>
        </div>
	</div>
  </body>
</html>
