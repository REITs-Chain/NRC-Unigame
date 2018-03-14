<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>社区群</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="common/main_style.jsp"></jsp:include>    
    <style type="text/css">
    	.hidcol{
    		display: none;
    	}

    	@media (max-width: 767px){
    		tr{
    			display:inline-block;
	    	}
	    	td{
	    		display:inline-block;
	    	}
    	}
    </style>
	<script type="text/javascript">
    	var rootUrl="<%=WwSystem.getRoot(request)%>";
    </script> 
    <script type="text/javascript">
    
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
				        <h3 class="box-title pull-left"  style="margin-right: 20px;margin-top: 8px">社区群管理</h3>
                  		<div class="btn-group btn-group-sm pull-left">
						  <a type="button" class="btn btn-success" href="admin/Group/edit">新增</a>
						  <a type="button" class="btn btn-warning" href="admin/Group/list">刷新</a>
						</div>
						<form id="table_form" action="admin/Group/list" method="post">
							<input type="hidden" name="page" value="1"/>					
							<input type="hidden" name="pageRows" value="0"/>
							
							<div class="pull-right" style="width:200px;margin-bottom: 3px;">
								<div class="input-group">
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
								    <th><input type="checkbox" onclick="onCheck(this)"></th>
                                    <th>ID</th>
                                    <th>群名称</th>
                                    <th>Name</th>
                                    <th>群图标</th>
                                    <th>星级</th>
                                    <th>创建时间</th>
                                    <th>创建人</th>
                                    <th>群简介</th>
                                    <th>群社区</th>
                                    <th>是否同步</th><!-- 同步融云服务器和本地服务器。0-已同步，1-未同步 -->
                                    <th>群状态 </th><!-- 0-已审核 1-未通过  2-待审核 -->
									<th>后台操作</th>
						        </tr>
						    </thead>
						    <tbody>
						    	<c:forEach var="item" items="${list}">
						        <tr >
						            <td style="width:130px">
						            	<div class="btn-group btn-group-xs" style="margin: 0;">
										 <a class="btn btn-primary" href="javascript:onEdit(${item.id})"><i class="am-icon-edit" style="margin-right:4px;"></i>修改</a>
										 <a class="btn btn-info" href="javascript:onView(${item.id})"><i class="am-icon-edit"></i>查看</a>
										 <a class="btn btn-danger" href="javascript:onDelete(${item.id});"><i class="am-icon-remove" style="margin-right:4px;"></i>删除</a>
										</div>
						            </td>
						            <td style="width:30px"><input type="checkbox"></td>
                                    <td>${item.id}</td>
                                    <td>${item.name}</td>
                                    <td>${item.name_en}</td>
                                    <td><%-- ${item.icon} --%>
                                    	<img src="${item.icon}" alt="群组图标" height="150" width="150"/>
                                    </td>
                                    <td>${item.minStarLevel}</td>
                                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.createTime}"/></td>
                                    <td>${item.createPerson}</td>
                                    <td>${item.describe}</td>
                                    <td>${item._getParentField("communityId","t_asset_community","id","name").toString()}</td>
                                    <td>${item.synchron==0?'已同步':'未同步'}</td>
                                    <td>${item.status==0?'已通过':(item.status==1?'未通过':'待审核')}</td>
									<td style="width:150px">
                                    	<div class="btn-group btn-group-xs" style="margin: 0;">
										<c:if test="${item.status==2 }">
											<a class="btn btn-info" href="#" onclick="doloss(${item.id});return false;"><i class="am-icon-edit"></i>执行审核</a>
											<%-- <button name="adminId" class="btn btn-primary btn-lg" data-toggle="modal" 
											   data-target="#myModal"  id="${item.id}">执行找回
											</button> --%>
										</c:if>
										<c:if test="${item.status==0 }">
										<a class="btn btn-info" href="#" onclick="viewGroup(${item.id});return false;"><i class="am-icon-edit"></i>群成员</a>
											<%-- <button name="adminId" class="btn btn-primary btn-lg" data-toggle="modal" 
											   data-target="#myModal"  id="${item.id}">执行找回
											</button> --%>
										</c:if>
										</div>
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
	                <h4 class="modal-title" id="myModalLabel">是否执行审核</h4>
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
    	
    	function viewGroup(id){
    		var url="admin/Group/viewUser?";
   			url+="id="+id;
   			window.location=url;
    	}
    	var select_id=0;
   		function doloss(_id){
   			select_id=_id;
   			$('#myModal').modal('show');
   		}
   		
	    function doloss_ok() {
  			var url="admin/Group/examine";
	   			$.ajax({
	                type: "POST",
	                url: url,
	                data: {
	                	id:select_id
	                },
	                dataType: "json",
	                success: function(data){
	                	if(data.success){                		
	                		$('#table_form').submit(); //刷新
	                		alert("审核通过");
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
	   		
	    }
    	
   		$(function(){ 	
   			//初始化表格分页
   			initTablePaging("table_list","table_form",${page},${pageRows},${allRows});
   		});	
   		
   		function onNew(){   			
   			var url="admin/Group/edit";		
   			window.location=url;
   		}
   		function onEdit(id){
   			var url="admin/Group/edit?";
   			url+="id="+id;			
   			window.location=url;
   		}  
   		function onView(id){
   			var url="admin/Group/view?";
   			url+="id="+id; 			
   			window.location=url;
   		}  
	    
	    //删除记录
	    function onDelete(id){	    	
	    	var result =confirm("你确定要删除吗?");
	    	if(result ==true){
	    		doAjaxDelete(id);	    		
	    	}
	    	return false;//取消a链接的跳转
	    }
	    //执行删除
	    function doAjaxDelete(id){
	    	$.ajax({
			    url:'admin/admin/Group/delete',
			    type:'POST', //GET
			    async:true,    //或false,是否异步
			    data:{
			        id:id
			    },
			    timeout:30000,    //超时时间
			    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
			    success:function(data,textStatus,jqXHR){
			    	if(data.code==0){ //成功
			    		alert(data.message);
			    		$("#table_form").submit();
			    	}else{//失败
			    		alert(data.message);
			    	}
			    },
			    error:function(xhr,textStatus){
			        alert("网络或配置异常！");
			        console.log(xhr);
			        console.log(textStatus);
			    }
			});
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
  </body>
</html>
