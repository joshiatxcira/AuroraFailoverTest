package com.example.rdsreconnectdemo.component;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rdsreconnectdemo.service.TestManager;

import lombok.Getter;

@Service
public class TestDataWriter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Getter
	private TestManager testManager;
	
	@PostConstruct
	public void init() {
		
		getTestManager().deleteAllFromTest();
		getTestManager().resetSequence();
		
		run();
	}
	
	public void run() {
		
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
		
		for(int i=1; i<4; i++) {
			
			final String threadName = "writer" + i;
			
			logger.info("Starting thread:" + threadName);
			
			executor.submit(() -> {

				int index = 1;
				Random random = new Random();
				
				while(true) {
					
					try {
					
						getTestManager().createTestRow(threadName + "-->" + index);
						
					} catch(Exception exception) {
						
						logger.error(exception.getMessage(), exception);
					}
					
				    Thread.sleep(random.nextInt(500));
				    
				    index ++;
				}
			});
		}
		
	}
}
