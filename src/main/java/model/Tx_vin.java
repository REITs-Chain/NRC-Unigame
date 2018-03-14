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


public class Tx_vin extends model.BaseEntity implements java.io.Serializable {
	public static final String _tableName = "t_tx_vin";
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
	private java.lang.Long parentId;
	private java.lang.String txid;
	private java.lang.Double vout;
	private java.lang.Long sequence;
	private java.lang.String coinbase;


	// Constructors

	/** default constructor */
	public Tx_vin() {
	}

	public java.lang.Long getId() {
		return this.id;
	}
	public void setId(java.lang.Long id) {
		this.id=id;
	}
	public java.lang.Long getParentId() {
		return this.parentId;
	}
	public void setParentId(java.lang.Long parentId) {
		this.parentId=parentId;
	}
	public java.lang.String getTxid() {
		return this.txid;
	}
	public void setTxid(java.lang.String txid) {
		this.txid=txid;
	}
	public java.lang.Double getVout() {
		return this.vout;
	}
	public void setVout(java.lang.Double vout) {
		this.vout=vout;
	}
	public java.lang.Long getSequence() {
		return this.sequence;
	}
	public void setSequence(java.lang.Long sequence) {
		this.sequence=sequence;
	}
	public java.lang.String getCoinbase() {
		return this.coinbase;
	}
	public void setCoinbase(java.lang.String coinbase) {
		this.coinbase=coinbase;
	}


}
