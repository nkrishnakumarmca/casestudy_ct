/**
 * Copyright (c) 2019, ABC Ltd.
 */
package com.rb.stmt.validator.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class which is used to hold the data from xml
 * 
 * @author
 *
 */
@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatementDatas {

	@XmlElement(name = "record")
	List<StatementData> models;

	/**
	 * @return the models
	 */
	public List<StatementData> getModels() {
		return models;
	}

	/**
	 * @param models the models to set
	 */
	public void setModels(List<StatementData> models) {
		this.models = models;
	}

}
