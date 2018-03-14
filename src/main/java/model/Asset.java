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


public class Asset extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_asset";
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
	private java.lang.String sname;
	private java.lang.String name;
	private java.lang.String name_en;
	private java.lang.Long circulation;
	private java.lang.String issueAddress;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date issueTime;
	private java.lang.Integer status;
	private java.lang.String imageUrl;
	private java.lang.String produtIntroUrl;
	private java.lang.String issueDatum;
	private java.lang.String title;
	private java.lang.String content;


	// Constructors

	/** default constructor */
	public Asset() {
	}

	public java.lang.Integer getId() {
		return this.id;
	}
	public void setId(java.lang.Integer id) {
		this.id=id;
	}
	public java.lang.String getSname() {
		return this.sname;
	}
	public void setSname(java.lang.String sname) {
		this.sname=sname;
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
	public java.lang.Long getCirculation() {
		return this.circulation;
	}
	public void setCirculation(java.lang.Long circulation) {
		this.circulation=circulation;
	}
	public java.lang.String getIssueAddress() {
		return this.issueAddress;
	}
	public void setIssueAddress(java.lang.String issueAddress) {
		this.issueAddress=issueAddress;
	}
	public java.util.Date getIssueTime() {
		return this.issueTime;
	}
	public void setIssueTime(java.util.Date issueTime) {
		this.issueTime=issueTime;
	}
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status=status;
	}
	public java.lang.String getImageUrl() {
		return this.imageUrl;
	}
	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl=imageUrl;
	}
	public java.lang.String getProdutIntroUrl() {
		return this.produtIntroUrl;
	}
	public void setProdutIntroUrl(java.lang.String produtIntroUrl) {
		this.produtIntroUrl=produtIntroUrl;
	}
	public java.lang.String getIssueDatum() {
		return this.issueDatum;
	}
	public void setIssueDatum(java.lang.String issueDatum) {
		this.issueDatum=issueDatum;
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


}
