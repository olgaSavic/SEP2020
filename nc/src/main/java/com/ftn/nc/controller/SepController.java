package com.ftn.nc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ftn.nc.dto.ZahtevZaPlacanjeDTO;
import com.ftn.nc.payload.request.ZapocniPlacanjeRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/nc")
public class SepController {
	
	@Autowired
	RestTemplate restTemplate;
    
	@PostMapping("/zapocniPlacanje")
	public ResponseEntity<String> beginPaymentProcess(@RequestBody ZapocniPlacanjeRequest request) {
		
		ZahtevZaPlacanjeDTO payingRequest = new ZahtevZaPlacanjeDTO(1, request.getCena());
		System.out.println("Cena iz requesta: " + request.getCena());
		ResponseEntity<String> response = restTemplate.postForEntity("https://localhost:8200/createPayment", payingRequest, String.class);
		
		return response;
	}

}
