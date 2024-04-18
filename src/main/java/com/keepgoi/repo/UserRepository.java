package com.keepgoi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keepgoi.model.User;

public interface UserRepository extends JpaRepository<User, String>{

	User findByMobileNumber(String username);

}
