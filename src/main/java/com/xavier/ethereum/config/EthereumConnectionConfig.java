package com.xavier.ethereum.config;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

import com.xavier.config.env.EnvConfiguration;



/**
 * Geth connection config bean
 * 
 * @author Rahbar ali
 *
 */
@Configuration
public class EthereumConnectionConfig {
	private Logger log = LoggerFactory.getLogger(EthereumConnectionConfig.class);

	@Autowired
	private EnvConfiguration envConfiguration;

	@Bean
	public
	Admin getEthereumConnection() throws InterruptedException, ExecutionException {
		HttpService httpService = new HttpService(envConfiguration.getEthereumGethUrl());
		Admin web3jAdmin = Admin.build(httpService);
		String clientVersion = web3jAdmin.web3ClientVersion().sendAsync().get().getWeb3ClientVersion();
		log.info("Conncted to ethereum client with version {}", clientVersion);
		return web3jAdmin;
	}

}
