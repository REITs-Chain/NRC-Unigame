function initPaging(paging_id,page,totalPage,pageRows,listUrl){
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
	  
	  var url=listUrl+"?page=1";
	  var code="<li><a href=\""+url+"\">第一页</a></li>";
	  url=listUrl+"?page="+(page-1<=0?1:page-1);
	  code+="<li><a href=\""+url+"\">上一页</a></li>";
	  
	  for(var i=begin;i<=end;i++){
		  url=listUrl+"?page="+i;
		  var ac="";
		  if(i==page)
			  ac=" class=\"am-active\" ";
		  code+="<li"+ac+"><a href=\""+url+"\">"+i+"</a></li>";
	  }
	  
	  url=listUrl+"?page="+(page+1>totalPage?totalPage:page+1);
	  code+="<li><a href=\""+url+"\">下一页</a></li>";
	  var url=listUrl+"?page="+totalPage;
	  code+="<li><a href=\""+url+"\">最末页</a></li>";
	  
	  $("#"+paging_id).html(code);
 }