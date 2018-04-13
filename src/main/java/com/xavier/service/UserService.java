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
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;


import com.xavier.config.env.EnvConfiguration;
import com.xavier.domain.UserDomain;
import com.xavier.ethereum.config.EthereumConnectionConfig;
import com.xavier.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	EthereumConnectionConfig ethereumConnectionConfig;
	@Autowired
	EnvConfiguration envConfiguration;
	@Autowired
	UserRepository userRepository;

	public Map<String, Object> createUserWallet() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidAlgorithmParameterException, CipherException, IOException, InterruptedException, ExecutionException {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(envConfiguration.getWalletPath());
		String password = "user"+Math.random();
		String fileName = WalletUtils.generateFullNewWalletFile(password, file);
		System.out.println(fileName);
		UserDomain user = new UserDomain();
		String address = address(password, fileName);
		user.setAddress(address);
		user.setFileName(fileName);
		user.setPassword(password);
		user.setEther(new BigDecimal("0"));
		user.setDateCreated(new Date());
		user.setToken(new BigInteger("0"));
		userRepository.save(user);
		map.put("address", address);
		map.put("ether", new BigInteger("0"));
		return map;
	}
	
	public Map<String, Object> getUserByAddress(String address){
		Map<String, Object> map = new HashMap<String, Object>();
		UserDomain userDomain = userRepository.findByAddress(address);
		map.put("Address", userDomain.getAddress());
		map.put("Ether", userDomain.getEther());
		map.put("Token", userDomain.getToken());
		map.put("Create Date", userDomain.getDateCreated());
		return map;
	}
	public List<UserDomain> getAllUserDetails(){
		List<UserDomain> user = userRepository.findAll();
		return user;
	}

	public String address(String password, String fileName)
			throws IOException, CipherException, InterruptedException, ExecutionException {
		File file = new File(envConfiguration.getWalletPath()+"/"+fileName);
		Credentials credentials = WalletUtils.loadCredentials(password, file);
		System.out.println(credentials.getAddress());
		return credentials.getAddress(); 
	}

}
