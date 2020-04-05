package com.springcourse.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springcourse.domain.RequestStage;
import com.springcourse.domain.enums.RequestState;
import com.springcourse.repository.RequestRepository;
import com.springcourse.repository.RequestStageRepository;

@Service
public class RequestStageService {
	@Autowired private RequestStageRepository requestStageRepository;
	@Autowired private RequestRepository requestRepository;
	
	public RequestStage save(RequestStage stage) {
		stage.setRealizationDate(new Date());
		RequestStage createdStage = requestStageRepository.save(stage);
		
		Long requestId = stage.getRequest().getId();
		RequestState state = stage.getState();
		
		requestRepository.updateStatus(requestId, state);
		
		return createdStage;
	}
	
	public RequestStage findById(Long id) {
		return requestStageRepository.findById(id).get();
	}
	
	public List<RequestStage> listAllByRequestId(Long requestId){
		return requestStageRepository.findAllByRequestId(requestId);
	}
}
