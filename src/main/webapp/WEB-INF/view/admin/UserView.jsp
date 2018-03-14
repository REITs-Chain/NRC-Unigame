<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>会员用户</title>
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
					会员用户
					<small>会员用户面板</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
                  		<h3 class="box-title">会员用户查看</h3>
						<a type="button" class="btn btn-success" href="javascript:history.go(-1);">返回</a>
						<%-- <c:if test="${data.certificationStatus!=2 }">
							<a type="button" class="btn btn-success" href="javascript:onPass(${data.id})">认证通过</a>
							<a type="button" class="btn btn-danger" href="javascript:onDine(${data.id})">拒绝认证</a>
		            	</c:if> --%>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<form:form commandName="data" id="form1" action="" method="post" class="form-horizontal">
                  		<div class="box-body">					
							
					<div class="form-group">
						<label for="id" class="col-sm-2 control-label">会员ID</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.id}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="phoneNum" class="col-sm-2 control-label">手机号码</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.phoneNum}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-2 control-label"></label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.password}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="nickName" class="col-sm-2 control-label">昵称</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.nickName}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="realName" class="col-sm-2 control-label">真实姓名</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.realName}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="gender" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.gender}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="idType" class="col-sm-2 control-label">身份类型</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.idType}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="idNum" class="col-sm-2 control-label">身份证号码</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.idNum}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="idPhoto1" class="col-sm-2 control-label">身份图片1</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.idPhoto1}</span>
							<img src="admin/uploadimage/getimage?savePath=private/images/&fileName=${data.idPhoto1}" id="img_idPhoto1" alt="身份图片1图片" style=" max-width:800px;"/>
						</div>
					</div>
					<div class="form-group">
						<label for="idPhoto2" class="col-sm-2 control-label">身份图片2</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.idPhoto2}</span>
							<img src="admin/uploadimage/getimage?savePath=private/images/&fileName=${data.idPhoto2}" id="img_idPhoto2" alt="身份图片2图片" style=" max-width:800px;"/>
						</div>
					</div>
					<div class="form-group">
						<label for="photo" class="col-sm-2 control-label">手持身份照片</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.photo}</span>
							<img src="admin/uploadimage/getimage?savePath=private/images/&fileName=${data.photo}" id="img_photo" alt="手持身份照片图片" style=" max-width:800px;"/>
						</div>
					</div>
					<div class="form-group">
						<label for="avatar" class="col-sm-2 control-label">头像</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.avatar}</span>
							<img src="public/image_list/userhead/${data.avatar}" id="img_avatar" alt="头像图片" style=" max-width:500px;"/>
						</div>
					</div>
					<div class="form-group">
						<label for="status" class="col-sm-2 control-label">状态</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.status}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="certificationStatus" class="col-sm-2 control-label">认证状态</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.certificationStatus}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="certificationTime" class="col-sm-2 control-label">认证时间 </label>
						<div class="col-sm-6 input-group">
							<span class="form-control"><fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${data.certificationTime}'/></span>
						</div>
					</div>
					<div class="form-group">
						<label for="loginLock" class="col-sm-2 control-label">是否锁定</label>
						<div class="col-sm-6 input-group">
							<form:checkbox path="loginLock" value="${data.loginLock}" class="flat-red" disabled="true"/>
						</div>
					</div>
					<div class="form-group">
						<label for="loginTimes" class="col-sm-2 control-label">登录次数</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.loginTimes}</span>
						</div>
					</div>
					<div class="form-group">
						<label for="walletAddress" class="col-sm-2 control-label">钱包地址</label>
						<div class="col-sm-6 input-group">
							<span class="form-control">${data.walletAddress}</span>
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
