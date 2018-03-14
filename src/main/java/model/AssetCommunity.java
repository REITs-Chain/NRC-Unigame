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


public class AssetCommunity extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_asset_community";
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
	private java.lang.String name;
	private java.lang.String name_en;
	private java.lang.String icon;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date createTime;
	private java.lang.Integer assetId;


	// Constructors

	/** default constructor */
	public AssetCommunity() {
	}

	public java.lang.Integer getId() {
		return this.id;
	}
	public void setId(java.lang.Integer id) {
		this.id=id;
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
	public java.lang.String getIcon() {
		return this.icon;
	}
	public void setIcon(java.lang.String icon) {
		this.icon=icon;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime=createTime;
	}
	public java.lang.Integer getAssetId() {
		return this.assetId;
	}
	public void setAssetId(java.lang.Integer assetId) {
		this.assetId=assetId;
	}


}
