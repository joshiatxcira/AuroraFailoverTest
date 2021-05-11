package com.example.rdsreconnectdemo.component;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rdsreconnectdemo.model.Test;
import com.example.rdsreconnectdemo.service.TestManager;

import lombok.Getter;

@Service
public class TestDataReader {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Getter
	private TestManager testManager;
	
	@PostConstruct
	public void init() {
		
		run();
	}
	
	public void run() {
		
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
		
		for(int i=1; i<9; i++) {
			
			final String threadName = "reader" + i;
			
			logger.info("Starting thread:" + threadName);
			
			executor.submit(() -> {

				Random random = new Random();
				
				while(true) {
					
					try {
					
						Test test = getTestManager().getTestRow(random.nextInt(5000));
						
						if(test != null) {
							
							logger.info("found testRow - " + test.getId() + " - " + test.getData());
						}
						
					} catch(Exception exception) {
						
						logger.error(exception.getMessage(), exception);
					}
					
				    Thread.sleep(random.nextInt(500));
				}
			});
		}
		
	}
}
