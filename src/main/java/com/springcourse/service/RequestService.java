package com.springcourse.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springcourse.domain.Request;
import com.springcourse.domain.enums.RequestState;
import com.springcourse.repository.RequestRepository;

@Service
public class RequestService {
	@Autowired private RequestRepository requestRepository;
	
	public Request save(Request request) {
		request.setState(RequestState.OPEN);
		request.setCreationDate(new Date());
		
		return requestRepository.save(request);
	}
	
	public Request update(Request request) {
		return requestRepository.save(request);
	}
	
	public Request findById(Long id) {
		return requestRepository.findById(id).get();
	}

	public List<Request> listAll(){
		return requestRepository.findAll();
	}
	
	public List<Request> listAllByOwnerId(Long id){
		return requestRepository.findAllByOwnerId(id);
	}
}
