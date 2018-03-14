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


public class OrganInfo extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_organinfo";
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

	private java.lang.Long id;
	private java.lang.Integer type;
	private java.lang.Long userId;
	private java.lang.String name;
	private java.lang.String name_en;
	private java.lang.String url;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date regTime;
	private java.lang.String content;
	private model.User user;


	// Constructors

	/** default constructor */
	public OrganInfo() {
	}

	public java.lang.Long getId() {
		return this.id;
	}
	public void setId(java.lang.Long id) {
		this.id=id;
	}
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setType(java.lang.Integer type) {
		this.type=type;
	}
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId=userId;
	}
	public java.lang.String getName() {
		return this.name;
	}
	public void setName(java.lang.String name) {
		this.name=name;
	}
	public java.lang.String getName_en() {
		return this.name_en;
	}
	public void setName_en(java.lang.String name_en) {
		this.name_en=name_en;
	}
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setUrl(java.lang.String url) {
		this.url=url;
	}
	public java.util.Date getRegTime() {
		return this.regTime;
	}
	public void setRegTime(java.util.Date regTime) {
		this.regTime=regTime;
	}
	public java.lang.String getContent() {
		return this.content;
	}
	public void setContent(java.lang.String content) {
		this.content=content;
	}
	public model.User getUser() {
		return this.user;
	}
	public void setUser(model.User value) {
		this.user=value;
	}


}
