package model;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;


public class User extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_user";
	public static final String _keyName = "id";

	// Fields
	//常用验证方式
	/*
	@Max(value=100,message="不能大于100")
	@Min(value=0,message="不能小于0")
	@NotNull(message="不能为null")
	@NotBlank(message="不能为空！")
	@NotEmpty(message="不能为空！")
	@Past(message="不能大于今天！")
	@Future(message="不能小于今天！")
	@Email(message="电子邮箱地址格式 不正确！")
	@Range(min=0,max=100,message="只能输入0至100之间的数字！")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Pattern(regexp="",message="输入的格式不正确！")*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.Integer id;
	private java.lang.Integer type;
	private java.lang.String areaNum;
	private java.lang.String phoneNum;
	private java.lang.String email;
	private java.lang.String password;
	private java.lang.String password_his;
	private java.lang.String nickName;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date regTime;
	private java.lang.String realName;
	private java.lang.Integer gender;
	private java.lang.String idType;
	private java.lang.String idNum;
	private java.lang.String idPhoto1;
	private java.lang.String idPhoto2;
	private java.lang.String photo;
	private java.lang.String avatar;
	private java.lang.Integer status;
	private java.lang.Integer certificationStatus;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date certificationTime;
	private java.lang.Boolean loginLock;
	private java.lang.Integer loginTimes;
	private java.lang.Integer walletLock;
	private java.lang.String qq;
	private java.lang.String weixin;
	private java.lang.Integer bankCardVerifStatus;
	private java.lang.String inviteCode;
	private java.lang.String parentInviteCode;
	private java.lang.String walletAddress;
	private java.lang.String walletAddress_his;
	private java.lang.String aesKey;
	private java.lang.String aesIv;
	private java.lang.String defaultLanguage;
	private java.lang.Integer newUserReward;
	private java.lang.String walletBackup;
	private java.lang.String transPassword;
	private java.lang.String rongCloudToken;
	private java.util.List<model.Message> messages;
	private java.util.List<model.OrganInfo> organInfos;
	private java.util.List<model.ReportLoss> reportLosses;
	private java.util.List<model.UserGroup> groups;


	// Constructors

	/** default constructor */
	public User() {
	}

	public java.lang.Integer getId() {
		return this.id;
	}
	public void setId(java.lang.Integer id) {
		this.id=id;
	}
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setType(java.lang.Integer type) {
		this.type=type;
	}
	public java.lang.String getAreaNum() {
		return this.areaNum;
	}
	public void setAreaNum(java.lang.String areaNum) {
		this.areaNum=areaNum;
	}
	public java.lang.String getPhoneNum() {
		return this.phoneNum;
	}
	public void setPhoneNum(java.lang.String phoneNum) {
		this.phoneNum=phoneNum;
	}
	public java.lang.String getEmail() {
		return this.email;
	}
	public void setEmail(java.lang.String email) {
		this.email=email;
	}
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setPassword(java.lang.String password) {
		this.password=password;
	}
	public java.lang.String getPassword_his() {
		return this.password_his;
	}
	public void setPassword_his(java.lang.String password_his) {
		this.password_his=password_his;
	}
	public java.lang.String getNickName() {
		return this.nickName;
	}
	public void setNickName(java.lang.String nickName) {
		this.nickName=nickName;
	}
	public java.util.Date getRegTime() {
		return this.regTime;
	}
	public void setRegTime(java.util.Date regTime) {
		this.regTime=regTime;
	}
	public java.lang.String getRealName() {
		return this.realName;
	}
	public void setRealName(java.lang.String realName) {
		this.realName=realName;
	}
	public java.lang.Integer getGender() {
		return this.gender;
	}
	public void setGender(java.lang.Integer gender) {
		this.gender=gender;
	}
	public java.lang.String getIdType() {
		return this.idType;
	}
	public void setIdType(java.lang.String idType) {
		this.idType=idType;
	}
	public java.lang.String getIdNum() {
		return this.idNum;
	}
	public void setIdNum(java.lang.String idNum) {
		this.idNum=idNum;
	}
	public java.lang.String getIdPhoto1() {
		return this.idPhoto1;
	}
	public void setIdPhoto1(java.lang.String idPhoto1) {
		this.idPhoto1=idPhoto1;
	}
	public java.lang.String getIdPhoto2() {
		return this.idPhoto2;
	}
	public void setIdPhoto2(java.lang.String idPhoto2) {
		this.idPhoto2=idPhoto2;
	}
	public java.lang.String getPhoto() {
		return this.photo;
	}
	public void setPhoto(java.lang.String photo) {
		this.photo=photo;
	}
	public java.lang.String getAvatar() {
		return this.avatar;
	}
	public void setAvatar(java.lang.String avatar) {
		this.avatar=avatar;
	}
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status=status;
	}
	public java.lang.Integer getCertificationStatus() {
		return this.certificationStatus;
	}
	public void setCertificationStatus(java.lang.Integer certificationStatus) {
		this.certificationStatus=certificationStatus;
	}
	public java.util.Date getCertificationTime() {
		return this.certificationTime;
	}
	public void setCertificationTime(java.util.Date certificationTime) {
		this.certificationTime=certificationTime;
	}
	public java.lang.Boolean getLoginLock() {
		return this.loginLock;
	}
	public void setLoginLock(java.lang.Boolean loginLock) {
		this.loginLock=loginLock;
	}
	public java.lang.Integer getLoginTimes() {
		return this.loginTimes;
	}
	public void setLoginTimes(java.lang.Integer loginTimes) {
		this.loginTimes=loginTimes;
	}
	public java.lang.Integer getWalletLock() {
		return this.walletLock;
	}
	public void setWalletLock(java.lang.Integer walletLock) {
		this.walletLock=walletLock;
	}
	public java.lang.String getQq() {
		return this.qq;
	}
	public void setQq(java.lang.String qq) {
		this.qq=qq;
	}
	public java.lang.String getWeixin() {
		return this.weixin;
	}
	public void setWeixin(java.lang.String weixin) {
		this.weixin=weixin;
	}
	public java.lang.Integer getBankCardVerifStatus() {
		return this.bankCardVerifStatus;
	}
	public void setBankCardVerifStatus(java.lang.Integer bankCardVerifStatus) {
		this.bankCardVerifStatus=bankCardVerifStatus;
	}
	public java.lang.String getInviteCode() {
		return this.inviteCode;
	}
	public void setInviteCode(java.lang.String inviteCode) {
		this.inviteCode=inviteCode;
	}
	public java.lang.String getParentInviteCode() {
		return this.parentInviteCode;
	}
	public void setParentInviteCode(java.lang.String parentInviteCode) {
		this.parentInviteCode=parentInviteCode;
	}
	public java.lang.String getWalletAddress() {
		return this.walletAddress;
	}
	public void setWalletAddress(java.lang.String walletAddress) {
		this.walletAddress=walletAddress;
	}
	public java.lang.String getWalletAddress_his() {
		return this.walletAddress_his;
	}
	public void setWalletAddress_his(java.lang.String walletAddress_his) {
		this.walletAddress_his=walletAddress_his;
	}
	public java.lang.String getAesKey() {
		return this.aesKey;
	}
	public void setAesKey(java.lang.String aesKey) {
		this.aesKey=aesKey;
	}
	public java.lang.String getAesIv() {
		return this.aesIv;
	}
	public void setAesIv(java.lang.String aesIv) {
		this.aesIv=aesIv;
	}
	public java.lang.String getDefaultLanguage() {
		return this.defaultLanguage;
	}
	public void setDefaultLanguage(java.lang.String defaultLanguage) {
		this.defaultLanguage=defaultLanguage;
	}
	public java.lang.Integer getNewUserReward() {
		return this.newUserReward;
	}
	public void setNewUserReward(java.lang.Integer newUserReward) {
		this.newUserReward=newUserReward;
	}
	public java.lang.String getWalletBackup() {
		return this.walletBackup;
	}
	public void setWalletBackup(java.lang.String walletBackup) {
		this.walletBackup=walletBackup;
	}
	public java.lang.String getTransPassword() {
		return this.transPassword;
	}
	public void setTransPassword(java.lang.String transPassword) {
		this.transPassword=transPassword;
	}
	public java.lang.String getRongCloudToken() {
		return this.rongCloudToken;
	}
	public void setRongCloudToken(java.lang.String rongCloudToken) {
		this.rongCloudToken=rongCloudToken;
	}
	public java.util.List<model.Message> getMessages() {
		return this.messages;
	}
	public void setMessages(java.util.List<model.Message> value) {
		this.messages=value;
	}
	public java.util.List<model.OrganInfo> getOrganInfos() {
		return this.organInfos;
	}
	public void setOrganInfos(java.util.List<model.OrganInfo> value) {
		this.organInfos=value;
	}
	public java.util.List<model.ReportLoss> getReportLosses() {
		return this.reportLosses;
	}
	public void setReportLosses(java.util.List<model.ReportLoss> value) {
		this.reportLosses=value;
	}
	public java.util.List<model.UserGroup> getGroups() {
		return this.groups;
	}
	public void setGroups(java.util.List<model.UserGroup> value) {
		this.groups=value;
	}


}
