<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>内测用户信息</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="common/main_style.jsp"></jsp:include>
    <!-- iCheck for checkboxes and radio inputs -->
    <style type="text/css">
    	.form-control{
    		border: none 0px;
    	}
    </style>
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
					内测用户信息
					<small>内测用户信息面板</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
                  		<h3 class="box-title">内测用户信息查看</h3>
				<a type="button" class="btn btn-success" href="javascript:history.go(-1);">返回</a>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<form:form commandName="data" id="form1" action="" method="post" class="form-horizontal">
                  		<div class="box-body">					
							
					<div class="form-group">
						<label for="userId" class="col-sm-2 control-label">对应用户ID</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.userId}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="realName" class="col-sm-2 control-label">真实姓名</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.realName}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="idNum" class="col-sm-2 control-label">身份证号码</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.idNum}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="phoneNum" class="col-sm-2 control-label">内测用的手机号码</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.phoneNum}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="status" class="col-sm-2 control-label">状态</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">
							<c:choose>
                            	<c:when test="${data.status==0 }">未验证</c:when>
                            	<c:when test="${data.status==1 }">验证通过</c:when>
                            	<c:when test="${data.status==2 }">已处理完成</c:when>
                            	<c:when test="${data.status==-1 }">验证失败</c:when>
                            	<c:when test="${data.status==-2 }">处理失败</c:when>
                            </c:choose>
							</span>
						</div>
					</div>
					<div class="form-group">
						<label for="createTime" class="col-sm-2 control-label">创建时间</label>
						<div class="col-sm-6 input-group">
							<span class="form-control"><fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${data.createTime}'/></span>
						</div>
					</div>
					<div class="form-group">
						<label for="note" class="col-sm-2 control-label">备注</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.note}</span>
						</div>
					</div>

							
						</div>
						<!-- /panel-body -->
						<!-- panel-footer -->
                  		<div class="box-footer">
                  		</div>
                  		<!-- /panel-footer -->
                	</form:form>
                	
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
    <!-- iCheck 1.0.1 -->
    <script src="public/adminLTE/plugins/iCheck/icheck.min.js" type="text/javascript"></script>

	
    <script type="text/javascript">
    </script>    
  </body>
</html>
