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
	<!-- panel -->
	<div class="box box-primary">
		<!-- panel-header -->
		<div class="box-header with-border">
            <div class="btn-group btn-group-sm pull-left">
		  		<a type="button" class="btn btn-default" href="Look/look?tableName=${tableName }">刷新</a>
			</div>
			<form id="table_form" action="admin/Test/list" method="post">
				<input type="hidden" name="page" value="1"/>
				<input type="hidden" name="beginRow" value="1"/>					
				<input type="hidden" name="pageRows" value="0"/>
				<input type="hidden" name="tableName" value="${tableName }"/>
				<div class="pull-right" style="width:200px;margin-bottom: 3px;">
					<div class="input-group">
		            	<input type="text" name="query" class="form-control" placeholder="搜索..." />
		            	<span class="input-group-addon"><i class="fa fa-search"></i></span>
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
			    	<c:forEach var="field" items="${fieldList}" varStatus="status">
			    	<th>${field.fieldTitle }</th>
                    </c:forEach>
	        	</tr>
	    	</thead>
	    	<tbody>
		    	<c:forEach var="item" items="${list}">
		        <tr >
		            <td style="width:50px">
						<a class="btn btn-xs btn-default" href="javascript:;" onclick="select(this);"><i class="am-icon-edit"></i>选择</a>
		            </td>
		            <td style="width:30px;"><input type="checkbox"></td>
		            <c:forEach var="field" items="${fieldList }" varStatus="status">
	                <td>${item.get(field.fieldName) }</td>
	                </c:forEach>
		        </tr>
		        </c:forEach>
	    	</tbody>
            </table>
            <ul id="paging" class="pagination"></ul>              
        </div>
        <!-- /panel-body -->
    </div>
    <!-- /panel -->
	
	<jsp:include page="../admin/common/main_script.jsp"></jsp:include>
	<script src="public/js/wwcommon.js" type="text/javascript"></script>
    <script type="text/javascript">
   		$(function(){  	
   			initTablePaging("table_list","table_form",${page},${pageRows},${allRows});
   		});	
    	
   		function select(me){
   			var tds=WW.getCurRowTDs(me);
   			var data=[];
			for(var i=2;i<tds.length;i++){
				data[i-2]=tds[i].innerText;
			}
   			return_fun(data);
   		}
    </script>    
  </body>
</html>
