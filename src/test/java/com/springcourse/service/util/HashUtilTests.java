package com.springcourse.service.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HashUtilTests {

	@Test
	public void getSecureHashTest() {
		String hash = HashUtil.getSecureHash("123");
		
		assertThat(hash.length()).isEqualTo(64);
	}
}
