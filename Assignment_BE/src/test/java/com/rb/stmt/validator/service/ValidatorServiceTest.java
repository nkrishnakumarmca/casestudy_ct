package com.rb.stmt.validator.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rb.stmt.validator.config.AppConfig;
import com.rb.stmt.validator.domain.ResponseData;

/**
 * 
 * @author
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Import(AppConfig.class)
public class ValidatorServiceTest {

	private ValidatorService validatorService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		validatorService = new ValidatorService();
		validatorService.setUnprocessedFilePath("C:\\kk\\Assignment_BE\\Assignment_BE\\inputFile\\unprocessed");
		validatorService.setProcessedFilePath("C:\\kk\\Assignment_BE\\Assignment_BE\\inputFile\\processed");
	}

	@Test
	public void testValidateAndGetFailedTrans() throws Exception {
		ResponseData validateAndGetFailedTrans = validatorService.validateAndGetFailedTrans("xml");
		assertNotNull(validateAndGetFailedTrans);
	}
}
