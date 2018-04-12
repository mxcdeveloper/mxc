package com.xavier.domain;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BigInteger getEther() {
		return ether;
	}
	public void setEther(BigInteger ether) {
		this.ether = ether;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public BigInteger getToken() {
		return token;
	}
	public void setToken(BigInteger token) {
		this.token = token;
	}
	String fileName;
	String address;
	BigInteger ether;
	String password;
	Date dateCreated;
	BigInteger token;

}
