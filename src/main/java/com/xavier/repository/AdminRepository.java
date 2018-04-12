package com.xavier.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xavier.domain.AdminDomain;

@Repository
public interface AdminRepository extends JpaRepository<AdminDomain, Serializable>{

	AdminDomain findByAddress(String to);

}