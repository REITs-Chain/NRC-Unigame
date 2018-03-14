var WW={authe:"yangyuwang",v:1.0};

//使用bootstrapp实现窗口
WW.openWin = function (id, url, title, width, height) {
    if (width == undefined)
        width = "70%";
      
    if (height == undefined)
        height = "520px";
    var frame_name = 'iframe_' + id;

    try { $(".ww_dialog_tem_123").remove(); } catch (e) { }

    if (window.document.getElementById(id) == undefined || window.document.getElementById(id) == null) {
        var tpl = "<div id='" + id + "' class='modal fade ww_dialog_tem_123'  tabindex='-1' role='dialog' aria-hidden='true'>";
        tpl += "<div class='modal-dialog' style='width:" + width + ";'>";
        tpl += "    <div class='modal-content'>";
        tpl += "        <div class='modal-header'>";
        tpl += "            <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>";
        tpl += "            <h4 class='modal-title'>" + title + "</h4>";
        tpl += "        </div>";
        tpl += "        <div class='modal-body'>";
        tpl += "            <iframe id='" + frame_name + "' name='" + frame_name + "' width='100%' height='" + height + "' frameborder='0' src=\"" + url + "\"></iframe>";
        tpl += "        </div>";
        tpl += "        <div class='modal-footer'>";
        tpl += "            <button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>";
        tpl += "        </div>";
        tpl += "    </div>";
        tpl += "</div>";
        tpl += "</div>";

        $(tpl).appendTo($("body"));
    }

    var win=$("#"+id).modal();
    win.window = window.frames[frame_name];
    return win;
}

//使用jquery ui实现窗口
WW.openWin_jui = function (id,url, title, width, height,buttons) {
    try { $(".ww_dialog_tem_123").remove(); } catch (e) { }
    var frame_name='iframe_'+id;
    if (window.document.getElementById(id) == undefined||window.document.getElementById(id) == null) {
        $("<div id='" + id + "' class='ww_dialog_tem_123' title='" + title + "' ><iframe id='"+frame_name+"' name='"+frame_name+"' width='100%' height='100%' frameborder='0' src=\"" + url + "\"></iframe></div>").appendTo($("body"));
    }

    var win=$("#" + id).dialog({
        autoOpen: true,
        modal: true,
        width: width,
        height: height,
        buttons:buttons
        /*[
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
		]*/
    });
    win.window=window.frames[frame_name]; 
    
    //$("#" + id).dialog("open");
    
    return win;
}

//bootstrap
WW.closeWin = function (id) {
    $("#" + id).modal('hide');
}

//jquery ui
WW.closeWin_jui=function(id){
	$("#"+id).dialog("close");
}

WW.iframeWindow=function(id){
	var frame_name='iframe_'+id;
	return window.frames[frame_name];
}

WW.json=function(jsonStr) {
    return eval("(" + jsonStr + ")");
}

WW.toDateTime=function(str_datetime){
	str = str_datetime.replace(/-/g,"/");//一般得到的时间的格式都是：yyyy-MM-dd hh24:mi:ss，所以我就用了这个做例子，是/的格式，就不用replace了。  
	var v_date = new Date(str);//将字符串转化为时间  
	return v_date;
}

WW.toDate=function(str_date){
	str = str_date.replace(/-/g,"/");//一般得到的时间的格式都是：yyyy-MM-dd，所以我就用了这个做例子，是/的格式，就不用replace了。  
	var v_date = new Date(str);//将字符串转化为时间  
	return v_date;
}

WW.formatDateTime=function(dt) {
    var year = dt.getYear();
    var sYear = year < 1900 ? year + 1900 : year;
    var month = dt.getMonth() + 1;
    var sMonth = month < 10 ? "0" + month : "" + month;
    var date = dt.getDate();
    var sDay = date < 10 ? "0" + date : "" + date;
    var hour = dt.getHours();
    var sHour = hour < 10 ? "0" + hour : "" + hour;
    var minute = dt.getMinutes();
    var sMinute = minute < 10 ? "0" + minute : "" + minute;
    var second = dt.getSeconds();
    var sSecond = second < 10 ? "0" + second : "" + second;
    return sYear + "-" + sMonth + "-" + sDay + " " + sHour + ":" + sMinute + ":" + sSecond;
}
WW.formatDate=function(d) {
    var year = d.getYear();
    var sYear = year < 1900 ? year + 1900 : year;
    var month = d.getMonth() + 1;
    var sMonth = month < 10 ? "0" + month : "" + month;
    var date = d.getDate();
    var sDay = date < 10 ? "0" + date : "" + date;
    return sYear + "-" + sMonth + "-" + sDay;
}

WW.addDays=function(date, days) {
    var nd = new Date(date);
    nd = nd.valueOf();
    nd = nd + days * 24 * 60 * 60 * 1000;
    return new Date(nd);
}

WW.getCheckValue=function(checkbox){
	if(typeof checkbox == "string"){
		checkbox=$("#"+checkbox);
	}
	return checkbox.is(":checked");
}
WW.setCheckValue=function(checkbox,value){
	if(typeof checkbox == "string"){
		checkbox=$("#"+checkbox);
	}
	checkbox.attr("checked", value);
}
WW.getSelectValue=function(select){
	if(typeof select == "string"){
		select=$("#"+select);
	}
	return select.val();
}
WW.getSelectText=function(select){
	if(typeof select == "string"){
		select=$("#"+select);
	}
	return select.find("option:selected").text();
}
WW.setSelectValue=function(select,value){
	if(typeof select == "string"){
		select=$("#"+select);
	}
	select.val(value);
}

WW.addSelectOption=function(select,value,text){
	if(typeof select == "string"){
		select=$("#"+select);
	}
	select.append("<option value='"+value+"'>"+text+"</option>");
}
WW.clearSelect=function(select_id){
	if(typeof select == "string"){
		select=$("#"+select);
	}
	select.find("option").remove();
	//$("#"+select_id+" option").remove();
}

//获取点击元素所在行的td列表
//el_in_td在td中的点击的元素对象
WW.getCurRowTDs=function(el_in_td){
	var cur_td=$(el_in_td).parent();
    var tr=cur_td.parent();
    var tds=tr.children('td');
    return tds;
}

//日期有效性验证 
//格式为：YYYY-MM-DD或YYYY/MM/DD   
WW.IsValidDate=function(DateStr){
	str = str.replace(/-/g,"/");
	var date = new Date(str);
	if(date==null||date==NaN||date=='Invalid Date')
		return false;
	else
		return true;
	/*
  var sDate=DateStr.replace(/(^\s+|\s+$)/g,'');//去两边空格; 
  if(sDate==''){ 
      return true; 
  } 
  //如果格式满足YYYY-(/)MM-(/)DD或YYYY-(/)M-(/)DD或YYYY-(/)M-(/)D或YYYY-(/)MM-(/)D就替换为'' 
  //数据库中，合法日期可以是:YYYY-MM/DD(2003-3/21),数据库会自动转换为YYYY-MM-DD格式 
  var s=sDate.replace(/[\d]{4,4}[\-/]{1}[\d]{1,2}[\-/]{1}[\d]{1,2}/g,''); 
  if(s==''){//说明格式满足YYYY-MM-DD或YYYY-M-DD或YYYY-M-D或YYYY-MM-D 
      var t=new Date(sDate.replace(/\-/g,'/')); 
      var ar=sDate.split(/[-/:]/); 
      if(ar[0]!=t.getYear()||ar[1]!=t.getMonth()+1||ar[2]!=t.getDate()){//alert('错误的日期格式！格式为：YYYY-MM-DD或YYYY/MM/DD。注意闰年。'); 
          return false; 
      } 
  }else{//alert('错误的日期格式！格式为：YYYY-MM-DD或YYYY/MM/DD。注意闰年。'); 
      return false; 
  } 
  return true; */
}

//日期时间有效性检查 
//格式为：YYYY-MM-DD HH:MM:SS 
WW.CheckDateTime=function(str){ 
	//var str ='2012-08-12 23:13:15';
	str = str.replace(/-/g,"/");
	var date = new Date(str);
	if(date==null||date==NaN||date=='Invalid Date')
		return false;
	else
		return true;
  /*
  var reg=/^(\d+)-(\d{1,2})-(\d{1,2})(\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
  var r=str.match(reg);
  alert(reg);
  if(r==null) return false; 
  r[2]=r[2]-1; 
  var d= new Date(r[1],r[2],r[3],r[4],r[5],r[6]);  
  if(d.getFullYear()!=r[1]) return false; 
  if(d.getMonth()!=r[2]) return false; 
  if(d.getDate()!=r[3]) return false; 
  if(d.getHours()!=r[4]) return false; 
  if(d.getMinutes()!=r[5]) return false; 
  if(d.getSeconds()!=r[6]) return false; 
  return true; */
}

WW.post=function(url,data){
	try{
		var htmlobj=$.ajax({url:url,type:'POST',dataType:'json',data:data,async:false});	
		var json=WW.json(htmlobj.responseText);
		return json;
	}catch(e){
		return {success:false,message:e.message};
	}
}










