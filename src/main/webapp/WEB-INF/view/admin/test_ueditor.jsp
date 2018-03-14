<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=WwSystem.getRoot(request)%>">
    <meta charset="UTF-8">
    <title>WDP_平台</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="common/main_style.jsp"></jsp:include>    
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
					Test
					<small>测试UEditor</small>
				</h1>
			</section>
			<!-- /content Header -->
			<!-- Main content -->
			<section class="content">
			<form action="admin/test2" method="post" enctype="multipart/form-data">
				<!-- panel -->
              	<div class="box box-primary">
                	<!-- panel-header -->
                	<div class="box-header with-border">
                  		<h3 class="box-title">管理</h3>
                	</div>
                	<!-- /panel-header -->
                	<!-- panel-body -->
               		<div class="box-body">
               			<input type="text" id="dename" name="dename" value="">
               			<button type="button" class="btn btn-primary" onclick="OpenWin()">弹出窗口</button>
               			
	               		<!-- 百度编辑框
	               	    <script id="editor" type="text/plain" style="width:100%;height:300px;">sdfsdfsdfdsf</script> -->
	               	    <textarea id="code" name="code" style="width:100%;height:300px;">fsfsdfsdfsdf</textarea>
	               	    <!--end 百度编辑框 -->
                 		
               		</div>
               		<div class="box-footer">
                 		<button type="submit" class="btn btn-primary">提交</button>
               		</div>
                	<!-- /panel-body -->
              </div>
			  <!-- /panel -->
			</form>  
			</section>
			<!-- /Main content -->
		</div>
		<!-- /Content Wrapper -->
	</div>
	<!-- /wrapper -->	
	<jsp:include page="common/main_footer.jsp"></jsp:include>
	<jsp:include page="common/main_right.jsp"></jsp:include>      
	
	<jsp:include page="common/main_script.jsp"></jsp:include>
	<script type="text/javascript" charset="utf-8" src="public/ueditor1_4_3/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="public/ueditor1_4_3/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="public/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
	
	<script type="text/javascript">
	//实例化百度编辑器
     var ue = UE.getEditor('code',{
		//定制工具按钮        
		/*toolbars:[["fullscreen","source","undo","redo","bold","Italic","Underline","|",
		           "StrikeThrough","Horizontal","Date","FontFamily","FontSize","LineHeight","CustomStyle",        
		           "JustifyLeft", "JustifyRight", "JustifyCenter","RemoveFormat"]]*/ 
		//是否展示元素路径       
		elementPathEnabled : false       
		//是否计数        
		,wordCount:true
		//高度是否自动增长        
		,autoHeightEnabled:false
		,imageUrlPrefix:rootUrl   
	});
    
    function OpenWin(){
    	var win=WW.openWin("winid",rootUrl+"admin/AdminQuery/query","请选择",1100,700,[
 			{
 			    text: "确定1",
 			    click: function () {
 			        $(this).dialog("close");
 			    }
 			},
 			{
 			    text: "取消",
 			    click: function () {
 			        $(this).dialog("close");
 			    }
 			}
 		]);
		//alert(win);
		//WW.closeWin("winid");
    }
	$(function() {	
		
	});
	</script>
	
	   
  </body>
</html>


