package com.xavier.config.ethereum;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

import com.xavier.config.env.EnvConfiguration;

@Service
public class EthereumConnection {
	@Autowired
	EnvConfiguration envConfiguration;
	public Web3j getEthereumConnection() throws InterruptedException, ExecutionException {
		System.out.println("...............////////");
		HttpService httpService = new HttpService(envConfiguration.getEthereumGethUrl());
		Web3j web3jAdmin = Admin.build(httpService);
		String clientVersion = web3jAdmin.web3ClientVersion().sendAsync().get().getWeb3ClientVersion();
		System.out.println("clientVersion "+clientVersion);
		return web3jAdmin;
	}

}
