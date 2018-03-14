<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=WwSystem.getRoot(request)%>">  
    <title>NRC | 登陆</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.4 -->
    <link href="public/adminLTE/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- Font Awesome Icons -->
    <link href="public/font-awesome-4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="public/css/login.css" >

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="public/adminLTE/other/html5shiv.min.js"></script>
        <script src="public/adminLTE/other/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <div class="content">
        <span class="title">NRC 登陆</span>
        <form id="form1" action="login/AdminLogin/login_in" method="post">
        <div class="login_panel">
            <div class="login_title">登陆</div>
            <div style="color:red">${message}</div>
            <div class="input-group">
		       <span class="input-group-addon"><i class="fa fa-user"></i></span>
		       <input type="text" name="username" title="用户名/手机号/邮箱" class="form-control ww_input" value="${username}" placeholder="请填写用户名/手机号码/邮箱">
		    </div>
		    <br>
            <div class="input-group">
		       <span class="input-group-addon"><i class="fa fa-lock"></i></span>
		       <input type="password" id="password" name="password" title="密码" class="form-control ww_input" placeholder="请填密码">
		    </div>
		    <br/>
		    <div class="input-group">
		       <span class="input-group-addon">记住我</span>
		       <div class="form-control" >
		       <input type="checkbox" id="rememberMe" name="rememberMe" />
		       </div>
		    </div>
		    <br/>
            <button type="submit" class="ww_button">登陆</button>
            <button class="ww_button">注册</button>
        </div>
        </form>
	</div>

    <!-- jQuery 2.1.4 -->
    <script src="public/jquery/jquery-2.1.4.min.js" type="text/javascript"></script>
    <!-- Bootstrap 3.3.2 JS -->
    <script src="public/adminLTE/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>    
  </body>
</html>

