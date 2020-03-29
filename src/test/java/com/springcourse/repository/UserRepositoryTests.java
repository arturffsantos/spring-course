package com.springcourse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springcourse.domain.User;
import com.springcourse.domain.enums.Role;

@SpringBootTest
public class UserRepositoryTests {
	@Autowired private UserRepository userRepository;
	
	@AfterEach
	public void clean() {
		userRepository.deleteAll();
	}
	
	@Test
	public void saveTest() {
		User user = new User(null, "John", "john@test.com", "123", Role.ADMINISTRATOR, null, null);
		User createdUser = userRepository.save(user);
		
		assertThat(createdUser.getId()).isNotNull();
	}
	
	@Test
	public void updateTest() {
		User user = new User(null, "John", "john@test.com", "123", Role.ADMINISTRATOR, null, null);
		User createdUser = userRepository.save(user);
		
		createdUser.setName("John Doe");
		
		User updatedUser = userRepository.save(createdUser);
		
		assertThat(updatedUser.getName()).isEqualTo("John Doe");
	}
	
	@Test
	public void getByIdTest() {
		User user = new User(null, "John", "john@test.com", "123", Role.ADMINISTRATOR, null, null);
		User createdUser = userRepository.save(user);
		
		Optional<User> result = userRepository.findById(createdUser.getId());
		user = result.get();
		
		assertThat(user.getEmail()).isEqualTo("john@test.com");
	}
	
	
	@Test
	public void listTest() {
		User user = new User(null, "John", "john@test.com", "123", Role.ADMINISTRATOR, null, null);
		User createdUser = userRepository.save(user);
		Optional<User> result = userRepository.login("john@test.com", "123");
		User loggedUser = result.get();
		
		assertThat(loggedUser.getId()).isEqualTo(createdUser.getId());
	}
	
}
