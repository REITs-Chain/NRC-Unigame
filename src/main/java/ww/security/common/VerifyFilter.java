package ww.security.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ww.security.ConfigRule;
import ww.security.SecurityManager;
import ww.security.ConfigRule.RuleItem;
import ww.security.common.VerifyResult;

public interface VerifyFilter {
	
	public VerifyResult doVerify(String filterName,String URI,RuleItem ri,HttpServletRequest request, HttpServletResponse response,SecurityManager securityManager);

}
