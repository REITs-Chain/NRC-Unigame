<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>挂失</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="common/main_style.jsp"></jsp:include>    
    <style type="text/css">
    	.hidcol{
    		display: none;
    	}
    	.w-box{
    		border-radius: 8px;
    		overflow: hidden;
    	}
    	.w-input{
    		margin-top: 10px;
    		margin-left:auto;
    		margin-right:auto;
    		width:80%;
    	}
    </style>
	<script type="text/javascript">
    	var rootUrl="<%=WwSystem.getRoot(request)%>";
    </script> 
    <script type="text/javascript">
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
    		$("#table_list input[type='checkbox']").prop("checked",'checked');
    	}else{
    		$("#table_list input[type='checkbox']").removeAttr("checked");
    	}
    }
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
					挂失
					<small>挂失面板</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
				        <h3 class="box-title pull-left"  style="margin-right: 20px;margin-top: 8px">挂失管理</h3>
                  		<div class="btn-group btn-group-sm pull-left">
						  <!-- <a type="button" class="btn btn-success" href="admin/ReportLoss/edit">新增</a> -->
						  <a type="button" class="btn btn-warning" href="admin/ReportLoss/list">刷新</a>						  
						</div>
						<form id="table_form" action="admin/ReportLoss/list" method="post">
							<input type="hidden" name="page" value="1"/>
							<input type="hidden" name="beginRow" value="0"/>					
							<input type="hidden" name="pageRows" value="0"/>
							
							<div class="pull-right" style="width:200px;margin-bottom: 3px;">								
								<div class="input-group">
									<!--<form:select name="status" id="status" class="form-control" path="status" value="${status}">						
										<form:option value="0">未处理</form:option>
									  	<form:option value="1">审核通过</form:option>						  						  	
									  	<form:option value="2">已找回</form:option>
									  	<form:option value="-1">审核失败</form:option>
									  	<form:option value="-2">找回失败</form:option>
									  	<form:option value="-3">其他问题</form:option>								
									</form:select>-->
									<select id="status" name="status" class="form-control" >
									  	<option value="0">未处理</option>
									  	<option value="1">审核通过</option>						  						  	
									  	<option value="2">已找回</option>
									  	<option value="-1">审核失败</option>	
									  	<option value="-2">找回失败</option>
									  	<option value="-3">其他问题</option>
									</select>									
					            	<input type="text" name="query" value="${query }" class="form-control" placeholder="搜索..." />
					            	<span class="input-group-addon" onclick="$('#table_form').submit()"><i class="fa fa-search"></i></span>
					            </div>
				            </div>					
						</form>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<div class="box-body">
                		<table id="table_list" class="table table-striped table-bordered table-condensed">
		                    <thead>
						        <tr>
						        	<th>操作</th>
								    <!-- <th><input type="checkbox" onclick="onCheck(this)"></th> -->
                                    <!-- <th>ID</th>-->
                                    <th>手机号</th>
                                    <th>实名</th>
                                    <th>老钱包地址</th>
                                    <!-- <th>新钱包</th> -->
                                    <th>申请时间</th>
                                    <th>状态</th>
									<th>后台处理</th>
						        </tr>
						    </thead>
						    <tbody>
						    	<c:forEach var="item" items="${list}">
						        <tr >
						            <td style="width:260px">
						            	<div class="btn-group btn-group-xs" style="margin: 0;">
										 <!-- <a class="btn btn-primary" href="admin/ReportLoss/edit?id=${item.id}&state=1"><i class="am-icon-edit" style="margin-right:4px;"></i>修改</a> -->
										 <a class="btn btn-info" href="admin/ReportLoss/view?id=${item.id}"><i class="am-icon-edit"></i>查看</a>
										 <a class="btn btn-primary" href="#" onclick="watchOld(${item.id});return false;"><i class="am-icon-view"></i>老钱包余额</a>
										 <a class="btn btn-info" href="#" onclick="watchNew(${item.id});return false;"><i class="am-icon-view"></i>新钱包余额</a>
										 <!-- <a class="btn btn-danger" href="admin/ReportLoss/delete?id=${item.id}" onclick="return onDelete(${item.id})"><i class="am-icon-remove" style="margin-right:4px;"></i>删除</a> -->
										</div>
						            </td>
						            <!-- <td style="width:30px"><input type="checkbox"></td> -->
                                    <!-- <td>${item.id}</td> -->
                                    <td>${item.user.phoneNum}</td>
                                    <td>${item.user.realName}</td>
                                    <td>${item.walletAddressOld}</td>
                                    <!-- <td>${item.walletAddressNew}</td> -->
                                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.reportTime}"/></td>
                                    <c:if test="${item.status==0}">
                                    	<td>未处理</td>
                                    </c:if>
                                    <c:if test="${item.status==1}">
                                    	<td>审核通过</td>
                                    </c:if>
                                    <c:if test="${item.status==2}">
                                    	<td>已找回</td>
                                    </c:if>
                                    <c:if test="${item.status==-1}">
                                    	<td>审核失败</td>
                                    </c:if>
                                    <c:if test="${item.status==-2}">
                                    	<td>找回失败</td>
                                    </c:if>
                                    <c:if test="${item.status==-3}">
                                    	<td>其他问题</td>
                                    </c:if>
                                    <td style="width:150px">
                                    	<div class="btn-group btn-group-xs" style="margin: 0;">
                                    	<c:if test="${item.status==0 }">
											<a class="btn btn-primary" href="#" onclick="check(${item.id},1);return false;"><i class="am-icon-edit" style="margin-right:4px;"></i>审核通过</a>
											<a class="btn btn-danger" href="#" onclick="check(${item.id},-1);return false;"><i class="am-icon-remove" style="margin-right:4px;"></i>审核失败</a>
										</c:if>
										<c:if test="${item.status==1 }">
											<a class="btn btn-info" href="#" onclick="doloss(${item.id});return false;"><i class="am-icon-edit"></i>执行找回</a>
											<%-- <button name="adminId" class="btn btn-primary btn-lg" data-toggle="modal" 
											   data-target="#myModal"  id="${item.id}">执行找回
											</button> --%>
										</c:if>
										</div>
                                    </td>
						        </tr>
						        </c:forEach>
						    </tbody>
		                  </table>
		                  <ul id="paging" class="pagination">	  
						  </ul>              
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
	
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content w-box">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	                <h4 class="modal-title" id="myModalLabel">登录密码验证</h4>
	            </div>
	            <div class="form-group">
	                  <label class="sr-only" for="name">登录密码</label>
	                  <input type="password" class="form-control w-input" id="newPwd" placeholder="请输入登录密码">
	             </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" onclick="doloss_ok()" >确认</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	
		
	
	<jsp:include page="common/main_footer.jsp"></jsp:include>
	<jsp:include page="common/main_right.jsp"></jsp:include>      
	
	<jsp:include page="common/main_script.jsp"></jsp:include>
	<script src="public/js/wwcommon.js" type="text/javascript"></script>
    <script type="text/javascript">
   		$(function(){ 	
   			initTablePaging("table_list","table_form",${page},${pageRows},${allRows});
   			$("#status").val(${status});
   			
   		    //模态窗口
   			$('#myModal').modal('hide');
   			
   		});	
   		
   		function check(id,status){
   			var url="admin/ReportLoss/check"
   			var a=confirm("你确定执行审核吗？");
	   		if(a==true)
	   		{
	   			$.ajax({
	                type: "POST",
	                url: url,
	                data: {
	                	id:id, 
	                	pass:status
	                },
	                dataType: "json",
	                success: function(data){
	                	if(data.success){                		
	                		$('#table_form').submit(); //刷新
	                		alert("完成审核");
	                	}else{
	                		alert(data.msg);
	                	}
	                },
	                error : function(res) {  
	                	// view("异常！");  
	                	alert(JSON.stringify(res));  
	                }  
	            });
	   		}
   		}
   		
   		var select_id=0;
   		function doloss(_id){
   			select_id=_id;
   			$('#myModal').modal('show');
   		}
   		
	    function doloss_ok() {
  			var url="admin/ReportLoss/doloss";
  			var password=$("#newPwd").val();
	   		if(password.length>0)
	   		{
	   			$.ajax({
	                type: "POST",
	                url: url,
	                data: {
	                	id:select_id,
	                	password:password
	                },
	                dataType: "json",
	                success: function(data){
	                	if(data.success){                		
	                		$('#table_form').submit(); //刷新
	                		alert("完成找回");
	                	}else{
	                		alert(data.msg);
	                	}
	                	$('#myModal').modal('hide');
	                },
	                error : function(res) {  
	                	// view("异常！");  
	                	alert(JSON.stringify(res));  
	                }  
	            });
	   		}else{
	   			alert('请输入转账密码！');		   			
	   		}	  		 
	    }
   		
   		
   		function watchOld(id){
   			var url="admin/ReportLoss/getOldBalance"
   			$.ajax({
                type: "POST",
                url: url,
                data: {
                	id:id
                },
                dataType: "json",
                success: function(data){
                	if(data.success){                	
                		alert(data.msg);
                	}else{
                		alert(data.msg);
                	}
                },
                error : function(res) {  
                	// view("异常！");  
                	alert(JSON.stringify(res));  
                }  
            });
   		}
   		
   		function watchNew(id){
   			var url="admin/ReportLoss/getNewBalance"
   			$.ajax({
                type: "POST",
                url: url,
                data: {
                	id:id
                },
                dataType: "json",
                success: function(data){
                	if(data.success){                	
                		alert(data.msg);
                	}else{
                		alert(data.msg);
                	}
                },
                error : function(res) {  
                	// view("异常！");  
                	alert(JSON.stringify(res));  
                }  
            });
   		}
    
    </script>    
  </body>
</html>
