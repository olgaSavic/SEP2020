package com.ftn.nc.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.dto.KorisnikDTO;
import com.ftn.nc.exception.BadRequestException;
import com.ftn.nc.model.Korisnik;
import com.ftn.nc.service.KorisnikService;


@RestController
@RequestMapping("/api/korisnici")
public class KorisnikController {
	
	@Autowired
	private KorisnikService korisnikService ;
	
	@Autowired
	private ModelMapper modelMapper ;
	
	@PostMapping("/registracija")
	public ResponseEntity<KorisnikDTO> registracija(@RequestBody @Valid KorisnikDTO userDTO, BindingResult result) {
		if(result.hasErrors()) {
			throw new BadRequestException("Neispravan unos.");
        }
		Korisnik user = korisnikService.registracija(new Korisnik(userDTO));
		KorisnikDTO createdUserDTO = modelMapper.map(user, KorisnikDTO.class);
		return new ResponseEntity<KorisnikDTO>(createdUserDTO, HttpStatus.CREATED);	// code 201	
	}

}
