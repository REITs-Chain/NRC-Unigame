<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>资产信息</title>
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
					资产信息
					<small>资产信息面板</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
                  		<h3 class="box-title">资产信息查看</h3>
				<a type="button" class="btn btn-success" href="javascript:history.go(-1);">返回</a>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<form:form commandName="data" id="form1" action="" method="post" class="form-horizontal">
                  		<div class="box-body">					
							
					<div class="form-group">
						<label for="id" class="col-sm-2 control-label">ID</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.id}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="sname" class="col-sm-2 control-label">资产简称</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.sname}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">资产名称</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.name}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="name_en" class="col-sm-2 control-label">name</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.name_en}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="circulation" class="col-sm-2 control-label">资产发行量</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.circulation}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="issueAddress" class="col-sm-2 control-label">发型地址</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.issueAddress}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="issueTime" class="col-sm-2 control-label">发行时间</label>
						<div class="col-sm-6 input-group">
							<span class="form-control"><fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${data.issueTime}'/></span>
						</div>
					</div>
					<div class="form-group">
						<label for="status" class="col-sm-2 control-label">资产状态</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.status}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="imageUrl" class="col-sm-2 control-label">资产图标URL</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.imageUrl}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="produtIntroUrl" class="col-sm-2 control-label">产品介绍URL</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.produtIntroUrl}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="issueDatum" class="col-sm-2 control-label">发行资料URL</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.issueDatum}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="title" class="col-sm-2 control-label">标题</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.title}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="content" class="col-sm-2 control-label">内容</label>
						<div class="col-sm-9 input-group">
							${data.content}
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
