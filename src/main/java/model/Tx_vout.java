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


public class Tx_vout extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_tx_vout";
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
	private java.lang.Long parentid;
	private java.lang.Long userId;
	private java.lang.String rootAddress;
	private java.lang.Integer assetid;
	private java.lang.Double value;
	private java.lang.Long vout_n;
	private java.lang.String type;
	private java.lang.String address;


	// Constructors

	/** default constructor */
	public Tx_vout() {
	}

	public java.lang.Long getId() {
		return this.id;
	}
	public void setId(java.lang.Long id) {
		this.id=id;
	}
	public java.lang.Long getParentid() {
		return this.parentid;
	}
	public void setParentid(java.lang.Long parentid) {
		this.parentid=parentid;
	}
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId=userId;
	}
	public java.lang.String getRootAddress() {
		return this.rootAddress;
	}
	public void setRootAddress(java.lang.String rootAddress) {
		this.rootAddress=rootAddress;
	}
	public java.lang.Integer getAssetid() {
		return this.assetid;
	}
	public void setAssetid(java.lang.Integer assetid) {
		this.assetid=assetid;
	}
	public java.lang.Double getValue() {
		return this.value;
	}
	public void setValue(java.lang.Double value) {
		this.value=value;
	}
	public java.lang.Long getVout_n() {
		return this.vout_n;
	}
	public void setVout_n(java.lang.Long vout_n) {
		this.vout_n=vout_n;
	}
	public java.lang.String getType() {
		return this.type;
	}
	public void setType(java.lang.String type) {
		this.type=type;
	}
	public java.lang.String getAddress() {
		return this.address;
	}
	public void setAddress(java.lang.String address) {
		this.address=address;
	}


}
