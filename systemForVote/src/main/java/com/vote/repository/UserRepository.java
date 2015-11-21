package com.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vote.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	
}
