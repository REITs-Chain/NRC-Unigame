<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>机构信息</title>
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
					机构信息
					<small>机构信息面板</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
                  		<h3 class="box-title">机构信息编辑</h3>
				<a type="button" class="btn btn-success" href="admin/OrganInfo/list">返回</a>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<form:form commandName="data" id="form1" action="admin/OrganInfo/save" method="post" class="form-horizontal">
                  		<div class="box-body">
                  			<c:if test="${error!=null&&error!=''}">
                  		    <label for="error_id" class="col-sm-2 control-label"></label>
                  			<div class="form-group wwerror"><div class="col-sm-6 input-group">
                  				${error}
                  			</div></div>
                  			</c:if>							
							
					<div class="form-group">
						<label for="id" class="col-sm-2 control-label"></label>
						<div class="col-sm-6 input-group">
							<input type="text" id="id" name="id" value="${data.id}" class="form-control pull-right" readonly="readonly" ww-integer />
							<form:errors path="id" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="type" class="col-sm-2 control-label">机构类型</label>
						<div class="col-sm-6 input-group">
							<form:select name="type" id="type" path="type" value="${data.type}" class="form-control">
                            	<form:option value="0">基金公司</form:option>
                            	<form:option value="1">审核机构</form:option>
                            </form:select>
							<form:errors path="type" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="userId" class="col-sm-2 control-label">用户ID</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="userId" name="userId" value="${data.userId}" class="form-control pull-right"  ww-integer />
							<form:errors path="userId" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">名称</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="name" name="name" value="${data.name}" class="form-control pull-right"  ww-string />
							<form:errors path="name" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="name_en" class="col-sm-2 control-label">name</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="name_en" name="name_en" value="${data.name_en}" class="form-control pull-right"  ww-string />
							<form:errors path="name_en" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="url" class="col-sm-2 control-label">官网背书网址</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="url" name="url" value="${data.url}" class="form-control pull-right"  ww-string />
							<form:errors path="url" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="regTime" class="col-sm-2 control-label">注册时间</label>
						<div class="col-sm-6 input-group">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" id="regTime" name="regTime" value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${data.regTime}'/>" class="form-control pull-right"  ww-datetime />
							<form:errors path="regTime" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="content" class="col-sm-2 control-label">介绍信息</label>
						<div class="col-sm-6 input-group">
							<textarea id="content" name="content" style="width:100%;height:260px;">${data.content}</textarea>
							<form:errors path="content" class="input-group-addon wwerror"></form:errors>
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
<script type="text/javascript" charset="utf-8" src="public/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="public/ueditor1_4_3/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="public/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>

	<script type="text/javascript">
WW.initUEditor('content');
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
