<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>社区群</title>
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
					社区群
					<small>社区群面板</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
                  		<h3 class="box-title">社区群查看</h3>
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
						<label for="name" class="col-sm-2 control-label">群名称</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.name}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="name_en" class="col-sm-2 control-label">Name</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.name_en}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="icon" class="col-sm-2 control-label">群图标</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.icon}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="groupQRcode" class="col-sm-2 control-label">群二维码</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.groupQRcode}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="minStarLevel" class="col-sm-2 control-label">最小星级要求</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.minStarLevel}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="status" class="col-sm-2 control-label">群状态 </label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.status}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="createTime" class="col-sm-2 control-label">创建时间</label>
						<div class="col-sm-6 input-group">
							<span class="form-control"><fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${data.createTime}'/></span>
						</div>
					</div>
					<div class="form-group">
						<label for="createPerson" class="col-sm-2 control-label">创建人</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.createPerson}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="manager" class="col-sm-2 control-label">群管理</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.manager}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="managerNum" class="col-sm-2 control-label">管理人数</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.managerNum}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="describe" class="col-sm-2 control-label">群简介</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.describe}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="communityId" class="col-sm-2 control-label">群所属社区</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data._getParentField("communityId","t_asset_community","id","name").toString()}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="synchron" class="col-sm-2 control-label">同步融云服务器和本地服务器。0-已同步，1-未同步</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.synchron}</span>
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
