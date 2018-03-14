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


public class Group extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_group";
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
	private java.lang.String groupQRcode;
	private java.lang.Integer minStarLevel;
	private java.lang.Integer status;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date createTime;
	private java.lang.String createPerson;
	private java.lang.String manager;
	private java.lang.Integer managerNum;
	private java.lang.String describe;
	private java.lang.Integer communityId;
	private java.lang.Integer synchron;
	private java.util.List<model.UserGroup> users;


	// Constructors

	/** default constructor */
	public Group() {
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
	public java.lang.String getGroupQRcode() {
		return this.groupQRcode;
	}
	public void setGroupQRcode(java.lang.String groupQRcode) {
		this.groupQRcode=groupQRcode;
	}
	public java.lang.Integer getMinStarLevel() {
		return this.minStarLevel;
	}
	public void setMinStarLevel(java.lang.Integer minStarLevel) {
		this.minStarLevel=minStarLevel;
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
	public java.lang.String getCreatePerson() {
		return this.createPerson;
	}
	public void setCreatePerson(java.lang.String createPerson) {
		this.createPerson=createPerson;
	}
	public java.lang.String getManager() {
		return this.manager;
	}
	public void setManager(java.lang.String manager) {
		this.manager=manager;
	}
	public java.lang.Integer getManagerNum() {
		return this.managerNum;
	}
	public void setManagerNum(java.lang.Integer managerNum) {
		this.managerNum=managerNum;
	}
	public java.lang.String getDescribe() {
		return this.describe;
	}
	public void setDescribe(java.lang.String describe) {
		this.describe=describe;
	}
	public java.lang.Integer getCommunityId() {
		return this.communityId;
	}
	public void setCommunityId(java.lang.Integer communityId) {
		this.communityId=communityId;
	}
	public java.lang.Integer getSynchron() {
		return this.synchron;
	}
	public void setSynchron(java.lang.Integer synchron) {
		this.synchron=synchron;
	}
	public java.util.List<model.UserGroup> getUsers() {
		return this.users;
	}
	public void setUsers(java.util.List<model.UserGroup> value) {
		this.users=value;
	}


}
