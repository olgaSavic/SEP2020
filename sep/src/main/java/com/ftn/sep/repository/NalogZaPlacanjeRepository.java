package com.ftn.sep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sep.model.NalogZaPlacanje;

public interface NalogZaPlacanjeRepository extends JpaRepository<NalogZaPlacanje, Long>{
	
	NalogZaPlacanje findOneById(long id) ;
	NalogZaPlacanje findOneByIdPlacanja(String idPlacanja) ;

}
