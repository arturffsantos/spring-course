package com.springcourse.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.springcourse.domain.Request;
import com.springcourse.domain.enums.RequestState;

public interface RequestRepository extends JpaRepository<Request, Long>{
	
	public List<Request> findAllByOwnerId(Long id);
	
	@Transactional
	@Modifying
	@Query("UPDATE request SET state = ?2 WHERE id = ?1")
	public int updateStatus(Long id, RequestState state);
}
