$(function(){
	$("[ww-date]").datepicker();
	$("[ww-date]").inputmask("yyyy-mm-dd",{ "placeholder": "yyyy-mm-dd" });
	$("[ww-datetime]").datetimepicker({language: 'zh-CN',autoclose:true,format:'yyyy-mm-dd hh:ii:ss'});
	$("[ww-daterange]").daterangepicker();
	$("[ww-float]").inputmask("decimal");
	$("[ww-decimal]").inputmask("decimal");
	$("[ww-double]").inputmask("decimal");
	$("[ww-integer]").inputmask("integer");
	//$("[ww-email]").inputmask("");
	$("[ww-tel]").inputmask("9999-99999999");
	$("[ww-mobile]").inputmask("99999999999");
	$("[ww-ip]").inputmask("ip");
	
	$("[ww-datetime]").on("change",function(a,b,c){
		var v=$(this).val();
		//alert(v);
		if(!WW.CheckDateTime(v)){
			alert("不是有效的日期时间格式(有效格式：YYYY-MM-DD HH:MM:SS)");
			$(this).val('');
		}
	});
	
});