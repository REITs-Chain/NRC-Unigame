<%@page language="java" import="java.util.*,ww.common.WwSystem" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE>
<html>
  <head>
    <base href="<%=WwSystem.getRoot(request)%>">    
    <title>编辑页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">	
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="renderer" content="webkit">
	<script type="text/javascript" src="public/jquery/jquery-1.11.3.js" ></script>
	<style type="text/css">
		#form1 span{
			width:200px;
			text-align: right;
			height:28px;
			display:block;
			color:#a0a0a0;
			font-size: 13pt;
			float: left;
			vertical-align:middle;
			line-height: 28px;
		}
		#form1 input[type='text'],select{		  
		  width:500px;
		  height:28px;
		  border:solid 1px #bbbbbb;
		  border-radius: 4px;
		  color:#777777;
		  float: left;
		  font-size: 12pt;
		}
		#form1 textarea{		  
		  width:500px;
		  height:80px;
		  border:solid 1px #bbbbbb;
		  border-radius: 4px;
		  color:#777777;
		  float: left;
		  font-size: 12pt;
		}
		#form1 div{
			clear:both;
			width:100%;
			padding: 5px;
			vertical-align: middle;
		}
		#form1 input[type='checkbox']{
			height: 28px;
		}
		.msg{
			float: left;
			height:28px;
			line-height: 28px;
			text-align: left;
		}
	</style>
	
  </head>  
  <body>  
  	<div class="">
  	<form:form id="form1" class="" commandName="data" action="maker/makeFrame" method="post">
  		<div>
	  	    <span>appPath:</span>
	  		<form:input path="appPath"  placeholder="输入 appPath"/>
  		</div>
  		<div>
	  	    <span>tableName:</span>
	  		<!--<form:input path="tableName"  placeholder="输入 tableName"/>-->
	  		<form:select path="tableName" items="${table_list}" placeholder="选择tableName"></form:select>
  		</div>
  		<div>
	  	    <span>sql:</span>
	  		<form:textarea path="sql" placeholder="输入 sql语句"/>
	  		<span>主表别名必须为m,并且不能有where、group by和order by语句</span>
  		</div> 
  		<div>
	  	    <span>keyName:</span>
	  		<form:input path="keyName"  placeholder="输入 keyName"/>
  		</div>
  		<div>
	  	    <span>generatedKeys:</span>
	  		<form:checkbox path="generatedKeys" value="generatedKeys" />
  		</div>  		
  		<div>
	  	    <span>entityType:</span>
	  		<form:input path="entityType"  placeholder="输入 entityType"/>
  		</div>
  		<div>
  			<span>表连接映射:</span>
	  		<form:textarea path="linkMaping"  placeholder="连接映射:[{column:字段名,propetry:属性名,to_entity:对应实体类},...]"/>
  			<br/>
  			<span>连接映射:[{column:字段名,propetry:属性名,to_entity:对应实体类},...]</span>
  		</div>
  		<div>
	  	    <span>entityPackage:</span>
	  		<form:input path="entityPackage"  placeholder="输入 entityPackage"/>
	  		<form:checkbox path="makeEntity" value="makeEntity" />
  		</div>
  		<div>
	  	    <span>interfacePackage:</span>
	  		<form:input path="interfacePackage"  placeholder="输入 interfacePackage"/>
	  		<form:checkbox path="makeInterface" value="makeInterface" />
  		</div> 		
  		<div>
	  	    <span>serverPackage:</span>
	  		<form:input path="serverPackage"  placeholder="输入 serverPackage"/>
	  		<form:checkbox path="makeServer" value="makeServer" />
  		</div>
  		<div>
	  	    <span>controllerPackage:</span>
	  		<form:input path="controllerPackage"  placeholder="输入 controllerPackage"/>
	  		<form:checkbox path="makeController" value="makeController" />
  		</div>
  		<div>
	  	    <span>viewSubPath:</span>
	  		<form:input path="viewSubPath"  placeholder="输入 viewSubPath"/>
  		</div>
  		<div>
	  	    <span>parentSubUrl:</span>
	  		<form:input path="parentSubUrl"  placeholder="输入 parentSubUrl"/>
	  		<span>为空表示从根开始</span>
  		</div>
  		
  		<div>
	  	    <span>MakeEditView:</span>
	  		<form:checkbox path="makeEditView" value="makeEditView" />
  		</div>
  		<div>
	  	    <span>MakeListView:</span>
	  		<form:checkbox path="makeListView" value="makeListView" />
  		</div>
  		<div>
	  	    <span></span>
	  	    <span>${msg}</span>
  		</div>  		
  		<div>
	  	    <span></span>
	  		<input type="submit" value="提交生产" >
  		</div>
        
	</form:form>
	
	</div>
  </body>
</html>
