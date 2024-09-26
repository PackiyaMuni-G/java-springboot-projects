package com.springboot.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.crud.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
