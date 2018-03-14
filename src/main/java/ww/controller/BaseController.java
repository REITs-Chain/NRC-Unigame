package ww.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
public class BaseController extends MultiActionController {
	
	/*@InitBinder
	public void initBinder(WebDataBinder binder) {
	    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // HH:mm:ss
	    //dateFormat.setLenient(false);
	    //binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
	    binder.registerCustomEditor(Boolean.class, new CustomBooleanEditor(true));//true:允许输入空值，false:不能为空值
	}*/
	
}
