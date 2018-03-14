package ww.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ww.security.ConfigRule.RuleItem;
import ww.security.common.VerifyFilter;
import ww.security.common.VerifyResult;

public class AuthcVerifyFilter implements VerifyFilter {

	public AuthcVerifyFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public VerifyResult doVerify(String filterName, String URI, RuleItem ri, HttpServletRequest request,
			HttpServletResponse response,SecurityManager securityManager) {		
		
		//登录验证
		LoginVerifyFilter loginVer=new LoginVerifyFilter();
		VerifyResult login_vr=loginVer.doVerify(filterName, URI, ri, request, response,securityManager);
		if(login_vr.status!=0){
			VerifyResult vr=new VerifyResult();
			vr.status=1;
			vr.loginUser=null;
			return vr;
		}
		
		//权限验证	
		DataRealm dr=securityManager.getDataRealm();
		List<String> perms=dr.getPermssions(login_vr.loginUser);
		
		for(int i=0;i<perms.size();i++){
			if(matches(perms.get(i),URI)){
				VerifyResult vr=new VerifyResult();
				vr.status=0;
				vr.loginUser=login_vr.loginUser;
				return vr;
			}
		}
		
		VerifyResult vr2=new VerifyResult();
		vr2.status=2;
		vr2.loginUser=login_vr.loginUser;
		return vr2;
	}
	
	private boolean matches(String regex,String str){
		if(str.equals(regex))
			return true;
		
		regex=regex.replaceAll("\\*","[\\\\w_/]*");
		
		boolean flag=str.matches(regex);
		return flag;
	}

}
