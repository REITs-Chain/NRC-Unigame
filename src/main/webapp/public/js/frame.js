//ajax 实现图片上传
WW.AjaxUploadImage=function(fileElementId,fieldId,maxWidth,savePath) {
	if(savePath==undefined||savePath==null||savePath==""){
		savePath="public/file_list/images/";
	}
	if(maxWidth==undefined){
		maxWidth=0;
	}
	//alert(id);
	$.ajaxFileUpload({
		url : rootUrl+"admin/uploadimage/upload",
		secureuri : false,
		type : "post",
	    data : {//向后台传数据
			fileElementId:fileElementId,
			savePath:savePath,
			maxWidth:maxWidth
		}, 
		fileElementId : fileElementId,
		dataType : "json",
		success : function(data,status) {
			if (data.success == true) {//上传成功
				$("#img_"+fieldId).attr("src",savePath + data.fileName);
				$("#"+fieldId).attr("value",data.fileName);
			}else{
				alert(data.message);
			}
		},
		error : function(s, xml, status, e) {
			alert('上传错误！');
		}
	});
}

//ajax 实现图片上传
WW.AjaxUploadImage_private=function(fileElementId,fieldId,savePath) {
	if(savePath==undefined||savePath==null||savePath==""){
		savePath="private/images/";//默认保存位置
	}
	
	//alert(id);
	$.ajaxFileUpload({
		url : rootUrl+"admin/uploadimage/upload_private",
		secureuri : false,
		type : "post",
	    data : {//向后台传数据
			fileElementId:fileElementId,
			savePath:savePath
		}, 
		fileElementId : fileElementId,
		dataType : "json",
		success : function(data,status) {
			if (data.success == true) {//上传成功
				var imgurl="admin/uploadimage/getimage?fileName="+data.fileName+"&savePath="+savePath;
				$("#img_"+fieldId).attr("src",imgurl);
				$("#"+fieldId).attr("value",data.fileName);
			}else{
				alert(data.message);
			}
		},
		error : function(s, xml, status, e) {
			alert('上传错误！');
		}
	});
}

//实例化百度编辑器
WW.initUEditor=function(el_id){
	var ue = UE.getEditor(el_id,{
		//定制工具按钮        
		/*toolbars:[["fullscreen","source","undo","redo","bold","Italic","Underline","|",
		           "StrikeThrough","Horizontal","Date","FontFamily","FontSize","LineHeight","CustomStyle",        
		           "JustifyLeft", "JustifyRight", "JustifyCenter","RemoveFormat"]]*/ 
		toolbars: [[
                      'fullscreen', 'source', '|', 'undo', 'redo', '|',
                      'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                      'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                      'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                      'directionalityltr', 'directionalityrtl', 'indent', '|',
                      'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                      'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                      'simpleupload', 'insertimage', 'emotion', 'template', 'background', '|',
                      'horizontal', 'date', 'time', 'spechars','|',
                      'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols'
                      
                  ]]
		//是否展示元素路径       
		,elementPathEnabled : false       
		//是否计数        
		,wordCount:true
		//高度是否自动增长        
		,autoHeightEnabled:false 
	});
	return ue;
}

WW.look=function(url,to_cmdId){
	var win=WW.openWin("look_121",url, "关联查询", "70%","500px",[
		{
		    text: "确定",
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
	
	win.window.return_fun=function(data){
		$("#"+to_cmdId).val(data[0]);    			
		$("#txt_"+to_cmdId).val(data[1]);
		WW.closeWin('look_121');
	}
	
}