<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>会员用户</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="common/main_style.jsp"></jsp:include>    
    <style type="text/css">
    	.hidcol{
    		display: none;
    	}
    </style>
	<script type="text/javascript">
    	var rootUrl="<%=WwSystem.getRoot(request)%>";
    </script> 
    <script type="text/javascript">
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
		<jsp:include page="common/main_top.jsp"></jsp:include>
		<jsp:include page="common/main_menu.jsp"></jsp:include>      
		
		<!-- Content Wrapper-->
		<div class="content-wrapper">
			<!-- Content Header-->
			<section class="content-header">
				<h1>
					${groupName}群用户列表
					<small>已加入群的用户</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
				        <h3 class="box-title pull-left"  style="margin-right: 20px;margin-top: 8px">用户列表</h3>
                  		<div class="btn-group btn-group-sm pull-left">
						  <!-- <a type="button" class="btn btn-success" href="admin/User/edit">新增</a> -->
						  <a type="button" class="btn btn-warning" href="admin/Group/viewUser">刷新</a>
						</div>
						<form id="table_form" action="admin/Group/viewUser" method="post">
							<input type="hidden" name="page" value="1"/>
							<input type="hidden" name="beginRow" value="0"/>					
							<input type="hidden" name="pageRows" value="0"/>
							
							<%-- <div class="pull-right" style="width:200px;margin-bottom: 3px;">
								<div class="input-group">
					            	<input type="text" name="query" value="${query }" class="form-control" placeholder="搜索..." />
					            	<span class="input-group-addon" onclick="$('#table_form').submit()"><i class="fa fa-search"></i></span>
					            </div> --%>
				            </div>					
						</form>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<div class="box-body">
                	<!-- /addUserToGroup -->
			              <div class="form-group">
							  <label for="phoneNum" class="col-sm-2 control-label" style="float:left">添加成员</label>
							  <div class="col-sm-6 input-group" style="float:left">
								  <input type="text" id="phoneNum" name="phoneNum" class="form-control pull-right"  ww-string  placeholder="请输入手机号码"/>
								  <!-- <form:errors path="phoneNum" class="input-group-addon wwerror" ww-string></form:errors>  -->
							  </div>
							  <div class="col-sm-1 input-group" style="float:left">
								  <input type="button" class="form-control pull-right" ww-string value="添加" onclick="addUserToGroup(${groupId})">
							  </div>
						  </div>
						  <!-- /addUserToGroup -->
                		<table id="table_list" class="table table-striped table-bordered table-condensed">
		                    <thead>
						        <tr>
								    <!-- <th><input type="checkbox" onclick="onCheck(this)"></th> -->
                                    <th>会员ID</th>
                                    <th>手机号码</th>
                                    <th>昵称</th>
                                    <th>真实姓名</th>
                                    <th>性别</th>
                                    <th>类型</th>
                                    <th>星级</th>
						        </tr>
						    </thead>
						    <tbody>
						    	<c:forEach var="item" items="${list}">
						        <tr>
						           <%--  <td style="width:30px"><input value="${item.id}" type="checkbox"></td> --%>
                                    <td>${item.id}</td>
                                    <td>${item.phoneNum}</td>
                                    <td>${item.nickName}</td>
                                    <td>${item.realName}</td>
                                    <td>${item.gender==1?'男':item.gender==2?'女':'保密'}</td>
                                    <td>${item.type==0?'普通用户':item.type==1?'基金公司':'审核结构'}</td>
                                    <td>
                                    	<input type="button" value="查看星级" onclick="viewUserGrade(${groupId},${item.id})">
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
	                <h4 class="modal-title" id="myModalLabel">用户星级</h4>
	            </div>
	            <div class="form-group">
	                  <label class="sr-only" for="name">星级</label>
	                  <input type="text" class="form-control w-input" id="grade"  readonly="readonly">
	             </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" onclick="view_ok()" >确认</button>
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
    	function addUserToGroup(groupId){
    		url="admin/Group/join";
    		var phoneNum=$("#phoneNum").val();
    		$.ajax({
	                type: "POST",
	                url: url,
	                data: {
	                	groupid:groupId,
	                	phoneNum:phoneNum
	                },
	                async:false,
	                success: function(data){
	                	$('#table_form').submit(); 
	                },
	                 error : function(res) { 
	                	alert(JSON.stringify(res));  
	                }   
	            }); 
    	}
    	function viewUserGrade(groupId,userId){
    		$('#myModal').modal('show');
    		
    		url="admin/Group/viewGrade";
    		$.ajax({
	                type: "POST",
	                url: url,
	                data: {
	                	id:groupId,
	                	userId:userId
	                },
	                async:false,
	                success: function(data){
	                	var ss=JSON.parse(data);
	                	document.getElementById("grade").value=ss.grade;
	                },
	                 error : function(res) { 
	                	alert(JSON.stringify(res));  
	                }   
	            }); 
    	}
    	
    	function view_ok(){
    		$('#myModal').modal('hide');
    		$('#table_form').submit(); //刷新
    	}
    	
    	
   		$(function(){ 	
   			initTablePaging("table_list","table_form",${page},${pageRows},${allRows});
   		});	
    	function joinUser(groupId){
    		var s ="";
    		//var s1=new Array();
    		var list=$("#table_list input[type='checkbox']:checked");
    		$.each(list,function(){
    			s+=","+this.value;
    			 //s1.push(this.value);
    		});
    		var groupJoinUserId=s.replace(",", "");
    		var url="admin/Group/join";	
    		$.ajax({
	                type: "POST",
	                url: url,
	                data: {
	                	id:groupId,
	                	groupJoinUserId:groupJoinUserId
	                },
	                success: function(data){
	                	var ss=JSON.parse(data)
	                	if(ss.success){                		
	                		$('#table_form').submit(); //刷新
	                		alert("完成添加");
	                	}else{
	                		alert(ss.msg);
	                	}
	                },
	                 error : function(res) { 
	                	alert(JSON.stringify(res));  
	                }   
	            }); 
    	}
    </script>    
  </body>
</html>
