/**
 * Copyright (c) 2019, ABC Ltd.
 */
package com.rb.stmt.validator.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rb.stmt.validator.domain.FailedResponse;
import com.rb.stmt.validator.domain.ResponseData;
import com.rb.stmt.validator.domain.StatementData;
import com.rb.stmt.validator.domain.StatementDatas;

/**
 * Service layer used to read and validate the transaction data
 * 
 * @author
 *
 */

@Service
public class ValidatorService implements ValidatorServiceIfc {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorService.class);

	@Value("${file.path.processed}")
	private String processedFilePath;

	@Value("${file.path.unprocessed}")
	private String unprocessedFilePath;

	public ResponseData validateAndGetFailedTrans(String fileType) throws Exception {
		BufferedReader br = null;
		List<File> filesInFolder = null;
		FileWriter fw = null;
		ResponseData responseData = new ResponseData();
		List<FailedResponse> failedResponses = new ArrayList<>();
		try {
			filesInFolder = Files.walk(Paths.get(unprocessedFilePath)).filter(Files::isRegularFile).map(Path::toFile)
					.filter(e -> e.getName().endsWith(fileType)).collect(Collectors.toList());
			if (filesInFolder.isEmpty()) {
				responseData.setErrorMsg("Files not found in the given location...");
			}
			// XXX: Can apply factory pattern based on the time
			if (fileType.equals("csv")) {
				extractCsv(filesInFolder, fw, failedResponses);
			} else if (fileType.equals("xml")) {
				extractXml(br, filesInFolder, fw, failedResponses);
			}
			responseData.setFailedResponse(failedResponses);
		} catch (Exception e) {
			logger.error("Exception occured :: ", e);
			throw new Exception("Some issue in application, Please try after sometime...");
		}

		return responseData;
	}

	/**
	 * 
	 * @param br
	 * @param filesInFolder
	 * @param fw
	 * @param failedResponses
	 * @throws JAXBException
	 * @throws IOException
	 */
	private void extractXml(BufferedReader br, List<File> filesInFolder, FileWriter fw,
			List<FailedResponse> failedResponses) throws JAXBException, IOException {
		for (File file : filesInFolder) {
			JAXBContext jaxbContext = JAXBContext.newInstance(StatementDatas.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StatementDatas stmts = (StatementDatas) jaxbUnmarshaller.unmarshal(file);
			// Create List for holding Employee objects
			List<Integer> refList = new ArrayList<>();
			Map<String, List<StatementData>> stMap = new HashMap<>();
			List<StatementData> failedTrans = new LinkedList<>();
			for (StatementData sd : stmts.getModels()) {

				validateData(refList, stMap, failedTrans, sd);
			}
			buildResponse(failedResponses, stMap, failedTrans);
			moveFile(file);
			deleteFile(br, file);
		}
	}

	/**
	 * 
	 * @param filesInFolder
	 * @param fw
	 * @param failedResponses
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void extractCsv(List<File> filesInFolder, FileWriter fw, List<FailedResponse> failedResponses)
			throws IOException {
		for (File file : filesInFolder) {
			logger.debug("Processing for the file name :: {}", file.getName());

			// Reading the csv file
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {

				// Create List for holding Employee objects
				List<Integer> refList = new ArrayList<>();
				Map<String, List<StatementData>> stMap = new HashMap<>();
				List<StatementData> failedTrans = new LinkedList<>();

				String line = "";
				// Read to skip the header
				br.readLine();
				// Reading from the second line
				while ((line = br.readLine()) != null) {
					String[] transDetails = line.split(",");

					if (transDetails.length > 0) {
						// Save the employee details in Employee object
						StatementData sd = new StatementData();
						sd.setTransactionReference(Integer.parseInt(transDetails[0]));
						sd.setAccountNumber(transDetails[1]);
						sd.setDescription(transDetails[2]);
						sd.setStartBalance(new BigDecimal(transDetails[3]));
						sd.setMutation(transDetails[4]);
						sd.setEndBalance(new BigDecimal(transDetails[5]));
						validateData(refList, stMap, failedTrans, sd);
					}

				}

				buildResponse(failedResponses, stMap, failedTrans);
				moveFile(file);
				deleteFile(br, file);

			}
		}
	}

	/**
	 * 
	 * @param failedResponses
	 * @param stMap
	 * @param failedTrans
	 */
	private void buildResponse(List<FailedResponse> failedResponses, Map<String, List<StatementData>> stMap,
			List<StatementData> failedTrans) {
		for (StatementData st : validateTransaction(stMap, failedTrans)) {
			failedResponses.add(new FailedResponse(st.getTransactionReference(), st.getDescription()));
		}
	}

	/**
	 * 
	 * @param refList
	 * @param stMap
	 * @param failedTrans
	 * @param sd
	 */
	private void validateData(List<Integer> refList, Map<String, List<StatementData>> stMap,
			List<StatementData> failedTrans, StatementData sd) {
		if (refList.contains(sd.getTransactionReference())) {
			failedTrans.add(sd);

		} else {
			refList.add(sd.getTransactionReference());
		}
		if (stMap.containsKey(sd.getAccountNumber())) {
			List<StatementData> st = stMap.get(sd.getAccountNumber());
			st.add(sd);
			stMap.put(sd.getAccountNumber(), st);
		} else {
			List<StatementData> st = new ArrayList<>();
			stMap.put(sd.getAccountNumber(), st);
		}
	}

	/**
	 * 
	 * @param fw
	 * @param br
	 * @param file
	 * @throws IOException
	 */
	private void deleteFile(BufferedReader br, File file) throws IOException {
		if (br != null) {
			br.close();
		}
		Files.deleteIfExists(file.toPath());
	}

	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void moveFile(File file) throws IOException {

		Files.copy(file.toPath(), new File(processedFilePath + File.separator + file.getName()).toPath(),
				StandardCopyOption.REPLACE_EXISTING);

	}

	/**
	 * 
	 * @param processedMap
	 * @param failedTrans
	 * @return
	 */
	private static List<StatementData> validateTransaction(Map<String, List<StatementData>> processedMap,
			List<StatementData> failedTrans) {
		String pattern = "#0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		for (Map.Entry<String, List<StatementData>> entry : processedMap.entrySet()) {
			for (StatementData st : entry.getValue()) {
				BigDecimal startBal = st.getStartBalance();
				if (st.getMutation().startsWith("-")) {
					BigDecimal bt = new BigDecimal(st.getMutation().substring(1));
					if (!myFormatter.format(startBal.subtract(bt)).equals(myFormatter.format(st.getEndBalance()))) {
						failedTrans.add(st);
					}
				} else {
					if (!myFormatter.format(startBal.add(new BigDecimal(st.getMutation())))
							.equals(myFormatter.format(st.getEndBalance()))) {
						failedTrans.add(st);
					}
				}
			}
		}
		logger.info("failedTrans size :: {}", failedTrans.size());
		return failedTrans;
	}

	/**
	 * @param processedFilePath the processedFilePath to set
	 */
	public void setProcessedFilePath(String processedFilePath) {
		this.processedFilePath = processedFilePath;
	}

	/**
	 * @param unprocessedFilePath the unprocessedFilePath to set
	 */
	public void setUnprocessedFilePath(String unprocessedFilePath) {
		this.unprocessedFilePath = unprocessedFilePath;
	}
}
