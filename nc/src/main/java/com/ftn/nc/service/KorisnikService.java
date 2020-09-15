package com.ftn.nc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftn.nc.exception.BadRequestException;
import com.ftn.nc.model.Korisnik;
import com.ftn.nc.repository.KorisnikRepository;

@Service
public class KorisnikService {
	
	@Autowired
	private KorisnikRepository korisnikRepository ;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public boolean existsByEmail(String email) {
		return korisnikRepository.existsByEmail(email);
	}
	
	public Korisnik save(Korisnik user) {
		return korisnikRepository.save(user);
	}
	
	public Korisnik registracija(Korisnik u) {
		if (existsByEmail(u.getEmail())) {
			throw new BadRequestException("Korisnik sa tim email-om vec postoji.");
		}
		u.setLozinka(encoder.encode(u.getLozinka()));
		save(u);
		return u;
	}

}
