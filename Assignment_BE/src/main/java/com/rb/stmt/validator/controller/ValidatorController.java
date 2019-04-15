/**
 * Copyright (c) 2019, ABC Ltd.
 */
package com.rb.stmt.validator.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rb.stmt.validator.domain.ResponseData;
import com.rb.stmt.validator.service.ValidatorServiceIfc;

/**
 * Controller class
 * 
 * @author
 *
 */
@RestController
public class ValidatorController {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorController.class);

	@Autowired
	private ValidatorServiceIfc validatorServiceIfc;

	@Value("#{'${accepted.file.formats}'.split(',')}")
	private List<String> fileFormats;

	/**
	 * Method to return the failed transaction details as JSON
	 * 
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/validateData/{fileType}")
	public ResponseEntity<Object> getData(@PathVariable("fileType") String fileType) throws Exception {

		logger.debug("Received file format :: {}, Configured file formats :: {}", fileType, fileFormats);
		if (!fileFormats.contains(fileType)) {
			ResponseData responseData = new ResponseData();
			responseData.setErrorMsg("Please provide the accepted file formats :: " + String.join(",", fileFormats));
			return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
		}
		ResponseData failedTransDetails = validatorServiceIfc.validateAndGetFailedTrans(fileType);
		return new ResponseEntity<>(failedTransDetails, HttpStatus.OK);
	}

	/**
	 * @param validatorServiceIfc the validatorServiceIfc to set
	 */
	public void setValidatorServiceIfc(ValidatorServiceIfc validatorServiceIfc) {
		this.validatorServiceIfc = validatorServiceIfc;
	}

	/**
	 * @param fileFormats the fileFormats to set
	 */
	public void setFileFormats(List<String> fileFormats) {
		this.fileFormats = fileFormats;
	}

}
