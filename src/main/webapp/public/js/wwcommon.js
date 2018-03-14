function initTablePaging(table_id,form_id,page,pageRows,allRows){
	  var showPageCount=10; //最多显示10个数字按钮
	  var right=4;//当页很多时右边保持空4个数据按钮
	  var begin=1;//显示起始页
	  var end=10; //显示结束页
	  var allPages=1;//总页数
	  if(page==undefined||page<=0)
		  page=1;
	  if(pageRows==undefined||pageRows<=0)
		  pageRows=20;
	  if(allRows>0){
		  allPages=allRows%pageRows>0?(allRows-allRows%pageRows)/pageRows+1:allRows/pageRows;		
		  if(allPages==1)
		      page=1;
		  if(page>allPages)
		      page=allPages;
		  
		  if(allPages<=showPageCount){
			  begin=1;
			  end=allPages;
		  }else if(page+right<=allPages){
			  end=page+right;
			  if(page+right<showPageCount){
				  end=showPageCount;
			  }		  
			  begin=end-10+1;
		  }else{
			  end=allPages;
			  begin=end-10+1;
		  }
	  }else{
		  allPages=1;
		  page=1;
		  begin=1;
		  end=1;
	  }
	  
	  $("#"+form_id+" input[name='page']").val(page);
	  $("#"+form_id+" input[name='pageRows']").val(pageRows);	  
	  
	  var dis="";
	  if(page<=1){
		  dis="class='disabled'";
	  }
	  var code="<ul class='pagination' style='margin-top:8px;'>";
	  var fun="table_paging('"+table_id+"','"+form_id+"',1)";
	  code+="<li "+dis+"><a href=\"javascript:"+fun+"\">第一页</a></li>";
	  var pp=(page-1<=0?1:page-1);
	  fun="table_paging('"+table_id+"','"+form_id+"',"+pp+")";
	  code+="<li "+dis+"><a href=\"javascript:"+fun+"\">上一页</a></li>";
	  
	  for(var i=begin;i<=end;i++){
		  var ac="";
		  if(i==page)
			  ac=" class=\"active\" ";
		  fun="table_paging('"+table_id+"','"+form_id+"',"+i+")";
		  code+="<li"+ac+"><a href=\"javascript:"+fun+"\">"+i+"</a></li>";
	  }
	  
	  dis="";
	  if(page>=allPages){
		  dis="class='disabled'";
	  }
	  pp=(page+1>allPages?allPages:page+1);
	  fun="table_paging('"+table_id+"','"+form_id+"',"+pp+")";
	  code+="<li "+dis+"><a href=\"javascript:"+fun+"\">下一页</a></li>";
	  pp=allPages;
	  fun="table_paging('"+table_id+"','"+form_id+"',"+pp+")";
	  code+="<li "+dis+"><a href=\"javascript:"+fun+"\">最末页</a></li>";
	  code+="</ul>";
	  
	  $("#"+table_id).after(code);
	  
	  var table=$("#"+table_id);
	//全选复选框
      table.find('.group-checkable').change(function () {
          var set = "#"+table_id+" .checkboxes";
          var checked = jQuery(this).is(":checked");
          jQuery(set).each(function () {
              if (checked) {
                  $(this).attr("checked", true);
                  $(this).parents('tr').addClass("active");
              } else {
                  $(this).attr("checked", false);
                  $(this).parents('tr').removeClass("active");
              }
          });
          jQuery.uniform.update(set);
      });
  	//选中行高亮
      table.on('change', 'tbody tr .checkboxes', function () {
          $(this).parents('tr').toggleClass("active");
      });
}
function table_paging(table_id,form_id,page){
	//设置当前页
	$("#"+form_id+" input[name='page']").val(page);
	//获取每页行数
	var pageRows=$("#"+form_id+" input[name='pageRows']").val();
	
	var beginRow=pageRows*(page-1);	
	
	$("#"+form_id+" input[name='beginRow']").val(beginRow);
	//alert($("#"+form_id+" input[name='beginRow']").val());
	$("#"+form_id).submit();	
}

//已过时
function initPaging(paging_id,page,totalPage,pageRows,listUrl,params){
	  var showPageCount=10; //最多显示10个数字按钮
	  var right=4;//当页很多时右边保持空4个数据按钮
	  var begin=1;
	  var end=10;
	  if(totalPage<=showPageCount){
		  begin=1;
		  end=totalPage;
	  }else if(page+right<=totalPage){
		  end=page+right;
		  if(page+right<showPageCount){
			  end=showPageCount;
		  }		  
		  begin=end-10+1;
	  }else{
		  end=totalPage;
		  begin=end-10+1;
	  }
	  
	  if(params!=undefined && params!=""){
		  listUrl+="?"+params+"&";
	  }else{
		  listUrl+="?";
	  }
	  
	  var url=listUrl+"page=1";
	  var code="<li><a href=\""+url+"\">第一页</a></li>";
	  url=listUrl+"page="+(page-1<=0?1:page-1);
	  code+="<li><a href=\""+url+"\">上一页</a></li>";
	  
	  for(var i=begin;i<=end;i++){
		  url=listUrl+"page="+i;
		  var ac="";
		  if(i==page)
			  ac=" class=\"active\" ";
		  code+="<li"+ac+"><a href=\""+url+"\">"+i+"</a></li>";
	  }
	  
	  url=listUrl+"page="+(page+1>totalPage?totalPage:page+1);
	  code+="<li><a href=\""+url+"\">下一页</a></li>";
	  var url=listUrl+"page="+totalPage;
	  code+="<li><a href=\""+url+"\">最末页</a></li>";
	  
	  $("#"+paging_id).html(code);
 }