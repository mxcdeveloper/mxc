package com.xavier.controller.user;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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
import com.xavier.domain.UserDomain;
import com.xavier.service.UserService;
import com.xavier.util.ResponseHandler;

@RestController
@RequestMapping(URLMapping.API_PATH)
public class UserController {

	private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;

	@RequestMapping(value = URLMapping.CREATEUSERWALLET, method = RequestMethod.GET)
	public Map<String, Object> createUserWallet() {
		Map map = null;
		try {
			map = userService.createUserWallet();
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException
				| CipherException | IOException | InterruptedException | ExecutionException e) {
			LOGGER.info("Error in create admin wallet .. "+e.getMessage()+map);
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, true, null);
		}
		LOGGER.info("User wallet created Successfully "+map);
		return ResponseHandler.generateResponse("User wallet created Successfully", HttpStatus.OK, false, map);
	}
	@RequestMapping(value = URLMapping.GETUSERBYADDRESS, method =RequestMethod.GET)
	public Map<String, Object> getUserByAddress(String address){
		System.out.println("address "+address);
		Map<String, Object> user = null;
		try {
			user = userService.getUserByAddress(address);
		}catch(NullPointerException e) {
			LOGGER.info("Invalid address "+user);
			return ResponseHandler.generateResponse("Invalid address", HttpStatus.OK, false, user);
		}
		if(user == null){
			LOGGER.info("There are no user present on this address "+user);
			return ResponseHandler.generateResponse("There are no user present on this address", HttpStatus.OK, false, user);
		}
		return ResponseHandler.generateResponse("User Details", HttpStatus.OK, false, user);
	}
	@RequestMapping(value = URLMapping.GETAllUSERDETAILS, method = RequestMethod.GET)
	public Map<String, Object> getAllUserDetails() {
		List<UserDomain> user = userService.getAllUserDetails();
		if (user.isEmpty()) {
			return ResponseHandler.generateResponse("There are no user ", HttpStatus.OK, false, user);
		}
		return ResponseHandler.generateResponse("All user list", HttpStatus.OK, false, user);
	}

}
