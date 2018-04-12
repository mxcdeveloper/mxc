package com.xavier.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xavier.domain.TokenDomain;
@Repository
public interface TokenRepository extends JpaRepository<TokenDomain, Serializable>{

}
