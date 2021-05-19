package com.springboot.samplerealtime.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.samplerealtime.project.model.User;
import com.springboot.samplerealtime.project.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository UserRepository;

	@Override
	public List<User> getAllUsers() {
		return UserRepository.findAll();
	}

	@Override
	public void saveUser(User user) {
		this.UserRepository.save(user);

	}

	@Override
	public User getUserById(long id) {
		Optional<User> optional = UserRepository.findById(id);
		User user = null;
		if (optional.isPresent()) {
			user = optional.get();
		} else {
			throw new RuntimeException("User not Found for id ::" + id);
		}
		return user;
	}

	@Override
	public void deleteUserById(long id) {
		this.UserRepository.deleteById(id);
	}

	public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.UserRepository.findAll(pageable);
	}

}
