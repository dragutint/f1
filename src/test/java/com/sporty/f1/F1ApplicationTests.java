package com.sporty.f1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class F1ApplicationTests {

	@Test
	void contextLoads() {
	}

}
