package com.springcourse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springcourse.domain.User;
import com.springcourse.exception.NotFoundException;
import com.springcourse.model.PageModel;
import com.springcourse.model.PageRequestModel;
import com.springcourse.repository.UserRepository;
import com.springcourse.service.util.HashUtil;

@Service
public class UserService {
	@Autowired private UserRepository userRepository;
	
	public User save(User user) {
		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);
		
		return userRepository.save(user);
	}
	
	public User update(User user) {
		return userRepository.save(user);
	}
	
	public User findById(Long id) {
		Optional<User> result = userRepository.findById(id);
		
		return result.orElseThrow(() -> new NotFoundException("User not found with id = " + id));
	}
	
	public List<User> listAll(){
		return userRepository.findAll();
	}
	
	public PageModel<User> listAllOnLazyMode(PageRequestModel pr){
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<User> page = userRepository.findAll(pageable);
		
		return new PageModel<User>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
	}
	
	public User login(String email, String password) {
		password = HashUtil.getSecureHash(password);
		
		Optional<User> result = userRepository.login(email, password);
		return result.get();
	}
	
	public int updateRole(User user) {
		return userRepository.updateRole(user.getId(), user.getRole());
	}
}
