package com.springcourse.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springcourse.domain.Request;
import com.springcourse.domain.RequestStage;
import com.springcourse.domain.enums.RequestState;
import com.springcourse.exception.NotFoundException;
import com.springcourse.model.PageModel;
import com.springcourse.model.PageRequestModel;
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
		Optional<RequestStage> result = requestStageRepository.findById(id);
		
		return result.orElseThrow(() -> new NotFoundException("Stage not found with id = " + id));
	}
	
	public List<RequestStage> listAllByRequestId(Long requestId){
		return requestStageRepository.findAllByRequestId(requestId);
	}
	
	public PageModel<RequestStage> llistAllByRequestIddOnLazyMode(Long requestId, PageRequestModel pr){
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<RequestStage> page = requestStageRepository.findAllByRequestId(requestId, pageable);
		
		return new PageModel<RequestStage>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
	}
}
