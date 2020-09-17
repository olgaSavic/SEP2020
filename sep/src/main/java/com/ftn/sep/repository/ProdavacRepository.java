package com.ftn.sep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sep.model.Prodavac;

public interface ProdavacRepository extends JpaRepository<Prodavac, Long> {
	
	public Prodavac findOneById(long id);

}
