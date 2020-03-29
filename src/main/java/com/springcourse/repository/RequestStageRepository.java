package com.springcourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springcourse.domain.RequestStage;

public interface RequestStageRepository extends JpaRepository<RequestStage, Long>{
	
	public List<RequestStage> findAllByRequest(Long id);
}
