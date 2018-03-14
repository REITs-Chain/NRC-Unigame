package model;

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


public class Message extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_message";
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
	private java.lang.String title;
	private java.lang.String content;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date createtime;
	private java.lang.Integer status;
	private model.User user;


	// Constructors

	/** default constructor */
	public Message() {
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
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setTitle(java.lang.String title) {
		this.title=title;
	}
	public java.lang.String getContent() {
		return this.content;
	}
	public void setContent(java.lang.String content) {
		this.content=content;
	}
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	public void setCreatetime(java.util.Date createtime) {
		this.createtime=createtime;
	}
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status=status;
	}
	public model.User getUser() {
		return this.user;
	}
	public void setUser(model.User value) {
		this.user=value;
	}


}
