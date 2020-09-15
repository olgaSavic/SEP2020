package com.ftn.nc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ftn.nc.dto.KorisnikDTO;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Korisnik {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String ime ;
	private String prezime ; 
	private String email ;
	private String lozinka ;
	
	public Korisnik(KorisnikDTO dto) {
		this.email = dto.getEmail();
		this.lozinka = dto.getLozinka();
		this.ime = dto.getIme();
		this.prezime = dto.getPrezime();
	}


}
