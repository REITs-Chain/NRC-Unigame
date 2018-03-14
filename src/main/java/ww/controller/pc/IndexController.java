package ww.controller.pc;

import java.io.File;
import java.io.IOException;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import ww.controller.BaseController;


@Controller
@RequestMapping(value="/")
public class IndexController extends BaseController {
	
	@RequestMapping(value={"/","","/index"})
	public ModelAndView Index(HttpServletRequest request,HttpServletResponse response){
		
		ModelAndView mv=new ModelAndView("pc/index");
		
		return mv;
	}
	
	@RequestMapping(value="/uploadImage")
	public ModelAndView UploadImage(HttpServletRequest request,HttpServletResponse response){
		
		ModelAndView mv=new ModelAndView("pc/uploadImage");
		
		return mv;
	}
	
	@RequestMapping(value="/doUploadImage")
	public ModelAndView DoUploadImage(HttpServletRequest request,HttpServletResponse response){		
		ModelAndView mv=new ModelAndView("pc/uploadImage");
		String filename="";
		try {
		    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		    // 得到上传的文件
		    MultipartFile mFile = multipartRequest.getFile("upload");
		    // 得到上传服务器的路径
		    String path = request.getSession().getServletContext().getRealPath("/public/file_list/test");
		    // 得到上传的文件的文件名
		    filename = mFile.getOriginalFilename();
		    path += "/" + filename;		  
		  
			FileCopyUtils.copy(mFile.getBytes(), new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		mv.addObject("imageName","public/file_list/test/" + filename);
		
		return mv;
	}
	
	@RequestMapping(value="/getJson")//,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getJson(HttpServletRequest request,HttpServletResponse response){
		
		return "{\"success\":true,\"msg\":\"hello!\"}";
	}
	
}
