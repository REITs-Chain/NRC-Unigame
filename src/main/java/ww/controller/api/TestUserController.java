package ww.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import ww.authen.ApiLoginContext;
import ww.authen.LoginUser;
import ww.common.DbModel;
import ww.common.ModelDAO;
import ww.common.SqlMap;
import ww.common.WwSystem;
import ww.controller.BaseController;

@CrossOrigin(origins = "*", maxAge = 3600)  //跨域post请求
@Controller
@RequestMapping(value="/api/testuser")
public class TestUserController extends BaseController {

	public TestUserController() {
		// TODO Auto-generated constructor stub
	}
	
	//采集内侧用户信息
	@ResponseBody
	@RequestMapping(value="/gettestuser",method=RequestMethod.POST)
	public JSONObject getTestUser(String token,String idNum,String realName,String phoneNum,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "已超时，请重新登录！");
			return result;
		}
		
		DbModel dao=new DbModel();
		SqlMap testUser=dao.table("t_testUser").where("userId=:userId")
		.bind("userId", luser.getUserid())
		.selectOne();
		
		if(testUser!=null){
			int status=testUser.getInt("status");
			String statusDesc="";
			if(status==0){
				statusDesc="未验证";
			}else if(status==1){
				statusDesc="验证通过";
			}else if(status==-1){
				statusDesc="验证失败 ，如有疑问请联系微信：secret835706";
			}else if(status==2){
				statusDesc="已处理";
			}else if(status==-2){
				statusDesc="处理有问题，请联系微信：secret835706";
			}
			
			testUser.put("statusDesc", statusDesc);
		}
		
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "成功");
		result.put("data", testUser);
		return result;
		
	}
	
	//采集内测用户信息
	@ResponseBody
	@RequestMapping(value="/settestuser",method=RequestMethod.POST)
	public JSONObject setTestUser(String token,String idNum,String realName,String phoneNum,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "已超时，请重新登录！");
			return result;
		}
		
		DbModel dao=new DbModel();
		
		long count=dao.table("t_testUser")
				.where("userId=:userId")
				.bind("userId", luser.getUserid())
				.count("userId");
		if(count>0){
			result.put("success", false);
			result.put("code",3);
			result.put("message", "你已经提交过，不能重复提交！");
			return result;
		}
		
		SqlMap testUser=new SqlMap();
		testUser.put("userId", luser.getUserid());
		testUser.put("realName", realName);
		testUser.put("idNum", idNum);
		testUser.put("phoneNum", phoneNum);
		testUser.put("createTime", WwSystem.now());
		testUser.put("status", 0); //状态(0-未验证 1-验证通过 2-已处理 -1-验证失败 -2-处理失败)
		
		dao.table("t_testUser").where("userId=:userId")
			.bind("userId", luser.getUserid())
			.save(testUser);
		
		if(!luser.getRealName().equals(realName)||
				!luser.getIdNum().equals(idNum)){
			
			dao.table("t_testUser")
				.where("userId=:userId")
				.bind("userId", luser.getUserid())
				.bind("status", -1)   //-1-验证失败
				.bind("note", "验证失败,实名或身份证号码与公测注册的信息不一致！")
				.update("status=:status,note=:note");
			
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "验证失败  如有疑问请联系微信：secret835706");
			return result;
		}
		
		
		SqlMap userOk=dao.table("t_testUserOk")
				.where("idNum=:idNum and realName=:realName")
				.bind("idNum", idNum)
				.bind("realName", realName)
				.selectOne();
		
		if(userOk==null){
			dao.table("t_testUser")
			.where("userId=:userId")
			.bind("userId", luser.getUserid())
			.bind("status", -1)   //-1-验证失败
			.bind("note", "验证失败,实名或身份证号码与内测时留下的信息不一致！")
			.update("status=:status,note=:note");
			
			result.put("success", false);
			result.put("code",2);
			result.put("message", "验证失败  如有疑问请联系微信：secret835706");
			return result;
		}
		
		dao.table("t_testUser")
		.where("userId=:userId")
		.bind("userId", luser.getUserid())
		.bind("status", 1)   //1-验证通过
		.update("status=:status");				
		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "验证通过，请耐心等待处理.");
		return result;
	}

}
