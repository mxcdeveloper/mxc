package com.xavier.controller.admin;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;

import com.xavier.constant.URLMapping;
import com.xavier.controller.user.UserController;
import com.xavier.domain.AdminDomain;
import com.xavier.domain.TokenDomain;
import com.xavier.domain.TransactionDomain;
import com.xavier.repository.AdminRepository;
import com.xavier.repository.TokenRepository;
import com.xavier.service.AdminService;
import com.xavier.util.ResponseHandler;

@RestController
@RequestMapping(URLMapping.API_PATH)
public class AdminController {

	private static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	AdminService adminService;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	TokenRepository tokenRepository;

	@RequestMapping(value = URLMapping.CREATEADMINWALLET, method = RequestMethod.GET)
	public Map<String, Object> createAdminWallet()  {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AdminDomain> admin = adminRepository.findAll();
		Map map1 = null;
		if (!admin.isEmpty()) {
			for (AdminDomain adm : admin) {
				map.put("Address", adm.getAddress());
				map.put("Ether", adm.getEther());
			}
			LOGGER.info("Admin wallet already created "+map);
			return ResponseHandler.generateResponse("Admin wallet already created ", HttpStatus.OK, false, map);
		}
		try {
		map1 = adminService.createAdminWallet();
		}catch(NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | CipherException | IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			LOGGER.info("Admin wallet creation  "+e.getMessage()+" .. "+map);
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, true, null);
		}
		LOGGER.info("Admin wallet created Successfully "+map1);
		return ResponseHandler.generateResponse("Admin wallet created Successfully", HttpStatus.OK, false, map1);
	}

	@RequestMapping(value = URLMapping.CREATETOKEN, method = RequestMethod.GET)
	public Map<String, Object> createToken() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TokenDomain> token = tokenRepository.findAll();
		Map map1 = null;
		if (!token.isEmpty()) {
			for (TokenDomain tkn : token) {
				map.put("Contract Address", tkn.getContractAddress());
				map.put("Token Name", tkn.getName());
				map.put("Token Symbole", tkn.getSymbole());
				map.put("Admin Address", tkn.getAdminAddress());
			}
			LOGGER.info("Contract already created "+map);
			return ResponseHandler.generateResponse("Contracts already created ", HttpStatus.OK, false, map);
		}
		try {
			map1 = adminService.createToken();	
		}catch(IOException | CipherException | InterruptedException | ExecutionException e) {
			LOGGER.info("On admin wallet creation exception "+e.getMessage());
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, true, null);
		}
		LOGGER.info("Contract created Successfully "+map1);
		return ResponseHandler.generateResponse("Contacts created Successfully", HttpStatus.OK, false, map1);
	}

	@RequestMapping(value = URLMapping.SENDTOKENTOUSER, method = RequestMethod.GET)
	public Map<String, Object> sendTokenToUser(String Address, BigInteger Token) {
		LOGGER.info("Send token request on Address .."+Address +" Token "+Token);
		List<TokenDomain> token = tokenRepository.findAll();
		Map map =null;
		for (TokenDomain tkn : token) {
			int i = (tkn.getRemainingToken().compareTo(Token));
			if (i == -1)
				return ResponseHandler.generateResponse("Low Amount available in Token", HttpStatus.OK, false, null);
		}
		try {
			map = adminService.sendTokenToUser(Address, Token);	
		}catch(Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, true, map);
		}
		if (map != null) {
			LOGGER.info("Send Token successful "+map);
			return ResponseHandler.generateResponse("Send Successfull", HttpStatus.OK, false, map);
		}
		return ResponseHandler.generateResponse("unable to make transaction !", HttpStatus.OK, true, map);
	}

	@RequestMapping(value = URLMapping.GETTRANSACTIONDETAILS, method = RequestMethod.GET)
	public Map<String, Object> getTransacctionDetails() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TransactionDomain> transaction = adminService.getTransactionDetails();
		if (transaction.isEmpty()) {
			return ResponseHandler.generateResponse("There are no transaction yet ", HttpStatus.OK, false, transaction);
		}
		return ResponseHandler.generateResponse("Transaction Details", HttpStatus.OK, false, transaction);
	}

	@RequestMapping(value = URLMapping.GETAVAILABLETOKEN, method = RequestMethod.GET)
	public Map<String, Object> getAvailableToken() {
		Map map =null;
		try {
			map = adminService.getAvailableToken();	
		}catch(IOException | CipherException | InterruptedException | ExecutionException e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, true, null);
		}
		
		return ResponseHandler.generateResponse("Available token ", HttpStatus.OK, false, map);
	}
}
