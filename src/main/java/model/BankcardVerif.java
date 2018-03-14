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


public class BankcardVerif extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_bankcard_verif";
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
	private java.lang.String bankCardNum;
	private java.lang.String bankName;
	private java.lang.String name;
	private java.lang.String idNumber;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //HH:mm:ss
	private java.util.Date verifTime;


	// Constructors

	/** default constructor */
	public BankcardVerif() {
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
	public java.lang.String getBankCardNum() {
		return this.bankCardNum;
	}
	public void setBankCardNum(java.lang.String bankCardNum) {
		this.bankCardNum=bankCardNum;
	}
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setBankName(java.lang.String bankName) {
		this.bankName=bankName;
	}
	public java.lang.String getName() {
		return this.name;
	}
	public void setName(java.lang.String name) {
		this.name=name;
	}
	public java.lang.String getIdNumber() {
		return this.idNumber;
	}
	public void setIdNumber(java.lang.String idNumber) {
		this.idNumber=idNumber;
	}
	public java.util.Date getVerifTime() {
		return this.verifTime;
	}
	public void setVerifTime(java.util.Date verifTime) {
		this.verifTime=verifTime;
	}


}
