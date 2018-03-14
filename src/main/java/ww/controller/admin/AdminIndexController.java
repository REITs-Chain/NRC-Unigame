package ww.controller.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Admin;
import model.Function;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import ru.paradoxs.bitcoin.client.BitcoinClient;
import ru.paradoxs.bitcoin.client.TransactionInfo;
import ww.authen.LoginUser;
import ww.bitcoin.Config;
import ww.bitcoin.WwBitcoinClient;
import ww.common.ModelDAO;
import ww.common.SmsUtil_heng;
import ww.common.SqlMap;
import ww.common.WwLog;
import ww.common.WwSystem;
import ww.controller.BaseController;
import ww.dao.FunctionMapper;
import ww.idcard.IDCard;
import ww.redis.WwRedisDAO;
import ww.redis.WwRedisDAO2;


@Controller
@RequestMapping(value="/admin")
public class AdminIndexController extends BaseController {	

	@RequestMapping(value={"/","","/index"})
	public ModelAndView Index(HttpServletRequest request,HttpServletResponse response){		
		ModelAndView mv=new ModelAndView("admin/index");
		
		//test
		WwLog.getLogger(this).debug(" debug!");
		WwLog.getLogger(this).info(" ifno!");
		WwLog.getLogger(this).error(" error!");
		WwLog.getLogger(this).warn(" warn!");
		
		//test
		//SmsUtil_heng.sendVerifyNum_common("18787008828", "5629");
		
		return mv;
	}
	
	@RequestMapping(value="/test")
	public ModelAndView test(HttpServletRequest request,HttpServletResponse response){		
		ModelAndView mv=new ModelAndView("admin/test_ueditor");
		
		return mv;
	}
	
	@RequestMapping(value="/test2")
	@ResponseBody
	public String test2(HttpServletRequest request,HttpServletResponse response){
		
		//ModelAndView mv=new ModelAndView("admin/test_ueditor");
		
		String code=request.getParameter("code");
		
		return code;
	}
	
}
