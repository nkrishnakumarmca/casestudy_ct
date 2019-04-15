package com.rb.stmt.validator.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rb.stmt.validator.config.AppConfig;
import com.rb.stmt.validator.domain.FailedResponse;
import com.rb.stmt.validator.domain.ResponseData;
import com.rb.stmt.validator.service.ValidatorServiceIfc;

/**
 * 
 * @author
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Import(AppConfig.class)
public class ValidatorControllerTest {

	@Mock
	private ValidatorServiceIfc validatorServiceIfc;

	private ValidatorController validatorController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		validatorController = new ValidatorController();
		validatorController.setFileFormats(Arrays.asList("csv", "xml"));
		validatorController.setValidatorServiceIfc(validatorServiceIfc);

	}

	@Test
	public void testInvalidGetData() throws Exception {
		ResponseData responseData = new ResponseData();
		List<FailedResponse> failedResponse = new ArrayList<>();
		responseData.setFailedResponse(failedResponse);
		when(validatorServiceIfc.validateAndGetFailedTrans("cs")).thenReturn(responseData);
		ResponseEntity<Object> rep = validatorController.getData("cs");
		assertEquals(HttpStatus.BAD_REQUEST, rep.getStatusCode());
	}

	@Test
	public void testgetData() throws Exception {
		ResponseData responseData = new ResponseData();
		List<FailedResponse> failedResponse = new ArrayList<>();
		responseData.setFailedResponse(failedResponse);
		when(validatorServiceIfc.validateAndGetFailedTrans("csv")).thenReturn(responseData);
		ResponseEntity<Object> rep = validatorController.getData("csv");
		assertNotNull(rep);
		verify(validatorServiceIfc).validateAndGetFailedTrans("csv");

	}
}
