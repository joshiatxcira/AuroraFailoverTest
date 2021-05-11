package com.example.rdsreconnectdemo.data.rw;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.example.rdsreconnectdemo.data.ro.TestMapperRO;

public interface TestMapperRW extends TestMapperRO {

	@Insert("insert into test(data) values (#{data})")
	public int createTestRow(String data);
	
	@Insert("delete from test")
	public int deleteAllFromTest();
	
	@Select("SELECT setval('public.test_id_seq', 1, true)")
	public long resetSequence();
}
