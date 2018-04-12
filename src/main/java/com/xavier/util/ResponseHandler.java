package com.xavier.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

import com.xavier.constant.ResponseHandlerConstant;

public class ResponseHandler {
	public static Map<String,Object>generateResponse(String message,HttpStatus status,Boolean error,Object resObj){
		Map<String,Object> map=new HashMap<String, Object>();
		try{
			
		
		map.put(ResponseHandlerConstant.MESSAGE,message);
		map.put(ResponseHandlerConstant.STATUS, status);
		map.put(ResponseHandlerConstant.ERROR,error);
		map.put(ResponseHandlerConstant.DATA,resObj);
		map.put(ResponseHandlerConstant.TIME_STAMP, new Date());
		return map;
		}catch(Exception e){
			e.printStackTrace();
			map.clear();
			map.put(message, e.getMessage());
			map.put("Status", HttpStatus.INTERNAL_SERVER_ERROR);
			map.put("TimeStamp",new Date());
			return map;
		}
	}
}