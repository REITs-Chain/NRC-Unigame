package ww.controller.api;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import ww.authen.ApiLoginContext;
import ww.authen.LoginUser;
import ww.common.SqlMap;
import ww.common.WwSystem;
import ww.controller.BaseController;
import ww.service.admin.ArticleService;

@CrossOrigin(origins = "*", maxAge = 3600)  //跨域post请求
@Controller
@RequestMapping(value="/api/essay")
public class EssayController extends BaseController{

	@Autowired
	private ArticleService articleService;
	
	
	//查看文章列表
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JSONObject list(Integer beginRow,Integer pageRows,String token,
			HttpServletRequest request,
			HttpServletResponse response){
		
		JSONObject result=new JSONObject();
		
		LoginUser user=ApiLoginContext.getUser(token);
		if(user==null){
			result.put("success", false);	
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
        if(beginRow<=0)
            beginRow=0;
        if(pageRows<=0)
            pageRows=10;
        
        String rootUrl=WwSystem.getFullRoot(request);
	    String  fullPhoto=rootUrl+"public/file_list/images/";
        
		String sql="select id,title,brief,relaseTime,publisher,concat('"+fullPhoto+"',photo) as photo from t_article  limit "+beginRow+","+pageRows;
		List<SqlMap> list = articleService.selectSql(sql);
	    
		int page=beginRow/pageRows==0?beginRow/pageRows:beginRow/pageRows+1;
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "成功");
		result.put("page", page);
		result.put("data", list);
		return result;
	}
	
	//查看文章详情
	@ResponseBody
	@RequestMapping(value="/details",method=RequestMethod.POST)
	public JSONObject details(Integer id,String token,
			HttpServletRequest request,
			HttpServletResponse response){
		
		JSONObject result=new JSONObject();
		
		LoginUser user=ApiLoginContext.getUser(token);
		if(user==null){
			result.put("success", false);	
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
        if(id==null){
        	result.put("success", false);	
			result.put("code", 1);
			result.put("message", "请选择你要查看的文章！");
			return result;
        }
        
        Article article = articleService.getById(id);

        //获取文章内容
        String content = article.getContent();
    	//将图片路径替换成全路径
        String fullUrl=WwSystem.getFullRoot(request)+"public/file_list/";
        String replaceAll = content.replaceAll("public/file_list/", fullUrl);
        article.setContent(replaceAll);
       
        String rootUrl=WwSystem.getRoot(request);
        String  fullPhoto=rootUrl+"public/file_list/images/"+article.getPhoto();
        article.setPhoto(fullPhoto);
        
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "成功");
		result.put("data", article);
		return result;
	}
}
