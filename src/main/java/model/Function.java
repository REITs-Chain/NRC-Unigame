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


public class Function extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "w_function";
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
	private java.lang.Integer fparentId;
	private java.lang.Integer flevel;
	private java.lang.Boolean fisGroup;
	private java.lang.Integer findex;
	private java.lang.String furl;
	private java.lang.String ficon;
	private java.lang.Boolean fvisible;


	// Constructors

	/** default constructor */
	public Function() {
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
	public java.lang.Integer getFparentId() {
		return this.fparentId;
	}
	public void setFparentId(java.lang.Integer fparentId) {
		this.fparentId=fparentId;
	}
	public java.lang.Integer getFlevel() {
		return this.flevel;
	}
	public void setFlevel(java.lang.Integer flevel) {
		this.flevel=flevel;
	}
	public java.lang.Boolean getFisGroup() {
		return this.fisGroup;
	}
	public void setFisGroup(java.lang.Boolean fisGroup) {
		this.fisGroup=fisGroup;
	}
	public java.lang.Integer getFindex() {
		return this.findex;
	}
	public void setFindex(java.lang.Integer findex) {
		this.findex=findex;
	}
	public java.lang.String getFurl() {
		return this.furl;
	}
	public void setFurl(java.lang.String furl) {
		this.furl=furl;
	}
	public java.lang.String getFicon() {
		return this.ficon;
	}
	public void setFicon(java.lang.String ficon) {
		this.ficon=ficon;
	}
	public java.lang.Boolean getFvisible() {
		return this.fvisible;
	}
	public void setFvisible(java.lang.Boolean fvisible) {
		this.fvisible=fvisible;
	}


}
