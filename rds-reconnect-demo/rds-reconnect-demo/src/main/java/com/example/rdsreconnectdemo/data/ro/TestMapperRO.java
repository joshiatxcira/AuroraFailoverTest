package com.example.rdsreconnectdemo.data.ro;

import org.apache.ibatis.annotations.Select;

import com.example.rdsreconnectdemo.model.Test;

public interface TestMapperRO {

	@Select("SELECT id, data, creation_date FROM test WHERE id = #{id}")
	public Test getTestRow(int id);
	
}
