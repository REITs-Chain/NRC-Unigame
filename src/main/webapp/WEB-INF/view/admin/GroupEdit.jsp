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
                  		<h3 class="box-title">社区群编辑</h3>
				<a type="button" class="btn btn-success" href="javascript:history.go(-1);">返回</a>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<form:form commandName="data" id="form1" action="admin/Group/save" method="post" class="form-horizontal">
                  		<div class="box-body">
                  			<c:if test="${error!=null&&error!=''}">
                  		    <label for="error_id" class="col-sm-2 control-label"></label>
                  			<div class="form-group wwerror"><div class="col-sm-6 input-group">
                  				${error}
                  			</div></div>
                  			</c:if>							
							
					<div class="form-group">
						<label for="id" class="col-sm-2 control-label">ID</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="id" name="id" value="${data.id}" class="form-control pull-right" readonly="readonly" ww-integer />
							<form:errors path="id" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">群名称</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="name" name="name" value="${data.name}" class="form-control pull-right"  ww-string  />
							<form:errors path="name" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="name_en" class="col-sm-2 control-label">Name</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="name_en" name="name_en" value="${data.name_en}" class="form-control pull-right"  ww-string />
							<form:errors path="name_en" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="icon" class="col-sm-2 control-label">群图标</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="icon" name="icon" value="${data.icon}" class="form-control pull-right"  ww-string />
							<form:errors path="icon" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="groupQRcode" class="col-sm-2 control-label">群二维码</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="groupQRcode" name="groupQRcode" value="${data.groupQRcode}" class="form-control pull-right"  ww-string />
							<form:errors path="groupQRcode" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="minStarLevel" class="col-sm-2 control-label">最小星级要求</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="minStarLevel" name="minStarLevel" value="${data.minStarLevel}" class="form-control pull-right"  ww-integer />
							<form:errors path="minStarLevel" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="status" class="col-sm-2 control-label">群状态 </label>
						<div class="col-sm-6 input-group">
							<input type="text" id="status" name="status" value="${data.status}" class="form-control pull-right"  ww-integer />
							<form:errors path="status" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="createTime" class="col-sm-2 control-label">创建时间</label>
						<div class="col-sm-6 input-group">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" id="createTime" name="createTime" value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${data.createTime}'/>" class="form-control pull-right"  ww-datetime />
							<form:errors path="createTime" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="createPerson" class="col-sm-2 control-label">创建人</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="createPerson" name="createPerson" value="${data.createPerson}" class="form-control pull-right"  ww-string />
							<form:errors path="createPerson" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="manager" class="col-sm-2 control-label">群管理</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="manager" name="manager" value="${data.manager}" class="form-control pull-right"  ww-string />
							<form:errors path="manager" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="managerNum" class="col-sm-2 control-label">管理人数</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="managerNum" name="managerNum" value="${data.managerNum}" class="form-control pull-right"  ww-integer />
							<form:errors path="managerNum" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="describe" class="col-sm-2 control-label">群简介</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="describe" name="describe" value="${data.describe}" class="form-control pull-right"  ww-string />
							<form:errors path="describe" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="communityId" class="col-sm-2 control-label">群所属社区</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="txt_communityId" name="txt_communityId" value="${data._getParentField("communityId","t_asset_community","id","name").toString()}" class="form-control pull-right" readonly="readonly" />
                            <input type="hidden" id="communityId" name="communityId" value="${data.communityId}" />
                            <span class="input-group-addon" onclick="lookWin('communityId','t_asset_community','id','name');"><i class="fa fa-search"></i></span>
							<form:errors path="communityId" class="input-group-addon wwerror"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="synchron" class="col-sm-2 control-label">是否同步</label>
						<div class="col-sm-6 input-group">
							<input type="text" id="synchron" name="synchron" value="${data.synchron}" class="form-control pull-right"  ww-integer />
							<form:errors path="synchron" class="input-group-addon wwerror"></form:errors>
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
		//通用关联查询
		function lookWin(to_cmdId,tableName,valueField,textField,showField){				
			var url=rootUrl+"Look/look?tableName="+tableName;
			if(valueField!=null){
				url+="&valueField="+valueField;
			}
			if(textField!=null){
				url+="&textField="+textField;
			}
			if(showField!=null){
				url+="&showField="+showField;
			}
			WW.look(url,to_cmdId);
		}
    </script>    
  </body>
</html>
