package ww.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Rawasset;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import ww.authen.ApiLoginContext;
import ww.authen.LoginUser;
import ww.common.ModelDAO;
import ww.common.SqlList;
import ww.controller.BaseController;
import ww.service.admin.RawassetService;
/**
 * 我的
 * @author Administrator
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)  //跨域post请求
@Controller
@RequestMapping(value="/api/datum")
public class DatumController extends BaseController{
	
	//获取部分个人资料
	@ResponseBody
	@RequestMapping(value="/getPartMyData",method=RequestMethod.POST)
	public JSONObject getPartMyData(String token,
			HttpServletRequest request,
			HttpServletResponse response){
		JSONObject result=new JSONObject();
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);	
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		ModelDAO dao=new ModelDAO();
		SqlList select = dao.M("t_user").where(" id="+luser.getUserid()).select(" avatar,nickName ");
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");
		result.put("data", select);
		return result;
	}
	
	//获取个人资料
	@ResponseBody
	@RequestMapping(value="/getMyData",method=RequestMethod.POST)
	public JSONObject getMyData(String token,
			HttpServletRequest request,
			HttpServletResponse response){
		JSONObject result=new JSONObject();
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);	
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		String sql="select qq,nickName,avatar,weixin,gender from t_user where phoneNum="+luser.getUsername();
		SqlList query = dao.query(sql);
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");
		result.put("data", query);
		return result;
	}
	//编辑个人资料
	@ResponseBody
	@RequestMapping(value="/editMyData",method=RequestMethod.POST)
	public JSONObject editMyData(User user,String token,
			HttpServletRequest request,
			HttpServletResponse response){
		JSONObject result=new JSONObject();
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);	
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		ModelDAO dao=new ModelDAO();
		int update = dao.M(" t_user ").where(" phoneNum= "+luser.getUsername()).update(
											" qq="+user.getQq()+
											", nickName="+user.getNickName()+
											", avatar="+user.getAvatar()+
											", gender="+user.getGender()+
											", weixin="+user.getWeixin());
		if (update>0) {
			result.put("success", true);	
			result.put("code", 0);
			result.put("message", "编辑成功");
		}else{
			result.put("success", false);	
			result.put("code", 1);
			result.put("message", "编辑失败");
		}
		return result;
	}
}
