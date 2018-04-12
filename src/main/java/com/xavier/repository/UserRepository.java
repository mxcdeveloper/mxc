package com.xavier.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xavier.domain.UserDomain;

@Repository
public interface UserRepository extends JpaRepository<UserDomain, Serializable> {

	UserDomain findByAddress(String to);

}
 