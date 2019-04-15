/**
 * Copyright (c) 2019, ABC Ltd.
 */
package com.rb.stmt.validator.domain;

/**
 * Model class which holds the response structure
 * 
 * @author
 *
 */
public class FailedResponse {

	private Integer transactionReference;
	private String description;

	public FailedResponse(Integer transactionReference, String description) {
		super();
		this.transactionReference = transactionReference;
		this.description = description;
	}

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
}
