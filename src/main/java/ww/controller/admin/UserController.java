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
import org.springframework.web.servlet.ModelAndView;

import ww.common.WwSystem;
import model.User;
import ww.service.admin.UserService;

@Controller
@RequestMapping(value="/admin/User")
public class UserController extends ww.controller.BaseController {
	
	@Autowired
	private UserService service;

	
	
	
	@RequestMapping(value="/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){		
		ModelAndView mv=new ModelAndView();
		
		//test
		//String num=SmsUtil.getNewVerifyNum();
		//boolean b=SmsUtil.sendVerifyNum_Reg("18787008828", num);
		
		//test
		//IDCard idc=new IDCard();
		//int res=idc.idcard_verify("杨毓旺", "532424197702040315");
		
		int page=WwSystem.ToInt(request.getParameter("page"));//第几页
        int beginRow=WwSystem.ToInt(request.getParameter("beginRow"));//每页开始行
        int pageRows=WwSystem.ToInt(request.getParameter("pageRows"));//每页显示行数
        String query=WwSystem.ToString(request.getParameter("query"));//搜索条件
        
        if(page<=0)
            page=1;
        if(beginRow<=0)
            beginRow=0;
        if(pageRows<=0)
            pageRows=20;
        
        String where="";
        
        //请根据实际搜索条件更改代码
        if(!query.isEmpty()){
        	where=" where phoneNum='"+query+"' or realName='"+query+"'";
        }

        String parkSql=where + " limit "+beginRow+","+pageRows;
    	List<User> list=service.getList(parkSql);
    	int allRows=service.getCount(where);
    	
    	mv.addObject("list",list); //列表数据
    	mv.addObject("page",page); //第几页
    	mv.addObject("pageRows",pageRows); //每页显示行数
    	mv.addObject("allRows",allRows); //查询总行数	
    	mv.addObject("query",query); //查询总行数		
		
		mv.setViewName(getGroupUserName());
		return mv;
	}
	
	@RequestMapping(value="/edit")
	public ModelAndView edit(java.lang.Integer id,Integer state,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView(getEditViewName());
		User data=null;
		if(id!=null)			
			data=service.getById(id);
		if(data==null)
			data=new User();
		
		if(state==null)
			state=0;
		
		mv.addObject("state", state);	
		mv.addObject("data",data);		
		return mv;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(@Valid @ModelAttribute("data") User data,BindingResult result,
			HttpServletRequest request,HttpServletResponse response)throws Exception{		
		ModelAndView mv=null;		
		//如果有验证错误 返回到form页面
        if(result.hasErrors()){
        	mv= new ModelAndView(getEditViewName());
        	mv.addObject("data",data);
        	mv.addObject("error","验证错误！");
        	return mv;
        }
        
        User saveData=service.getById(data.getId());
		if(saveData==null){
			saveData=new User();
		}	
		//保存request提交的字段，其他字段为数据库已经存在的值
	    saveData.mergeProperty(data, request);	
				
		if(service.Save(saveData)){
			mv=new ModelAndView("redirect:/admin/User/list");
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
		User data=null;
		if(id!=null)
			data=service.getById(id);
		if(data==null)
			data=new User();
		
		mv.addObject("data",data);		
		return mv;
	}
	
	@RequestMapping(value="/confirm")
	public ModelAndView confirm(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView("admin/UserConfirm");
		User data=null;
		if(id!=null)
			data=service.getById(id);
		if(data==null)
			data=new User();
		
		mv.addObject("data",data);		
		return mv;
	}
	
	@RequestMapping(value="/delete")
	public ModelAndView delete(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView("forward:/admin/User/list");
		if(id==null){
			mv.addObject("msg","id无效！");
			mv.addObject("success","false");
			return mv;
		}
		if(service.Delete(id)){
			mv.addObject("msg","删除成功！");
			mv.addObject("success","true");
		}else{
			mv.addObject("msg","删除失败！");
			mv.addObject("success","false");
		}
		
		return mv;
	}
	
	@RequestMapping(value="/pass")
	public ModelAndView pass(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView();
		
		User user=service.getById(id);
		if(user==null){
			mv.addObject("error","异常：找不到用户！");
			mv.addObject("data",new User());
			mv.setViewName("redirect:/admin/User/UserConfirm");
			return mv;
		}
		
		user.setCertificationStatus(2);
		
		if(!service.Save(user)){
			mv.addObject("error","保存失败");
			mv.addObject("data",user);
			mv.setViewName("redirect:/admin/User/UserConfirm");
			return mv;
		}		
		
		mv.setViewName("redirect:/admin/User/list");
		return mv;
	}
	
	@RequestMapping(value="/deny")
	public ModelAndView deny(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView();
		
		User user=service.getById(id);
		if(user==null){
			mv.addObject("error","异常：找不到用户！");
			mv.addObject("data",new User());
			mv.setViewName("redirect:/admin/User/UserConfirm");
			return mv;
		}
		
		user.setCertificationStatus(0);
		
		if(!service.Save(user)){
			mv.addObject("error","保存失败");
			mv.addObject("data",user);
			mv.setViewName("redirect:/admin/User/UserConfirm");
			return mv;
		}		
		
		mv.setViewName("redirect:/admin/User/list");
		return mv;
	}
	
	private String getEditViewName(){
		return "admin/UserEdit";
	}
	
	private String getViewViewName(){
		return "admin/UserView";
	}
	
	private String getListViewName(){
		return "admin/UserList";
	}
	private String getGroupUserName(){
		return "admin/GroupJoinUser";
	}
	
}
