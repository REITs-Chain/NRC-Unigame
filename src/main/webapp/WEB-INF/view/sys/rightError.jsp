<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>WDP</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="../admin/common/main_style.jsp"></jsp:include>
    <style type="text/css">
    	.hidcol{
    		display: none;
    	}
    </style>
    <script type="text/javascript">
    var rootUrl="<%=WwSystem.getRoot(request)%>";
    function onDelete(id){
    	var result =confirm("你确定要删除吗?");
    	if(result ==true){
    		return true;
    	}else{
    		return false;
    	}
    }
    function onCheck(me){
    	var v=me.checked;
    	if(v==true){
    		var list=$("#table_list input[type='checkbox']");
    		$.each(list,function(){
    			this.checked="checked";
    		});
    	}else{
    		//$("#test1").removeAttr("checked");
    		$("#table_list input[type='checkbox']").removeAttr("checked");
    	}
    }
    </script>
  </head>
  <body class="skin-blue sidebar-mini">
	<!-- wrapper -->
	<div class="wrapper">
		<jsp:include page="../admin/common/main_top.jsp"></jsp:include>
		<jsp:include page="../admin/common/main_menu.jsp"></jsp:include>      
		
		<!-- Content Wrapper-->
		<div class="content-wrapper">
			<!-- Content Header-->
			<section class="content-header">
				<h1>
					温馨提示
					<small>权限面板</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
				        <h3 class="box-title pull-left"  style="margin-right: 20px;margin-top: 8px">用户：${admin.name }</h3>
                  		<div class="btn-group btn-group-sm pull-left">
						</div>
						<div class="pull-right" style="width:200px;">
						<div class="input-group">
			            </div>
			            </div>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<div class="box-body">
                		 权限异常：${message }   
                		 <br>
                		 <a type="button" class="btn btn-default" onclick="goback()" href="javascript:;">返回</a>      
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
	
	<jsp:include page="../admin/common/main_footer.jsp"></jsp:include>
	<jsp:include page="../admin/common/main_right.jsp"></jsp:include>      
	
	<jsp:include page="../admin/common/main_script.jsp"></jsp:include>

	<script src="public/js/wwcommon.js" type="text/javascript"></script>
    <script type="text/javascript">
    	function goback(){
    		history.back();
    		return false;
    	}
    </script>    
  </body>
</html>
