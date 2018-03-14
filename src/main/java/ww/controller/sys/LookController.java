package ww.controller.sys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import ww.common.DbModel;
import ww.common.FileUploadUtil;
import ww.common.SqlList;
import ww.common.SqlMap;
import ww.common.WwSystem;

@Controller
@RequestMapping(value="/Look")
public class LookController {
	
	@RequestMapping(value="/look")
	public ModelAndView look(HttpServletRequest request,HttpServletResponse response){		
		ModelAndView mv=new ModelAndView();
		
		String tableName=WwSystem.ToString(request.getParameter("tableName"));
		String valueField=WwSystem.ToString(request.getParameter("valueField"));
		String textField=WwSystem.ToString(request.getParameter("textField"));
		String showField=WwSystem.ToString(request.getParameter("showField"));
		int page=WwSystem.ToInt(request.getParameter("page"));//第几页
        int beginRow=WwSystem.ToInt(request.getParameter("beginRow"));//每页开始行
        int pageRows=WwSystem.ToInt(request.getParameter("pageRows"));//每页显示行数
        
        //防止sql注入
        tableName=DbModel.validSqlPark(tableName);
        valueField=DbModel.validSqlPark(valueField);
        textField=DbModel.validSqlPark(textField);
        showField=DbModel.validSqlPark(showField);
        
        if(page<=0)
            page=1;
        if(beginRow<=0)
            beginRow=0;
        if(pageRows<=0)
            pageRows=20;
		
        DbModel dao=new DbModel();
		
		SqlList fieldList=dao.getFields(tableName);	
		
		SqlMap valuef=null;
		if(valueField==null||valueField.isEmpty()){
			valuef=fieldList.get(0);
		}else{
			valuef=findField(valueField,fieldList);
		}
		SqlMap textf=null;
		if(textField==null||textField.isEmpty()){
			textf=fieldList.get(1);
		}
		else{
			textf=findField(textField,fieldList);
		}
		SqlMap showf=null;
		if(fieldList.size()>2){
			if(showField==null||showField.isEmpty()){
				showf=fieldList.get(2);
			}else{
				showf=findField(showField,fieldList);
			}
		}
		
		if(valuef==null||textf==null){
			mv.addObject("fieldList", null);
			mv.addObject("list", null);
			mv.addObject("tableName", tableName);		
	    	mv.addObject("page",page); //第几页
	    	mv.addObject("pageRows",pageRows); //每页显示行数
	    	mv.addObject("allRows",0); //查询总行数		
	    	mv.addObject("valueField和textField的字段名有问题！");
	    	
			mv.setViewName("sys/look");			
			return mv;
		}
		
		ArrayList<SqlMap> fields=new ArrayList<SqlMap>();
		genFieldTitle(valuef);
		genFieldTitle(textf);
		genFieldTitle(showf);
		fields.add(valuef);
		fields.add(textf);
		fields.add(showf);
		
		
		String fieldset_str=valuef.getString("fieldName")+","+textf.getString("fieldName");
		if(textf!=null){
			fieldset_str+=","+showf.getString("fieldName");
		}
		SqlList list=dao.table(tableName).limit(beginRow, pageRows).select(fieldset_str);
		long allRows=dao.table(tableName).count();
		
		mv.addObject("fieldList", fields);
		mv.addObject("list", list);
		mv.addObject("tableName", tableName);		
    	mv.addObject("page",page); //第几页
    	mv.addObject("pageRows",pageRows); //每页显示行数
    	mv.addObject("allRows",allRows); //查询总行数	
    	
		mv.setViewName("sys/look");		
		return mv;
	}
	
	/*****非接口****************/
	private String genFieldTitle(SqlMap sm){
		if(sm==null)
			return "";
		String title=sm.getString("fieldNote");
		if(title==null||title.isEmpty()){
			title=sm.getString("fieldName");
		}
		sm.put("fieldTitle", title);
		return title;
	}
	private SqlMap findField(String fieldName,SqlList list){
		for(int i=0;i<list.size();i++){			
			if(list.get(i).getString("fieldName").equalsIgnoreCase(fieldName)){
				return list.get(i);
			}		
		}
		return null;
	}
	
}
