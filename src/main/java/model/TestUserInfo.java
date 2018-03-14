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


public class TestUserInfo extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_testuser";
	public static final String _keyName = "userId";

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

	private java.lang.Integer userId;
	private java.lang.String realName;
	private java.lang.String idNum;
	private java.lang.String phoneNum;
	private java.lang.Integer status;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date createTime;
	private java.lang.String note;


	// Constructors

	/** default constructor */
	public TestUserInfo() {
	}

	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setUserId(java.lang.Integer userId) {
		this.userId=userId;
	}
	public java.lang.String getRealName() {
		return this.realName;
	}
	public void setRealName(java.lang.String realName) {
		this.realName=realName;
	}
	public java.lang.String getIdNum() {
		return this.idNum;
	}
	public void setIdNum(java.lang.String idNum) {
		this.idNum=idNum;
	}
	public java.lang.String getPhoneNum() {
		return this.phoneNum;
	}
	public void setPhoneNum(java.lang.String phoneNum) {
		this.phoneNum=phoneNum;
	}
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status=status;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime=createTime;
	}
	public java.lang.String getNote() {
		return this.note;
	}
	public void setNote(java.lang.String note) {
		this.note=note;
	}


}
