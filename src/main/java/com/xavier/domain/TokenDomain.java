package com.xavier.domain;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TokenDomain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String name;
	String symbole;
	BigInteger totalSupply;
	String contractAddress;
	String adminAddress;
	Date dateCreated;
	BigInteger remainingToken;
	public int getId() {
		return id;
	}
	public BigInteger getRemainingToken() {
		return remainingToken;
	}
	public void setRemainingToken(BigInteger remainingToken) {
		this.remainingToken = remainingToken;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbole() {
		return symbole;
	}
	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}
	public BigInteger getTotalSupply() {
		return totalSupply;
	}
	public void setTotalSupply(BigInteger totalSupply) {
		this.totalSupply = totalSupply;
	}
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public String getAdminAddress() {
		return adminAddress;
	}
	public void setAdminAddress(String adminAddress) {
		this.adminAddress = adminAddress;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	

}
