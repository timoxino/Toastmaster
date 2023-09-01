package com.timoxino.interview.toastmaster;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = { "TOASTMASTER_EMAIL_FROM = test@gmail.com", "TOASTMASTER_EMAIL_TO = test@gmail.com" })
class ToastmasterApplicationTests {

	@Test
	void contextLoads() {
	}

}
