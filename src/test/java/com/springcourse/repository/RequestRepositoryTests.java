package com.springcourse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springcourse.domain.Request;
import com.springcourse.domain.User;
import com.springcourse.domain.enums.RequestState;
import com.springcourse.domain.enums.Role;

@SpringBootTest
public class RequestRepositoryTests {
	@Autowired private RequestRepository requestRepository;
	@Autowired private UserRepository userRepository;
	private int count = 0;
	
	private User newUser() {
		count++;
		String name = "user " + count;
		User user = new User(null, name, name + "@test.com", "123", Role.ADMINISTRATOR, null, null);
		return userRepository.save(user);
	}
	
	@AfterEach
	public void clean() {
		requestRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	
	@Test
	public void saveTest() {
		Request request = new Request(null, "Novo laptop", "Solicito novo laptop", new Date(), RequestState.OPEN, newUser(), null);
		requestRepository.save(request);
		
		assertThat(request.getId()).isNotNull();
	}
	
	@Test
	public void updateTest() {
		Request request = new Request(null, "Nova cadeira", "Solicito nova cadeira", new Date(), RequestState.OPEN, newUser(), null);
		
		request.setDescription("Solicito nova cadeira com braços");
		
		Request updatedRequest = requestRepository.save(request);
		
		assertThat(updatedRequest.getDescription()).isEqualTo("Solicito nova cadeira com braços");
	}
	
	@Test
	public void findByIdTest() {
		Request request = new Request(null, "Nova cadeira", "Solicito nova cadeira", new Date(), RequestState.OPEN, newUser(), null);
		requestRepository.save(request);
		
		Optional<Request> result = requestRepository.findById(request.getId());
		request = result.get();
		
		assertThat(request.getId()).isNotNull();
	}
	
	
	@Test
	public void listTest() {
		User owner = newUser();
		
		Request request = new Request(null, "Nova cadeira", "Solicito nova cadeira", new Date(), RequestState.OPEN, owner, null);
		requestRepository.save(request);
		
		request = new Request(null, "Novo monitor", "Solicito novo monitor", new Date(), RequestState.OPEN, owner, null);
		requestRepository.save(request);
		
		List<Request> requests = requestRepository.findAll();
		
		assertThat(requests.size()).isEqualTo(2);
	}
	
	public void listByOwnerIdTest() {
		User firstOwner = newUser();
		User secondOwner = new User();
		
		Request request = new Request(null, "Nova cadeira", "Solicito nova cadeira", new Date(), RequestState.OPEN, firstOwner, null);
		requestRepository.save(request);
		
		request = new Request(null, "Novo monitor", "Solicito novo monitor", new Date(), RequestState.OPEN, secondOwner, null);
		requestRepository.save(request);
		
		request = new Request(null, "Nova prateleira", "Solicito nova prateleira", new Date(), RequestState.OPEN, firstOwner, null);
		requestRepository.save(request);
		
		List<Request> requests = requestRepository.findAllByOwnerId(firstOwner.getId());
		
		assertThat(requests.size()).isEqualTo(2);
	}
	
	@Test
	public void updateStatusTest() {
		Request request = new Request(null, "Novo laptop", "Solicito novo laptop", new Date(), RequestState.OPEN, newUser(), null);
		request = requestRepository.save(request);
		
		int affectedRows = requestRepository.updateStatus(request.getId(), RequestState.IN_PROGRESS);
		
		assertThat(affectedRows).isEqualTo(1);
	}
}
