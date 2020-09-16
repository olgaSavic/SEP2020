package com.ftn.sep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sep.model.PodaciOProdavcu;

public interface PodaciOProdavcuRepository extends JpaRepository<PodaciOProdavcu, Long> {
	
	public PodaciOProdavcu findOneById(long id);

}
