/**
 * Copyright (c) 2019, ABC Ltd.
 */
package com.rb.stmt.validator.service;

import com.rb.stmt.validator.domain.ResponseData;

/**
 * 
 * @author
 *
 */
public interface ValidatorServiceIfc {

	/**
	 * 
	 * @param fileType
	 */
	public ResponseData validateAndGetFailedTrans(String fileType) throws Exception;
}
