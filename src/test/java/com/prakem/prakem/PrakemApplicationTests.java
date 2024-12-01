package com.prakem.prakem;

import com.prakem.prakem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrakemApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private UserRepository userRepository;

	@Test
	void shouldReturnCorrectExistenceStatus() {
		String email = "test@example.com";
		boolean exists = userRepository.existsByEmail(email);

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:" + port + "/user/exists?email=" + email;

		ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);

		assertEquals(exists, response.getBody(), "API should return the same existence status as the repository.");
	}
}
