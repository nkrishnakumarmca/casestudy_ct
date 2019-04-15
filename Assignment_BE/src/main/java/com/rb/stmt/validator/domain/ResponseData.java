/**
 * Copyright (c) 2019, ABC Ltd.
 */
package com.rb.stmt.validator.domain;

import java.util.List;

/**
 * Model class which holds the response structure
 * 
 * @author
 *
 */
public class ResponseData {

	private List<FailedResponse> failedResponse;
	private String errorMsg;

	/**
	 * @return the failedResponse
	 */
	public List<FailedResponse> getFailedResponse() {
		return failedResponse;
	}

	/**
	 * @param failedResponse the failedResponse to set
	 */
	public void setFailedResponse(List<FailedResponse> failedResponse) {
		this.failedResponse = failedResponse;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
