package com.xavier.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String contractAddress;
	String toAddress;
	BigDecimal ether;
	String hash;
	Date date;
	BigInteger token;
	public BigInteger getToken() {
		return token;
	}
	public void setToken(BigInteger token) {
		this.token = token;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public BigDecimal getEther() {
		return ether;
	}
	public void setEther(BigDecimal ether) {
		this.ether = ether;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
