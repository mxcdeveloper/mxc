package com.xavier.dto;

import java.math.BigDecimal;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionDTO {

	@NotBlank
	String address;
	@NotNull
	@DecimalMin("0.01")
	BigDecimal token;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getToken() {
		return token;
	}

	public void setToken(BigDecimal token) {

		this.token = token;
	}
	
	@AssertTrue(message="please provide value with 2 decimal precision")
	boolean isTokenValid(){
		boolean isValid = false;
		int dotIndex = this.token.toString().indexOf(".");
		if(dotIndex==-1) return false;
		int decimalCount = this.token.toString().substring(dotIndex+1,this.token.toString().length()).length();
		if(decimalCount<3) isValid=true;
		return isValid;
	}
}
