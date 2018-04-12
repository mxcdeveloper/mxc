package com.xavier.config.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
/**
 * Environment values for development profile
 * 
 * @author Chandan lunthi
 *
 */
@Configuration
@Profile("prod")
public class ProdEnv implements EnvConfiguration{
	
	/*********** Database configuration **************/
	@Value("${myxaviertoken.database.name.pro}")
	private String databaseName;
	@Value("${myxaviertoken.database.user.pro}")
	private String databaseUser;
	@Value("${myxaviertoken.database.password.pro}")
	private String databasePassword;
	@Value("${myxaviertoken.database.ip.pro}")
	private String databaseIP;
	@Value("${myxaviertoken.database.port.pro}")
	private int databasePort;
	@Value("${myxaviertoken.database.db.driver}")
	private String databaseDriver;
	@Value("${myxaviertoken.ethereum.geth.url.pro}")
	private String ethereumGethUrl;
	@Value("${myxaviertoken.wallet.location.pro}")
	private String walletPath;
	
	@Override
	public String getDatabaseName() {
		return databaseName;
	}

	@Override
	public String getDatabasePassword() {
		return databasePassword;
	}

	@Override
	public String getDatabaseIP() {
		return databaseIP;
	}

	@Override
	public int getDatabasePort() {
		return databasePort;
	}

	@Override
	public String getDatabaseUser() {
		return databaseUser;
	}
	@Override
	public String getDatabaseDriver() {
		return databaseDriver;
	}
	@Override
	public String getEthereumGethUrl() {
		return ethereumGethUrl;
	}
	@Override
	public String getWalletPath() {
		return walletPath;
	}

}
