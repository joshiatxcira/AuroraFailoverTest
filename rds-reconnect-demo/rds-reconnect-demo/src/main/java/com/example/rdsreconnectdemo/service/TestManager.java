package com.example.rdsreconnectdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.rdsreconnectdemo.data.ro.TestMapperRO;
import com.example.rdsreconnectdemo.data.rw.TestMapperRW;
import com.example.rdsreconnectdemo.model.Test;

@Component
public class TestManager {

	@Autowired
	private TestMapperRO testMapperRO;
	
	@Autowired
	private TestMapperRW testMapperRW;
	
	public int createTestRow(String data) {
		
		return testMapperRW.createTestRow(data);
	}
	
	public int deleteAllFromTest() {
		
		return testMapperRW.deleteAllFromTest();
	}
	
	public Test getTestRow(int id) {
		
		return testMapperRO.getTestRow(id);
	}
	
	public void resetSequence() {
		
		testMapperRW.resetSequence();
	}
}
