package com.ftn.nc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.nc.model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

	Boolean existsByEmail(String email);

	@Query(value = "SELECT * FROM korisnik as u where u.email = :email", nativeQuery = true)
	Korisnik findByEmail(String email);

}
