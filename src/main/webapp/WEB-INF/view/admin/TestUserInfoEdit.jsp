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
    <!-- Date Picker -->
    <link href="public/adminLTE/plugins/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
    <!-- Datetime Picker -->
    <link href="public/adminLTE/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
    <!-- Daterange picker -->
    <link href="public/adminLTE/plugins/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
    <!-- iCheck for checkboxes and radio inputs -->
    <link href="public/adminLTE/plugins/iCheck/all.css" rel="stylesheet" type="text/css" />
    <!-- Bootstrap time Picker -->
    <link href="public/adminLTE/plugins/timepicker/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
    <!-- Select2 -->
    <link href="public/adminLTE/plugins/select2/select2.min.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    	.wwerror{
    		color:red;
    		font-size: 11px;
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
                  		<h3 class="box-title">内测用户信息编辑</h3>
				<a type="button" class="btn btn-success" href="javascript:history.go(-1);">返回</a>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<form:form commandName="data" id="form1" action="admin/TestUserInfo/save" method="post" class="form-horizontal">
                  		<div class="box-body">
                  			<c:if test="${error!=null&&error!=''}">
                  		    <label for="error_id" class="col-sm-2 control-label"></label>
                  			<div class="form-group wwerror"><div class="col-sm-6 input-group">
                  				${error}
                  			</div></div>
                  			</c:if>							
							
					<div class="form-group">
						<label for="userId" class="col-sm-2 control-label">对应用户ID</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="userId" name="userId" value="${data.userId}" class="form-control pull-right"  readonly="readonly" />
							<form:errors path="userId" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="realName" class="col-sm-2 control-label">真实姓名</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="realName" name="realName" value="${data.realName}" class="form-control pull-right"  ww-string />
							<form:errors path="realName" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="idNum" class="col-sm-2 control-label">身份证号码</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="idNum" name="idNum" value="${data.idNum}" class="form-control pull-right"  ww-string />
							<form:errors path="idNum" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="phoneNum" class="col-sm-2 control-label">内测用的手机号码</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="phoneNum" name="phoneNum" value="${data.phoneNum}" class="form-control pull-right"  ww-string />
							<form:errors path="phoneNum" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="status" class="col-sm-2 control-label">状态</label>
						<div class="col-sm-6 input-group">
							<form:select name="status" id="status" path="status" value="${data.status}" class="form-control">
                            	<form:option value="0">未验证</form:option>
                            	<form:option value="1">验证通过</form:option>
                            	<form:option value="2">已处理</form:option>
                            	<form:option value="-1">验证失败</form:option>
                            	<form:option value="-2">处理失败</form:option>
                            </form:select>
							<form:errors path="status" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="createTime" class="col-sm-2 control-label">创建时间</label>
						<div class="col-sm-6 input-group">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" id="createTime" name="createTime" value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${data.createTime}'/>" class="form-control pull-right"  readonly="readonly" />
							<form:errors path="createTime" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="note" class="col-sm-2 control-label">备注</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="note" name="note" value="${data.note}" class="form-control pull-right"  ww-string />
							<form:errors path="note" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>

							
						</div>
                  		<div class="box-footer">
                    			<button type="submit" id="wwsubmit" class="btn btn-primary">提交</button>
                  		</div>
                	</form:form>
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
    <!-- datepicker -->
    <script src="public/adminLTE/plugins/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
    <script src="public/adminLTE/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js" type="text/javascript"></script>
    <!-- daterangepicker -->
    <script src="public/adminLTE/plugins/daterangepicker/moment-cn.min.js" type="text/javascript"></script>
    <script src="public/adminLTE/plugins/daterangepicker/daterangepicker-cn.js" type="text/javascript"></script> 
    <!-- datetimepicker -->
    <script src="public/adminLTE/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
    <script src="public/adminLTE/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
    <!-- bootstrap time picker -->
    <script src="public/adminLTE/plugins/timepicker/bootstrap-timepicker.min.js" type="text/javascript"></script>
    <!-- iCheck 1.0.1 -->
    <script src="public/adminLTE/plugins/iCheck/icheck.min.js" type="text/javascript"></script>
    <!-- Select2 -->
    <script src="public/adminLTE/plugins/select2/select2.full.min.js" type="text/javascript"></script>
    <!-- InputMask -->
    <script src="public/adminLTE/plugins/input-mask/jquery.inputmask.js" type="text/javascript"></script>
    <script src="public/adminLTE/plugins/input-mask/jquery.inputmask.date.extensions.js" type="text/javascript"></script>
    <script src="public/adminLTE/plugins/input-mask/jquery.inputmask.extensions.js" type="text/javascript"></script>
    <script src="public/adminLTE/plugins/input-mask/jquery.inputmask.numeric.extensions.js" type="text/javascript"></script>
    <script src="public/adminLTE/plugins/input-mask/jquery.inputmask.phone.extensions.js" type="text/javascript"></script>
    <script src="public/adminLTE/plugins/input-mask/jquery.inputmask.regex.extensions.js" type="text/javascript"></script>
    <script src="public/js/wwinputmask.js" type="text/javascript"></script>
    <script src="public/js/frame.js" type="text/javascript"></script>

	<script type="text/javascript">

	</script>
    <script type="text/javascript">
		var state="${state}";
    	if(state=="2"){
    		$("form input").prop("readonly", true);
		$("select").prop("disabled", "disabled");
    		$("input[type=file]").hide();
    		$("#wwsubmit").hide();
    	}
    </script>    
  </body>
</html>
