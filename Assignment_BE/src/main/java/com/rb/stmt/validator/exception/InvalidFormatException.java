/**
 * Copyright (c) 2019, ABC Ltd.
 */
package com.rb.stmt.validator.exception;

/**
 * Exception class holds invalid file format input
 * 
 * @author
 *
 */
public class InvalidFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidFormatException(String s) {
		super(s);
	}
}
