package com.ftn.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/nc")
public class TestController {
	
	@Autowired
	RestTemplate restTemplate;
		
	@RequestMapping("/testPaypal")
	public String testBitcoin() {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("https://localhost:8200/paypal/test", String.class);
		
		return "[PAYPAL] Naucna centrala funkcionise kako treba! Podaci koji su pristigli iz Paypal-a : " + response.getBody();
	}
	
	@RequestMapping("/test")
	public String test() {
	
		return "Test naucna centrala!";
	}

}
