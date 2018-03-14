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

import ww.common.FileUploadUtil;
import ww.common.WwLog;
import ww.common.WwSystem;
import ww.common.FileUploadUtil.UploadResult;
import model.Article;
import model.User;
import ww.service.admin.ArticleService;

@Controller
@RequestMapping(value="/admin/Article")
public class ArticleController extends ww.controller.BaseController {
	
	@Autowired
	private ArticleService service;
	
	@RequestMapping(value="/save2")//返回文件名
	public ModelAndView save2(@Valid @ModelAttribute("data") Article data,BindingResult result,
			HttpServletRequest request,HttpServletResponse response)throws Exception{		
		ModelAndView mv=null;		
		//如果有验证错误 返回到form页面
        if(result.hasErrors()){
        	mv= new ModelAndView(getEditViewName());
        	mv.addObject("data",data);
        	mv.addObject("error","验证错误！");
        	return mv;
        }
       
        Article saveData=service.getById(data.getId());
		if(saveData==null){
			saveData=new Article();
		}	
		//保存request提交的字段，其他字段为数据库已经存在的值
	    saveData.mergeProperty(data, request);	
				
		if(service.Save(saveData)){
			mv=new ModelAndView("redirect:/admin/Article/list");
		}else{			
			mv=new ModelAndView(getEditViewName());
			mv.addObject("error","保存失败！");
			mv.addObject("data",saveData);
		}
		
		return mv;
	}
	
	
	@RequestMapping(value="/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){		
		ModelAndView mv=new ModelAndView();
		
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
        /*
        //请根据实际搜索条件更改代码
        if(!query.isEmpty()){
        	where=" where fname='"+query+"'";
        }*/

        String parkSql=where + " limit "+beginRow+","+pageRows;
    	List<Article> list=service.getList(parkSql);
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
	public ModelAndView edit(java.lang.Integer id,Integer state,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView(getEditViewName());
		Article data=null;
		if(id!=null){			
			data=service.getById(id);
			//将图片存入session中
			request.getSession().setAttribute("photo", data.getPhoto());
			
			//获取文章内容
			String content = data.getContent();
			//截取请求的路径
	        StringBuffer requestURL = request.getRequestURL();
	        int indexOf = requestURL.indexOf("admin/");
	        String substring = requestURL.substring(0,indexOf);
	        //将图片路径替换成全路径
	        String replaceAll = content.replaceAll("/WDP//", substring);
	        String replaceAll2 = replaceAll.replaceAll("<img", "<img width=100px");
	        data.setContent(replaceAll2);
	        System.out.println("*******"+replaceAll);
		}
		if(data==null)
			data=new Article();
		
		if(state==null)
			state=0;
		
		mv.addObject("state", state);	
		mv.addObject("data",data);		
		return mv;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(@Valid @ModelAttribute("data") Article data,BindingResult result,
			HttpServletRequest request,HttpServletResponse response)throws Exception{		
		ModelAndView mv=null;		
		//如果有验证错误 返回到form页面
        if(result.hasErrors()){
        	mv= new ModelAndView(getEditViewName());
        	mv.addObject("data",data);
        	mv.addObject("error","验证错误！");
        	return mv;
        }
        
        Article saveData=service.getById(data.getId());
		if(saveData==null){
			saveData=new Article();
		}	
		//保存request提交的字段，其他字段为数据库已经存在的值
	    saveData.mergeProperty(data, request);	
				
		if(service.Save(saveData)){
			mv=new ModelAndView("redirect:/admin/Article/list");
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
		Article data=null;
		if(id!=null)
			data=service.getById(id);
		if(data==null)
			data=new Article();
			//获取文章内容
			String content = data.getContent();
			//截取请求的路径
	        StringBuffer requestURL = request.getRequestURL();
	        int indexOf = requestURL.indexOf("admin/");
	        String substring = requestURL.substring(0,indexOf);
	        //将图片路径替换成全路径
	        String replaceAll = content.replaceAll("/WDP//", substring);
	        String replaceAll2 = replaceAll.replaceAll("<img", "<img width=px");
	        data.setContent(replaceAll2);
	        //System.out.println("*******"+replaceAll);
		
		mv.addObject("data",data);		
		return mv;
	}
	
	@RequestMapping(value="/delete")
	public ModelAndView delete(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView("forward:/admin/Article/list");
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
	
	private String getEditViewName(){
		return "admin/ArticleEdit";
	}
	
	private String getViewViewName(){
		return "admin/ArticleView";
	}
	
	private String getListViewName(){
		return "admin/ArticleList";
	}
	
}
