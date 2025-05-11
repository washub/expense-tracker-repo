package com.expense.ds_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
class DsServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void testHello(){
		var msg = "Hello World";
		Assertions.assertEquals("Hello World", msg);
	}

}
