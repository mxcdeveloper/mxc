package com.xavier;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.xavier.service.RecieveTransaction;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	RecieveTransaction recieveTransaction;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		try {
			recieveTransaction.recieveTransaction();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}
}
