package com.xavier.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xavier.domain.TransactionDomain;
@Repository
public interface TransactionRepository extends JpaRepository<TransactionDomain, Serializable> {

	TransactionDomain findByHash(String hash);

}
