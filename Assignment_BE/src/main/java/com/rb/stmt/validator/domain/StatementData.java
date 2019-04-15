/**
 * Copyright (c) 2019, ABC Ltd.
 */
package com.rb.stmt.validator.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class which holds the transaction details
 * 
 * @author
 *
 */
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatementData {

	@XmlAttribute(name = "reference")
	private Integer transactionReference;
	@XmlElement(name = "accountNumber")
	private String accountNumber;
	@XmlElement(name = "description")
	private String description;
	@XmlElement(name = "startBalance")
	private BigDecimal startBalance;
	@XmlElement(name = "mutation")
	private String mutation;
	@XmlElement(name = "endBalance")
	private BigDecimal endBalance;

	/**
	 * @return the transactionReference
	 */
	public Integer getTransactionReference() {
		return transactionReference;
	}

	/**
	 * @param transactionReference the transactionReference to set
	 */
	public void setTransactionReference(Integer transactionReference) {
		this.transactionReference = transactionReference;
	}

	/**
	 * @return the accountNumber
	 */

	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the description
	 */

	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the mutation
	 */

	public String getMutation() {
		return mutation;
	}

	/**
	 * @param mutation the mutation to set
	 */
	public void setMutation(String mutation) {
		this.mutation = mutation;
	}

	/**
	 * @return the startBalance
	 */

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	/**
	 * @param startBalance the startBalance to set
	 */
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	/**
	 * @return the endBalance
	 */

	public BigDecimal getEndBalance() {
		return endBalance.setScale(2);
	}

	/**
	 * @param endBalance the endBalance to set
	 */
	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	@Override
	public String toString() {
		return "StatementData [transactionReference :: " + transactionReference + ", accountNumber :: " + accountNumber
				+ ", description :: " + description + ", startBalance :: " + startBalance + ", mutation :: " + mutation
				+ ", endBalance :: " + endBalance + "]";
	}
}
