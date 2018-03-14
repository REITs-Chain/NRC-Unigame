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


public class Chatrecord extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_chatrecord";
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
	private java.lang.String sender;
	private java.lang.String receiver;
	private java.lang.String sendcontent;
	@DateTimeFormat(pattern="yyyy-MM-dd") //HH:mm:ss
	private java.util.Date sendtime;
	private java.lang.String remark;
	private java.lang.String image;


	// Constructors

	/** default constructor */
	public Chatrecord() {
	}

	public java.lang.Integer getId() {
		return this.id;
	}
	public void setId(java.lang.Integer id) {
		this.id=id;
	}
	public java.lang.String getSender() {
		return this.sender;
	}
	public void setSender(java.lang.String sender) {
		this.sender=sender;
	}
	public java.lang.String getReceiver() {
		return this.receiver;
	}
	public void setReceiver(java.lang.String receiver) {
		this.receiver=receiver;
	}
	public java.lang.String getSendcontent() {
		return this.sendcontent;
	}
	public void setSendcontent(java.lang.String sendcontent) {
		this.sendcontent=sendcontent;
	}
	public java.util.Date getSendtime() {
		return this.sendtime;
	}
	public void setSendtime(java.util.Date sendtime) {
		this.sendtime=sendtime;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark=remark;
	}
	public java.lang.String getImage() {
		return this.image;
	}
	public void setImage(java.lang.String image) {
		this.image=image;
	}


}
