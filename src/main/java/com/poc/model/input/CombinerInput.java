package com.poc.model.input;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CombinerInput implements Serializable
{
	@JsonProperty("ID")
	private String ID;
	
	@JsonProperty("Message")
	private String message;
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}