package ww.authen;

import java.io.Serializable;

public class LoginUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4678277158713299355L;
	
	private int userid=0;
	private int type=0; //0-普通用户  1-基金公司  2-审核机构
	private String username="";
	private String password="";
	private String realName="";//实名
	private int loginTimes=0;
	private boolean lock=false;
	//private String validateCode="";//图片验证码
	private String nickName=""; //昵称
	private String idNum="";//身份号码
	private String avatar="";//头像
	private int certificationStatus=0; //:[{value:0,text:'未认证',value:1,text:'认证中',value:2,text:'已认证'}]
	private int bankCardVerifStatus=0;//:[{value:0,text:'未认证'},{value:1,text:'已认证'}]
	private String walletAddress=""; //钱包地址
	private String aesKey=""; //加密key
	private String aesIv="";//加密iv
	private String defaultLanguage="zh";
	private String transPassword="";
	private String areaNum="";//区号  默认+86
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	/**
//	 * 图片验证码
//	 * @return
//	 */
//	public String getValidateCode() {
//		return validateCode;
//	}
//	/**
//	 * 图片验证码
//	 * @param validateCode
//	 */
//	public void setValidateCode(String validateCode) {
//		this.validateCode = validateCode;
//	}
	public int getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}
	public boolean isLock() {
		return lock;
	}
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public int getCertificationStatus() {
		return certificationStatus;
	}
	public void setCertificationStatus(int certificationStatus) {
		this.certificationStatus = certificationStatus;
	}
	public int getBankCardVerifStatus() {
		return bankCardVerifStatus;
	}
	public void setBankCardVerifStatus(int bankCardVerifStatus) {
		this.bankCardVerifStatus = bankCardVerifStatus;
	}
	public String getWalletAddress() {
		return walletAddress;
	}
	public void setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {		
		this.type = type;
	}
	public String getAesKey() {
		return aesKey;
	}
	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}
	public String getAesIv() {
		return aesIv;
	}
	public void setAesIv(String aesIv) {
		this.aesIv = aesIv;
	}
	public String getDefaultLanguage() {
		return defaultLanguage;
	}
	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}
	public String getTransPassword() {
		return transPassword;
	}
	public void setTransPassword(String transPassword) {
		this.transPassword = transPassword;
	}
	public String getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}
	
	
	
}
