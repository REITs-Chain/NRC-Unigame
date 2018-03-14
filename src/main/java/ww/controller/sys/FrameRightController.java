package ww.controller.sys;

import java.io.UnsupportedEncodingException;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import model.Admin;
import ww.authen.RightValid;
import ww.common.ModelDAO;
import ww.common.SqlList;
import ww.common.SqlMap;
import ww.common.WwSystem;
import ww.service.admin.AdminService;

@Controller
@RequestMapping(value="/admin/FrameRight")
public class FrameRightController extends ww.controller.BaseController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/adminRight")
	public ModelAndView adminRight(Integer adminId,HttpServletRequest request,HttpServletResponse response){		
		ModelAndView mv=new ModelAndView();
		
		Admin curAdmin=(Admin)request.getSession().getAttribute("admin");		
		if(!RightValid.Valid(curAdmin, "FrameRight", "edit")){
			mv.setViewName("redirect:rightError");//forward
			mv.addObject("message", "你没有\"权限配置\"权限");
			return mv;
		}
		
		Admin admin =adminService.getById(adminId);
		
		mv.setViewName("sys/adminRight");
		mv.addObject("admin", admin);
		return mv;
	}
	
	@RequestMapping(value="/getNodes")
	@ResponseBody
	public JSONArray getNodes(String id,Integer adminId,HttpServletRequest request,HttpServletResponse response){
		adminId=WwSystem.ToInt(adminId);
		JSONArray data=new JSONArray();
		ModelDAO dao=new ModelDAO();
		if(id.equals("#")){
			String type="frame";
			SqlList frames=dao.M("w_frame").select();
			for(int i=0;i<frames.size();i++){
				SqlMap frame=frames.get(i);
				JSONObject json=new JSONObject();
				json.put("id", type+"@"+frame.get("fname"));
				json.put("text", frame.get("ftitle"));
				json.put("icon", "fa fa-folder icon-lg");
				json.put("children", true);
				json.put("type", "root");
				JSONObject state=new JSONObject();
				//state.put("selected", true);
				state.put("opened", true);
				json.put("state",state);
				data.add(json);
			}
		}else{
			String[] strs=id.split("@");
			String frameType=strs[0];
			String frameName=strs[1];
			
			String type="right";
			SqlList rights=dao.M("w_right").select();			
			for(int i=0;i<rights.size();i++){
				SqlMap right=rights.get(i);
				String rightName=WwSystem.ToString(right.get("fname"));
				JSONObject json=new JSONObject();
				json.put("id", type+"@"+frameName+"@"+rightName);
				json.put("text", right.get("ftitle"));
				json.put("icon", "fa fa-file fa-large icon-state-default"); 
				json.put("children", false);
				//json.put("type", "root");
				
				JSONObject state=new JSONObject();
				if(isRight(dao,adminId,frameName,rightName)){
					state.put("selected", true);
				}else{
					state.put("selected", false);
				}
				json.put("state",state);
				
				data.add(json);
			}
		}
		

		return data;
	}
	//判断是否有权限
	private boolean isRight(ModelDAO dao,int adminId,String frameName,String right){
		SqlList list=dao.query("select count(*) as count from w_admin_right t2 where t2.fadminid="+adminId+" and t2.fframename='"+frameName+"' and t2.fright='"+right+"'");
		SqlMap data=list.get(0);
		if(data.getInt("count")>0)
			return true;
		else
			return false;
	}
	
	//设置权限
	@RequestMapping(value="/setRight")
	@ResponseBody
	public JSONObject setRight(String nodeId,Boolean checked,Integer adminId,HttpServletRequest request,HttpServletResponse response){
		adminId=WwSystem.ToInt(adminId);
		String[] strs=nodeId.split("@");
		String type=strs[0];
		String frame=strs[1];
		String right="";
		
		ModelDAO dao=new ModelDAO();
		if(type.equalsIgnoreCase("right")){
			right=strs[2];
			SqlMap data=new SqlMap();
			data.put("fadminid", adminId);
			data.put("fframename", frame);
			data.put("fright", right);
			String ttt=data.toSqlString("and");
			if(checked)
				dao.M("w_admin_right").where(data.toSqlString("and")).save(data);
			else
				dao.M("w_admin_right").where(data.toSqlString("and")).Delete();
		}else{
			SqlMap data=new SqlMap();
			data.put("fadminid", adminId);
			data.put("fframename", frame);
			if(!checked){
				dao.M("w_admin_right").where("fadminid="+adminId+" and fframename='"+frame+"'").Delete();
			}else{
				String sql="insert into w_admin_right(fadminid,fframename,fright)";
	            sql+="      select "+adminId+" as fadminid, '"+frame+"' as fframename, fname as fright from w_right";
	            dao.exec(sql);
			}
		}	
		
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("message", "授权成功！");
		return json;
	}
	
	@RequestMapping(value="/rightError")
	public ModelAndView rightError(String message,HttpServletRequest request,HttpServletResponse response){		
		ModelAndView mv=new ModelAndView();
		String msg=WwSystem.ToString(message);
		
		mv.setViewName("sys/rightError");
		mv.addObject("message", msg);
		return mv;
	}
	
	/*
	@RequestMapping(value="/edit")
	public ModelAndView edit(java.lang.Integer id,Integer state,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView(getEditViewName());
		Admin data=null;
		if(id!=null)			
			data=server.getById(id);
		if(data==null)
			data=new Admin();
		
		if(state==null)
			state=0;
		
		mv.addObject("state", state);	
		mv.addObject("data",data);		
		return mv;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(@Valid @ModelAttribute("data") Admin data,BindingResult result,
			HttpServletRequest request,HttpServletResponse response)throws Exception{		
		ModelAndView mv=null;		
		//如果有验证错误 返回到form页面
        if(result.hasErrors()){
        	mv= new ModelAndView(getEditViewName());
        	mv.addObject("data",data);
        	return mv;
        }
        
        Admin saveData=server.getById(data.getId());
		if(saveData==null){
			saveData=new Admin();
		}	
		//保存request提交的字段，其他字段为数据库已经存在的值
	    saveData.mergeProperty(data, request);
				
		if(server.Save(saveData)){
			mv=new ModelAndView("forward:/admin/Admin/list");
		}else{			
			mv=new ModelAndView(getEditViewName());
			mv.addObject("data",saveData);
		}
		
		return mv;
	}
	
	@RequestMapping(value="/delete")
	public ModelAndView delete(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView("forward:/admin/Admin/list");
		if(id==null){
			mv.addObject("msg","id无效！");
			mv.addObject("success","false");
			return mv;
		}
		if(server.Delete(id)){
			mv.addObject("msg","删除成功！");
			mv.addObject("success","true");
		}else{
			mv.addObject("msg","删除失败！");
			mv.addObject("success","false");
		}
		
		return mv;
	}
	
	private String getEditViewName(){
		return "admin/AdminEdit";
	}
	
	private String getListViewName(){
		return "admin/AdminList";
	}
	*/
	
}
