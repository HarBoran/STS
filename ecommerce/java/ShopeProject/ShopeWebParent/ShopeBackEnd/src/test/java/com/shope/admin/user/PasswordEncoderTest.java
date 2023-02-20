package com.shope.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	//@Test
	public void testEncoderd() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "itbank2023";

		String encodePassword = passwordEncoder.encode(rawPassword);
		System.out.println(encodePassword);

		boolean b = passwordEncoder.matches(rawPassword, encodePassword);
		assertThat(b).isTrue();
		System.out.println(b);
	
	}

	
}
