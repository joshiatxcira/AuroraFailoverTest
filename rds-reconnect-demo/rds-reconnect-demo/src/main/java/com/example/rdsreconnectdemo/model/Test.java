package com.example.rdsreconnectdemo.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class Test {

	@Getter @Setter
	private int id;
	
	@Getter @Setter
	private String data;
	
	@Getter @Setter
	private Timestamp creationDate;
}
