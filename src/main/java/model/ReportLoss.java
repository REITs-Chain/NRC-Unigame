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


public class ReportLoss extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_reportloss";
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
	private java.lang.Integer userId;
	private java.lang.String walletAddressOld;
	private java.lang.String walletAddressNew;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date reportTime;
	private java.lang.Integer status;
	private java.lang.Integer checkAdminId;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date checkTime;
	private java.lang.Integer lossAdminId;
	private java.lang.String toWalletAddress;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date lossTime;
	private java.lang.String lossError;
	private java.lang.String note;
	private model.User user;


	// Constructors

	/** default constructor */
	public ReportLoss() {
	}

	public java.lang.Integer getId() {
		return this.id;
	}
	public void setId(java.lang.Integer id) {
		this.id=id;
	}
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setUserId(java.lang.Integer userId) {
		this.userId=userId;
	}
	public java.lang.String getWalletAddressOld() {
		return this.walletAddressOld;
	}
	public void setWalletAddressOld(java.lang.String walletAddressOld) {
		this.walletAddressOld=walletAddressOld;
	}
	public java.lang.String getWalletAddressNew() {
		return this.walletAddressNew;
	}
	public void setWalletAddressNew(java.lang.String walletAddressNew) {
		this.walletAddressNew=walletAddressNew;
	}
	public java.util.Date getReportTime() {
		return this.reportTime;
	}
	public void setReportTime(java.util.Date reportTime) {
		this.reportTime=reportTime;
	}
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status=status;
	}
	public java.lang.Integer getCheckAdminId() {
		return this.checkAdminId;
	}
	public void setCheckAdminId(java.lang.Integer checkAdminId) {
		this.checkAdminId=checkAdminId;
	}
	public java.util.Date getCheckTime() {
		return this.checkTime;
	}
	public void setCheckTime(java.util.Date checkTime) {
		this.checkTime=checkTime;
	}
	public java.lang.Integer getLossAdminId() {
		return this.lossAdminId;
	}
	public void setLossAdminId(java.lang.Integer lossAdminId) {
		this.lossAdminId=lossAdminId;
	}
	public java.lang.String getToWalletAddress() {
		return this.toWalletAddress;
	}
	public void setToWalletAddress(java.lang.String toWalletAddress) {
		this.toWalletAddress=toWalletAddress;
	}
	public java.util.Date getLossTime() {
		return this.lossTime;
	}
	public void setLossTime(java.util.Date lossTime) {
		this.lossTime=lossTime;
	}
	public java.lang.String getLossError() {
		return this.lossError;
	}
	public void setLossError(java.lang.String lossError) {
		this.lossError=lossError;
	}
	public java.lang.String getNote() {
		return this.note;
	}
	public void setNote(java.lang.String note) {
		this.note=note;
	}
	public model.User getUser() {
		return this.user;
	}
	public void setUser(model.User value) {
		this.user=value;
	}


}
