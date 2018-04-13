package com.xavier.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

import com.xavier.config.env.EnvConfiguration;
import com.xavier.config.ethereum.EthereumConnection;
import com.xavier.domain.AdminDomain;
import com.xavier.domain.TransactionDomain;
import com.xavier.domain.UserDomain;
import com.xavier.repository.AdminRepository;
import com.xavier.repository.TransactionRepository;
import com.xavier.repository.UserRepository;

@Service
public class RecieveTransaction {

	@Autowired
	EthereumConnection ethereumConnection;
	@Autowired
	EnvConfiguration envConfiguration;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TransactionRepository transactionRepository;

	public void recieveTransaction() throws InterruptedException, ExecutionException {
		Web3j web3j = Web3j.build(new HttpService(envConfiguration.getEthereumGethUrl()));
		web3j.transactionObservable().subscribe(tx -> {
			BigInteger amount = new BigInteger(tx.getValue().toString()).divide(new BigInteger("1000000000000000000"));
			if (checkHash(tx.getHash())) {
				if (inUser(tx) == false) {
					inAdmin(tx);
				}
			}

		});

	}

	boolean checkHash(String hash) {
		TransactionDomain transactionDomain = transactionRepository.findByHash(hash);
		if (transactionDomain == null) {
			return true;
		}
		return false;
	}

	boolean inUser(Transaction t) {
		UserDomain user = userRepository.findByAddress(t.getTo());
		if (user != null) {
			user.setEther(new BigDecimal(user.getEther().toString()).add(new BigDecimal(t.getValue().toString()).divide(new BigDecimal("1000000000000000000"))));
			userRepository.save(user);
			return true;
		}
		return false;
	}

	void inAdmin(Transaction t) {
		AdminDomain adminDomain = adminRepository.findByAddress(t.getTo());
		if (adminDomain != null) {
			adminDomain.setEther(new BigDecimal(adminDomain.getEther().toString()).add(new BigDecimal(t.getValue().toString()).divide(new BigDecimal("1000000000000000000"))));
		}
	}

}
