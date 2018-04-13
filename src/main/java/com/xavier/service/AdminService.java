package com.xavier.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.xavier.config.env.EnvConfiguration;
import com.xavier.config.ethereum.EthereumConnection;
import com.xavier.controller.admin.AdminController;
import com.xavier.domain.AdminDomain;
import com.xavier.domain.TokenDomain;
import com.xavier.domain.TransactionDomain;
import com.xavier.domain.UserDomain;
import com.xavier.erc20token.contract.FixedSupplyToken;
import com.xavier.repository.AdminRepository;
import com.xavier.repository.TokenRepository;
import com.xavier.repository.TransactionRepository;
import com.xavier.repository.UserRepository;

@Service
public class AdminService {

	private static Logger LOGGER = LoggerFactory.getLogger(AdminService.class);
	@Autowired
	EnvConfiguration envConfiguration;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	EthereumConnection ethereumConnection;
	@Autowired
	TokenRepository tokenRepository;
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	UserRepository userRepository;

	static final BigInteger gasPrice = BigInteger.valueOf(4700000); // 0.0000000000047 ETHER
	static final BigInteger gasLimit = BigInteger.valueOf(3100000); // 0.0000000000031 ETHER
	static final BigInteger totalSupply = BigInteger.valueOf(7000000000L); // it will have incresed 2 digit for prod
	static final String tokenName = "My-Xavier-Token";
	static final BigInteger decimalUnits = BigInteger.valueOf(2);
	static final String tokenSymbole = "MXTK";

	public Map<String, Object> createAdminWallet() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidAlgorithmParameterException, CipherException, IOException, InterruptedException, ExecutionException {

		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(envConfiguration.getWalletPath());
		String password = "admin" + Math.random();
		String fileName = WalletUtils.generateFullNewWalletFile(password, file);
		AdminDomain admin = new AdminDomain();
		String address = address(password, fileName);
		admin.setAddress(address);
		admin.setFileName(fileName);
		admin.setPassword(password);
		admin.setEther(new BigDecimal("0"));
		admin.setDateCreated(new Date());
		admin.setToken(new BigInteger("0"));
		adminRepository.save(admin);
		map.put("address", address);
		map.put("Ether", new BigInteger("0"));
		return map;
	}

	public Map<String, Object> createToken()
			throws IOException, CipherException, InterruptedException, ExecutionException {
		LOGGER.info("createToken method called");
		Map<String, Object> map = new HashMap<String, Object>();
		List<AdminDomain> admin = adminRepository.findAll();
		String password = null;
		String path = null;
		String adminAddress = null;
		if (admin != null) {
			for (AdminDomain adm : admin) {
				password = adm.getPassword();
				adminAddress = adm.getAddress();
				path = envConfiguration.getWalletPath() + "/" + adm.getFileName();
			}
		}
		LOGGER.info("path of file " + path);
		Credentials credentials = WalletUtils.loadCredentials(password, path);

		Web3j web3j = ethereumConnection.getEthereumConnection();

		RemoteCall<FixedSupplyToken> tokenFuture = FixedSupplyToken.deploy(web3j, credentials, gasPrice, gasLimit,
				totalSupply, tokenName, decimalUnits, tokenSymbole);
		LOGGER.info("cretion of contracts " + gasPrice + " " + gasLimit + " " + totalSupply + " " + tokenName + " "
				+ decimalUnits + " " + tokenSymbole);
		String contractAddress = tokenFuture.sendAsync().get().getContractAddress();
		LOGGER.info("contractAddress " + contractAddress);
		TokenDomain tokenDomain = new TokenDomain();
		tokenDomain.setAdminAddress(adminAddress);
		tokenDomain.setContractAddress(contractAddress);
		tokenDomain.setName(tokenName);
		tokenDomain.setSymbole(tokenSymbole);
		tokenDomain.setTotalSupply(totalSupply);
		tokenDomain.setDateCreated(new Date());
		tokenDomain.setRemainingToken(totalSupply);
		tokenRepository.save(tokenDomain);
		map.put("Admin address", adminAddress);
		map.put("Contract Address", contractAddress);
		map.put("Token Name", tokenName);
		map.put("Token Symbole", tokenSymbole);
		map.put("Total Supply", totalSupply);

		return map;
	}

	public Map<String, Object> sendTokenToUser(String toAddress, BigInteger token) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		Credentials credentials = getCredentials();
		String contractAddress = getContractAddress();
		FixedSupplyToken fixedSupplyToken = FixedSupplyToken.load(contractAddress,
				ethereumConnection.getEthereumConnection(), credentials, gasPrice, gasLimit);
		RemoteCall<TransactionReceipt> obj = fixedSupplyToken.transfer(toAddress, token);
		String hash = obj.send().getTransactionHash();
		LOGGER.info("hash of send transaction " + hash);
		System.out.println("helllo");
		System.out.println("token "+token);
		if (hash != null) {
			System.out.println("hash " + hash);
			UserDomain userDomain = userRepository.findByAddress(toAddress);
			if (userDomain != null) {
				userDomain.setToken(userDomain.getToken().add(token));
				UserDomain u = userRepository.save(userDomain);
			}
			TransactionDomain transactionDomain = new TransactionDomain();
			transactionDomain.setContractAddress(contractAddress);
			transactionDomain.setToAddress(toAddress);
			transactionDomain.setHash(hash);
			transactionDomain.setDate(new Date());
			transactionDomain.setToken(token);
			transactionRepository.save(transactionDomain);
			map.put("Transaction Hash", hash);
			LOGGER.info("hash of send transaction " + map);
			return map;
		}
		return null;
	}

	public List<TransactionDomain> getTransactionDetails() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TransactionDomain> transaction = transactionRepository.findAll();
		return transaction;
	}

	public Map<String, Object> getAvailableToken()
			throws IOException, CipherException, InterruptedException, ExecutionException {
		Map<String, Object> map = new HashMap<String, Object>();
		Credentials credentials = getCredentials();
		String contractAddress = getContractAddress();
		FixedSupplyToken fixedSupplyToken = FixedSupplyToken.load(contractAddress,
				ethereumConnection.getEthereumConnection(), credentials, gasPrice, gasLimit);
		String address_to_be_checked = credentials.getAddress();
		RemoteCall<BigInteger> obj = fixedSupplyToken.balanceOf(address_to_be_checked);
		map.put("Available Token", obj.sendAsync().get());

		return map;
	}
	public List<AdminDomain> getAdminDetails(){
		List<AdminDomain> admin = adminRepository.findAll();
		return admin;
	}

	public String address(String password, String fileName)
			throws IOException, CipherException, InterruptedException, ExecutionException {
		File file = new File(envConfiguration.getWalletPath() + "/" + fileName);
		Credentials credentials = WalletUtils.loadCredentials(password, file);
		System.out.println(credentials.getAddress());
		return credentials.getAddress();
	}

	public Credentials getCredentials() throws IOException, CipherException {
		List<AdminDomain> admin = adminRepository.findAll();
		String password = null;
		String path = null;
		String adminAddress = null;
		if (admin != null) {
			for (AdminDomain adm : admin) {
				password = adm.getPassword();
				adminAddress = adm.getAddress();
				path = envConfiguration.getWalletPath() + "/" + adm.getFileName();
			}
		}
		System.out.println("path " + path);
		return WalletUtils.loadCredentials(password, path);
	}

	public String getContractAddress() {
		List<TokenDomain> tokenRepo = tokenRepository.findAll();
		String contractAddress = null;
		if (tokenRepo != null) {
			for (TokenDomain token : tokenRepo) {
				contractAddress = token.getContractAddress();
			}
		}
		return contractAddress;
	}

}
