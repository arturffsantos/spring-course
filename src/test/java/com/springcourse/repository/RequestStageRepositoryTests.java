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
import com.springcourse.domain.RequestStage;
import com.springcourse.domain.User;
import com.springcourse.domain.enums.RequestState;
import com.springcourse.domain.enums.Role;

@SpringBootTest
public class RequestStageRepositoryTests {
	@Autowired private RequestStageRepository requestStageRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private RequestRepository requestRepository;
	private int countUser = 0;
	
	private User newUser() {
		countUser++;
		String name = "user " + countUser;
		User user = new User(null, name, name + "@test.com", "123", Role.ADMINISTRATOR, null, null);
		return userRepository.save(user);
	}
	
	private Request newRequest(User owner) {
		Request request = new Request(null, "Novo laptop", "Solicito novo laptop", new Date(), RequestState.OPEN, owner, null);
		return requestRepository.save(request);
	}
	
	private RequestStage newRequestStage(User owner, Request request) {
		RequestStage stage = new RequestStage(null, "Item foi comprado", new Date(), RequestState.CLOSED, request, owner);
		
		return requestStageRepository.save(stage);
	}
	
	private RequestStage newRequestStage() {
		User owner = newUser();
		Request request = newRequest(owner);
		
		return newRequestStage(owner, request);
	}
	
	@AfterEach
	public void clean() {
		requestStageRepository.deleteAll();
		requestRepository.deleteAll();
		userRepository.deleteAll();
		
	}
	
	@Test
	public void saveTest() {
		assertThat(newRequestStage().getId()).isNotNull();
	}
	
	@Test
	public void findByIdTest() {
		RequestStage stage = newRequestStage();
		Optional<RequestStage> result = requestStageRepository.findById(stage.getId());
		RequestStage foundedStage = result.get();
		
		assertThat(foundedStage.getId()).isEqualTo(stage.getId());
	}
	
	@Test
	public void listByRequestIdTest() {
		User owner = newUser();
		Request request =  newRequest(owner);
		requestStageRepository.save( newRequestStage(owner, request));
		requestStageRepository.save( newRequestStage(owner, request));
		
		Request otherRequest =  newRequest(owner);
		requestStageRepository.save( newRequestStage(owner, otherRequest));
		
		
		List<RequestStage> stages = requestStageRepository.findAllByRequestId(request.getId());
		
		assertThat(stages.size()).isEqualTo(2);
	}
}
