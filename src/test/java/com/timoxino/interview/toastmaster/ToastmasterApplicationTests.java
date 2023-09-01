package com.timoxino.interview.toastmaster;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = { "toastmaster.email.from = test@gmail.com", "toastmaster.email.to = test@gmail.com" })
class ToastmasterApplicationTests {

	@Test
	void contextLoads() {
	}

}
