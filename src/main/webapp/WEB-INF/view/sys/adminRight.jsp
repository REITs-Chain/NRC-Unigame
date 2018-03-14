<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <title>WDP</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="../admin/common/main_style.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="public/jstree/dist/themes/default/style.min.css"/>
    <style type="text/css">
    	.hidcol{
    		display: none;
    	}
    </style>
    <script type="text/javascript">
    var rootUrl="<%=WwSystem.getRoot(request)%>";
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
		<jsp:include page="../admin/common/main_top.jsp"></jsp:include>
		<jsp:include page="../admin/common/main_menu.jsp"></jsp:include>      
		
		<!-- Content Wrapper-->
		<div class="content-wrapper">
			<!-- Content Header-->
			<section class="content-header">
				<h1>
					功能权限
					<small>用户权限管理</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
				        <h3 class="box-title pull-left"  style="margin-right: 20px;margin-top: 8px">用户权限管理</h3>
				        <h3 class="box-title pull-left"  style="margin-right: 20px;margin-top: 8px">用户：${admin.name }</h3>
                  		<div class="btn-group btn-group-sm pull-left">                  		
						  <a type="button" class="btn btn-default" onclick="history.back();" href="javascript:;">返回</a>
						</div>
						<div class="pull-right" style="width:200px;">
						<div class="input-group">
			              <input type="text" name="query" class="form-control" placeholder="搜索..." />
			              <span class="input-group-addon"><i class="fa fa-search"></i></span>
			            </div>
			            </div>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
                	<div class="box-body">
                		<div id="tree_4" class="tree-demo"></div>           
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
	
	<jsp:include page="../admin/common/main_footer.jsp"></jsp:include>
	<jsp:include page="../admin/common/main_right.jsp"></jsp:include>      
	
	<jsp:include page="../admin/common/main_script.jsp"></jsp:include>
	
	<script src="public/jstree/dist/jstree.min.js"></script>
	<script src="public/js/wwcommon.js" type="text/javascript"></script>
    <script type="text/javascript">
   		$(function(){   			
   			wwtree();
   		});
   		
   		function wwtree() {
   			$.ajaxSetup({cache:false});//ajax调用不使用缓存
   	        var tree=$("#tree_4").jstree({
   	        	ui:{
   	        		initially_select : [ "frame@Amin" ] 
   	        	},
   	        	//check_callback : true,
   	            core : {
   	                themes : {
   	                    responsive: false
   	                }, 
   	                // so that create works
   	                
   	               check_callback : function (operation, node, node_parent, node_position, more) {
   	     		 				// operation can be 'create_node', 'rename_node', 'delete_node', 'move_node' or 'copy_node'
   	     		 				// in case of 'rename_node' node_position is filled with the new node name
   	     		 				alert(node);
   	     		 				return operation === 'rename_node' ? true : false;
   	     		 			},
   	                data : {
   	                    'url' : function (node) {
   	                    	return rootUrl+"admin/FrameRight/getNodes"; //'demo/jstree_ajax_data.php';
   	                    },
   	                    data : function (node) {
   	                    	return { 'id' : node.id,'adminId':${admin.id} };
   	                    }
   	                }
   	            },
   	            types : {
   	                "default" : {
   	                    icon : "fa fa-folder icon-state-warning icon-lg"
   	                },
   	                file : {
   	                    icon : "fa fa-file icon-state-warning icon-lg"
   	                }
   	            },
   	            state : { "key" : "demo3" },
   	            //plugins : [ "dnd", "state", "types" ]
   	            plugins: ["wholerow", "checkbox", "types"]
   	         	    
   	        }); 
   	        
   	        tree.bind('click.jstree', function(event) {
   	        	 var node=event.target;
   	        	 var node_id="";
   	        	 if(node.outerHTML=='<i class="jstree-icon jstree-ocl"></i>'){ //点击展开和收缩图标
   	        	 	return;
   	        	 }
   	        	 if(node.parentNode.id!=undefined
   	        			 &&node.parentNode.id!=null
   	        			 &&node.parentNode.id!=""){
   	        		 node_id=node.parentNode.id;
   	        	 }else if(node.parentNode.parentNode.id!=undefined&&
   	        			 node.parentNode.parentNode.id!=null&&
   	        			 node.parentNode.parentNode.id!=""){
   	        		 node_id=node.parentNode.parentNode.id;
   	        	 }
   	        	 var selected=$("#tree_4").jstree("is_selected",node);
	             onTree_checkbox_click(node_id,selected);
	     	});
   	    }
   		
   		function onTree_checkbox_click(node_id,selected){
   			//alert(node_id+" "+selected);   			
   			$.ajax({     
   				type : 'POST',   
   				url : rootUrl+"admin/FrameRight/setRight",     
   				data : {
   					nodeId:node_id,
   					adminId:${admin.id},
   					checked:selected
   				},     
   				dataType : 'json',     
   				success : function(data) {
   					alert(data.message); 
   				},     
   				error : function(a,b,c) {     
   					alert("error");    
   				}     
   			}); 
   		}
    
    </script>    
  </body>
</html>
