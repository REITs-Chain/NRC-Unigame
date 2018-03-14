package model;

import javax.persistence.Transient;
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


public class Admin extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "w_admin";
	public static final String _keyName = "fid";

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

	private java.lang.Integer fid;
	private java.lang.String fnumber;
	private java.lang.String fname;
	private java.lang.String fpassword;
	private java.lang.String flevel;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date fcreatetime;
	private java.lang.String fnote;
	private java.lang.Boolean fclose;
	private java.lang.String phoneNum;
	private java.lang.String okPassword;
	@Transient
	private java.lang.String password;
	@Transient
	private java.lang.String verifyNum;

	
	
	// Constructors

	/** default constructor */
	public Admin() {
	}

	public java.lang.Integer getFid() {
		return this.fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid=fid;
	}
	public java.lang.String getFnumber() {
		return this.fnumber;
	}
	public void setFnumber(java.lang.String fnumber) {
		this.fnumber=fnumber;
	}
	public java.lang.String getFname() {
		return this.fname;
	}
	public void setFname(java.lang.String fname) {
		this.fname=fname;
	}
	public java.lang.String getFpassword() {
		return this.fpassword;
	}
	public void setFpassword(java.lang.String fpassword) {
		this.fpassword=fpassword;
	}
	public java.lang.String getFlevel() {
		return this.flevel;
	}
	public void setFlevel(java.lang.String flevel) {
		this.flevel=flevel;
	}
	public java.util.Date getFcreatetime() {
		return this.fcreatetime;
	}
	public void setFcreatetime(java.util.Date fcreatetime) {
		this.fcreatetime=fcreatetime;
	}
	public java.lang.String getFnote() {
		return this.fnote;
	}
	public void setFnote(java.lang.String fnote) {
		this.fnote=fnote;
	}
	public java.lang.Boolean getFclose() {
		return this.fclose;
	}
	public void setFclose(java.lang.Boolean fclose) {
		this.fclose=fclose;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public java.lang.String getVerifyNum() {
		return verifyNum;
	}

	public void setVerifyNum(java.lang.String verifyNum) {
		this.verifyNum = verifyNum;
	}

	public java.lang.String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(java.lang.String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public java.lang.String getOkPassword() {
		return okPassword;
	}

	public void setOkPassword(java.lang.String okPassword) {
		this.okPassword = okPassword;
	}
	

}
