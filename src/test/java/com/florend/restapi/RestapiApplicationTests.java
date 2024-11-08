package com.florend.restapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestContainersConfiguration.class)
@SpringBootTest
class RestapiApplicationTests {

	@Test
	void contextLoads() {
	}

}
