package com.springcourse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springcourse.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT * FROM user WHERE email = ?1 AND password = ?2")
	public Optional<User> login(String email, String password);
}
