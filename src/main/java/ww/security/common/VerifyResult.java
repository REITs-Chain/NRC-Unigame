package ww.security.common;

/**
 * status： 0-验证成功  1-未登录(包括cookic) 2-没有权限 
 */
public class VerifyResult {

	/**
	 * 0-验证成功  1-未登录(包括cookic) 2-没有权限 
	 */
	public int status=1;
	
	/**
	 * 当前登录人
	 */
	public String loginUser=null;
	
	public VerifyResult(){
		this.status=1;
		this.loginUser=null;
	}
	
	public VerifyResult(int status,String loginUser){
		this.status=status;
		this.loginUser=loginUser;
	}
	
	public static VerifyResult Empty(){
		return new VerifyResult();
	}

}
