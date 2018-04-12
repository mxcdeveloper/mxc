package com.xavier.config.env;

public interface EnvConfiguration {
	
	/***** Database connection ************/
	String getDatabaseName();
	String getDatabaseUser();
	String getDatabasePassword();
	String getDatabaseIP();
	int getDatabasePort();
	String getDatabaseDriver();
	/****** Ethereum Geth client connection ******/
	String getEthereumGethUrl();
	/****** Wallet path ******/
	String getWalletPath();

}
