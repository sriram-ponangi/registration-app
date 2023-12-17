package com.example.registration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

@SpringBootTest
class RegistrationApplicationTests {

	@Test
	void contextLoads() {
		AssertionErrors.assertTrue("Unable to application context", true);
	}

}
