<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>WDP</title>
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
					测试模板
					<small>测试模板面板</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
                  		<h3 class="box-title">测试模板编辑</h3>
                  		<a title="返回" class="badge bg-green" href="admin/Test/list">返回</a>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<form:form commandName="data" id="form1" action="admin/Test/save" method="post" class="form-horizontal">
                  		<div class="box-body">
                  		<c:if test="${error!=null&&error!=''}">
                  		    <label for="id" class="col-sm-2 control-label"></label>
                  			<div class="form-group wwerror"><div class="col-sm-6 input-group">
                  				${error}
                  			</div></div>
                  		</c:if>
					<div class="form-group">
						<label for="fid" class="col-sm-2 control-label">fid</label>
						<div class="col-sm-6 input-group">
						    <input type="text" id="fid" name="fid" value="${data.fid}" class="form-control pull-right" readonly="readonly" ww-integer />
							<form:errors path="fid" class="input-group-addon wwerror"></form:errors> 
						</div>
					</div>
					<div class="form-group">
						<label for="ftext" class="col-sm-2 control-label">ftext</label>
						<div class="col-sm-6 input-group">
						    <input type="text" id="ftext" name="ftext" value="${data.ftext}" class="form-control pull-right" ww-string />
							<form:errors path="ftext" class="input-group-addon wwerror"></form:errors> 
						</div>
					</div>
					<div class="form-group">
						<label for="fint" class="col-sm-2 control-label">fint</label>
						<div class="col-sm-6 input-group">
						    <input type="text" id="fint" name="fint" value="${data.fint}" class="form-control pull-right" ww-integer />
							<form:errors path="fint" class="input-group-addon wwerror"></form:errors> 
						</div>
					</div>
					<div class="form-group">
						<label for="fdouble" class="col-sm-2 control-label">fdouble</label>
						<div class="col-sm-6 input-group">
						    <input type="text" id="fdouble" name="fdouble" value="${data.fdouble}" class="form-control pull-right" ww-double />
							<form:errors path="fdouble" class="input-group-addon wwerror"></form:errors> 
						</div>
					</div>
					<div class="form-group">
						<label for="fdate" class="col-sm-2 control-label">fdate</label>
						<div class="col-sm-6 input-group">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>							
							<input type="text" id="fdate" name="fdate" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${data.fdate}'/>" class="form-control pull-right" ww-date />
							<form:errors path="fdate" class="input-group-addon wwerror"></form:errors> 
						</div>
					</div>
					<div class="form-group">
						<label for="fdatetime" class="col-sm-2 control-label">fdatetime</label>
						<div class="col-sm-6 input-group">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>							
							<input type="text" id="fdatetime" name="fdatetime" value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${data.fdatetime}'/>" class="form-control pull-right" ww-datetime />
							<form:errors path="fdatetime" class="input-group-addon wwerror"></form:errors> 
						</div>
					</div>
					<div class="form-group">
						<label for="fboolean" class="col-sm-2 control-label">fboolean</label>
						<div class="col-sm-6 input-group">
							<form:checkbox path="fboolean" value="${data.fboolean}" class="flat-red" style="margin-top: 10px;"/>							
							<form:errors path="fboolean" class="input-group-addon wwerror"></form:errors> 
						</div>
					</div>
					<div class="form-group">
						<label for="fselectfix" class="col-sm-2 control-label">固定选择</label>
						<div class="col-sm-6 input-group">
						    <form:select name="fselectfix" id="fselectfix" class="form-control" path="fselectfix" value="${data.fselectfix}">
									<form:option value="0">未开始</form:option>
									<form:option value="1">进行中</form:option>
									<form:option value="2">已结束</form:option>									
							</form:select>
							<form:errors path="fselectfix" class="input-group-addon wwerror"></form:errors> 
						</div>
					</div>
					<div class="form-group">
						<label for="fselectdt" class="col-sm-2 control-label">动态选择</label>
						<div class="col-sm-6 input-group">
						    <form:select name="fselectdt" id="fselectdt" class="form-control" path="fselectdt" value="${data.fselectdt}">
									<form:option value="未开始">未开始</form:option>
									<form:option value="进行中">进行中</form:option>
									<form:option value="已结束">已结束</form:option>									
							</form:select>
							<form:errors path="fselectdt" class="input-group-addon wwerror"></form:errors> 
						</div>
					</div>
					<div class="form-group">
						<label for="flook" class="col-sm-2 control-label">关联查询</label>
						<div class="col-sm-6 input-group">
						    <input type="text" id="txt_flook" name="txt_flook" value="${data.flook}" class="form-control pull-right" readonly="readonly" />
                    		<input type="hidden" id="flook" name="flook" value="${data.flook}" />
                    		<span class="input-group-addon" onclick="WW.look('w_admin','flook');"><i class="fa fa-search"></i></span>
                    		<form:errors path="flook" class="input-group-addon wwerror"></form:errors>						
						</div>						 
					</div>
					<div class="form-group">
						<label for="fueditor" class="col-sm-2 control-label">富文本框</label>
						<div class="col-sm-6 input-group">
							<!-- 百度编辑框-->
	               	        <textarea id="fueditor" name="fueditor" style="width:100%;height:260px;">${data.fueditor}</textarea>
	               	        <!--end 百度编辑框 -->
							<form:errors path="fueditor" class="input-group-addon wwerror"></form:errors> 
						</div>
					</div>
					<div class="form-group">
						<label for="fimage" class="col-sm-2 control-label">图片</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="fimage" name="fimage" readonly="readonly" value="${data.fimage}" class="form-control pull-right"/>
							<input type="file" id="file_fimage" name="file_fimage"  onchange="WW.AjaxUploadImage('file_fimage','fimage');" />
							<img src="public/file_list/images/${data.fimage}" id="img_fimage" alt="图片" height="150" width="150"/>
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
    <script src="public/js/ajaxfileupload.js" type="text/javascript"></script>
    <script src="public/js/frame.js" type="text/javascript"></script>
    <script type="text/javascript" charset="utf-8" src="public/ueditor1_4_3/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="public/ueditor1_4_3/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="public/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
	    WW.initUEditor('fueditor');
    </script>
    <script type="text/javascript">
		var state="${state}";
    	if(state=="2"){
    		$("form input").prop("readonly", true);
    		$("#wwsubmit").hide();
    	}

    </script>    
  </body>
</html>
