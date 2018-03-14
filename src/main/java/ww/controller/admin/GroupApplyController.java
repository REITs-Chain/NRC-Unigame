package ww.controller.admin;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;

import ww.common.DbModel;
import ww.common.ListSearchState;
import ww.common.WwLog;
import ww.common.WwSystem;
import model.GroupApply;
import ww.service.admin.GroupApplyService;

@Controller
@RequestMapping(value="/admin/GroupApply")
public class GroupApplyController extends ww.controller.BaseController {
	
	@Autowired
	private GroupApplyService service;
	
	@RequestMapping(value="/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){		
		ModelAndView mv=new ModelAndView();
		
		ListSearchState searchState=new ListSearchState(request,"GroupApply");
		searchState.saveStates();//保存状态
		
		int page=WwSystem.ToInt(searchState.getParameterWithState("page"));//第几页
        int pageRows=WwSystem.ToInt(searchState.getParameterWithState("pageRows"));//每页显示行数
        String query=WwSystem.ToString(searchState.getParameterWithState("query"));//搜索条件
        
        query=DbModel.sqlParamString(query);//非常重要，防sql注入
        
        if(page<=0)
            page=1;
        if(pageRows<=0)
            pageRows=20;
            
        int beginRow=(page-1)*pageRows;
        
        String where="";
        /*
        //请根据实际搜索条件更改代码
        if(!query.isEmpty()){
        	where=" where fname='"+query+"'";
        }*/

        String parkSql=where + " limit "+beginRow+","+pageRows;
    	List<GroupApply> list=service.getList(parkSql);
    	int allRows=service.getCount(where);

    	mv.addObject("list",list); //列表数据
    	mv.addObject("page",page); //第几页
    	mv.addObject("pageRows",pageRows); //每页显示行数
    	mv.addObject("allRows",allRows); //查询总行数	
    	mv.addObject("query",query); //查询总行数		
		
		mv.setViewName(getListViewName());
		return mv;
	}
	
	@RequestMapping(value="/edit")
	public ModelAndView edit(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView(getEditViewName());
		GroupApply data=null;
		if(id!=null)			
			data=service.getById(id);
		if(data==null)
			data=new GroupApply();		

		mv.addObject("data",data);		
		return mv;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(@Valid @ModelAttribute("data") GroupApply data,BindingResult result,
			HttpServletRequest request,HttpServletResponse response)throws Exception{		
		ModelAndView mv=null;		
		//如果有验证错误 返回到form页面
        if(result.hasErrors()){
        	mv= new ModelAndView(getEditViewName());
        	mv.addObject("data",data);
        	mv.addObject("error","验证错误！");
        	return mv;
        }
        
        GroupApply saveData=service.getById(data.getId());
		if(saveData==null){
			saveData=new GroupApply();
		}	
		//保存request提交的字段，其他字段为数据库已经存在的值
	    saveData.mergeProperty(data, request);	
				
		if(service.Save(saveData)){
			mv=new ModelAndView("redirect:/admin/GroupApply/list");
		}else{			
			mv=new ModelAndView(getEditViewName());
			mv.addObject("error","保存失败！");
			mv.addObject("data",saveData);
		}
		
		return mv;
	}
	
	@RequestMapping(value="/view")
	public ModelAndView view(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView(getViewViewName());
		GroupApply data=null;
		if(id!=null)
			data=service.getById(id);
		if(data==null)
			data=new GroupApply();
		
		mv.addObject("data",data);		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/delete")
	public JSONObject delete(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){		
		JSONObject result=new JSONObject();
		
		if(id==null){
			result.put("code",1);
			result.put("message","id无效！");
			WwLog.getLogger(this).error("id==null无效");
			return result;
		}
		
		if(service.Delete(id)){
			result.put("code",0);
			result.put("message","删除成功。");
		}else{
			result.put("code",2);
			result.put("message","删除失败！");
			WwLog.getLogger(this).error("id=="+id+"删除失败!");
		}
		
		return result;
	}
	
	private String getEditViewName(){
		return "admin/GroupApplyEdit";
	}
	
	private String getViewViewName(){
		return "admin/GroupApplyView";
	}
	
	private String getListViewName(){
		return "admin/GroupApplyList";
	}
	
}
