package com.springboot.samplerealtime.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.samplerealtime.project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
