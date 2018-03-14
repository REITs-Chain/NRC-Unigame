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


public class Tx extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_tx";
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
	private java.lang.String txid;
	private java.lang.Long version;
	private java.lang.Integer locktime;
	private java.lang.Double outvalue;
	private java.lang.String blockhash;
	private java.lang.Long confirmations;
	private java.lang.Integer time;
	private java.lang.Integer blocktime;
	private java.lang.Integer year;
	private java.lang.Integer month;
	private java.lang.Integer day;


	// Constructors

	/** default constructor */
	public Tx() {
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
	public java.lang.String getTxid() {
		return this.txid;
	}
	public void setTxid(java.lang.String txid) {
		this.txid=txid;
	}
	public java.lang.Long getVersion() {
		return this.version;
	}
	public void setVersion(java.lang.Long version) {
		this.version=version;
	}
	public java.lang.Integer getLocktime() {
		return this.locktime;
	}
	public void setLocktime(java.lang.Integer locktime) {
		this.locktime=locktime;
	}
	public java.lang.Double getOutvalue() {
		return this.outvalue;
	}
	public void setOutvalue(java.lang.Double outvalue) {
		this.outvalue=outvalue;
	}
	public java.lang.String getBlockhash() {
		return this.blockhash;
	}
	public void setBlockhash(java.lang.String blockhash) {
		this.blockhash=blockhash;
	}
	public java.lang.Long getConfirmations() {
		return this.confirmations;
	}
	public void setConfirmations(java.lang.Long confirmations) {
		this.confirmations=confirmations;
	}
	public java.lang.Integer getTime() {
		return this.time;
	}
	public void setTime(java.lang.Integer time) {
		this.time=time;
	}
	public java.lang.Integer getBlocktime() {
		return this.blocktime;
	}
	public void setBlocktime(java.lang.Integer blocktime) {
		this.blocktime=blocktime;
	}
	public java.lang.Integer getYear() {
		return this.year;
	}
	public void setYear(java.lang.Integer year) {
		this.year=year;
	}
	public java.lang.Integer getMonth() {
		return this.month;
	}
	public void setMonth(java.lang.Integer month) {
		this.month=month;
	}
	public java.lang.Integer getDay() {
		return this.day;
	}
	public void setDay(java.lang.Integer day) {
		this.day=day;
	}


}
