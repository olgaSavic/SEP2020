package com.ftn.nc.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.nc.model.Korisnik;
import com.ftn.nc.repository.KorisnikRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private KorisnikRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Korisnik user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User Not Found with email: " + email);
		}

		return UserDetailsImpl.build(user);
	}

}