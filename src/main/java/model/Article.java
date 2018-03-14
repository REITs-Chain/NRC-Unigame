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


public class Article extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_article";
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
	private java.lang.String title;
	private java.lang.String content;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date relaseTime;
	private java.lang.Integer publisher;
	private java.lang.String photo;
	private java.lang.String brief;


	// Constructors

	/** default constructor */
	public Article() {
	}

	public java.lang.Integer getId() {
		return this.id;
	}
	public void setId(java.lang.Integer id) {
		this.id=id;
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
	public java.util.Date getRelaseTime() {
		return this.relaseTime;
	}
	public void setRelaseTime(java.util.Date relaseTime) {
		this.relaseTime=relaseTime;
	}
	public java.lang.Integer getPublisher() {
		return this.publisher;
	}
	public void setPublisher(java.lang.Integer publisher) {
		this.publisher=publisher;
	}
	public java.lang.String getPhoto() {
		return this.photo;
	}
	public void setPhoto(java.lang.String photo) {
		this.photo=photo;
	}
	public java.lang.String getBrief() {
		return this.brief;
	}
	public void setBrief(java.lang.String brief) {
		this.brief=brief;
	}


}
