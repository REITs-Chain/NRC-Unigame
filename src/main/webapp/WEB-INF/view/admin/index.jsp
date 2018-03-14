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
    <jsp:include page="common/main_style.jsp"></jsp:include>
    <script type="text/javascript">
    	var rootUrl="<%=WwSystem.getRoot(request)%>";
    </script>
  </head>
  <body class="skin-blue sidebar-mini">
	<!-- wrapper -->
	<div class="wrapper">
		<jsp:include page="common/main_top.jsp"></jsp:include>
		<jsp:include page="common/main_menu.jsp"></jsp:include>      
		
		<!-- Content Wrapper-->
		<div class="content-wrapper">
			<!-- Content Header-->
			<section class="content-header">
				<h1>
					首页
					<small>控制管理面板</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
                  		<h3 class="box-title">管理</h3>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
               		<div class="box-body">
               			<button type="button" class="btn btn-primary" onclick="OpenWin()">弹出窗口</button>
                 		
               		</div>
               		<div class="box-footer">
                 		<button type="button" class="btn btn-primary">确定</button>
               		</div>
                	<!-- /panel-body -->
              </div>
			  <!-- /panel -->
			  
			</section>
			<!-- /Main content -->
		</div>
		<!-- /Content Wrapper -->
	</div>
	<!-- /wrapper -->	
	<jsp:include page="common/main_footer.jsp"></jsp:include>
	<jsp:include page="common/main_right.jsp"></jsp:include>      
	
	<jsp:include page="common/main_script.jsp"></jsp:include>	
	
	<script type="text/javascript">
    function OpenWin(){
    	var win=WW.openWin("winid",rootUrl+"admin/Admin/list","请选择",1100,700,[
 			{
 			    text: "确定1",
 			    click: function () {
 			        $(this).dialog("close");
 			    }
 			},
 			{
 			    text: "取消",
 			    click: function () {
 			        $(this).dialog("close");
 			    }
 			}
 		]);
		//alert(win);
		//WW.closeWin("winid");
    }
	$(function() {	
		
	});
	</script>
	
	   
  </body>
</html>


