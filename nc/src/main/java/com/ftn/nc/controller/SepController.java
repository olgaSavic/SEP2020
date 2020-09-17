package com.ftn.nc.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.ftn.nc.payload.request.NovoPlacanjeRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/nc")
public class SepController {
	
	@Autowired
	RestTemplate restTemplate;
    
	@PostMapping("/rutirajNaPaypal")
	public Map<String, String> rutirajNaPaypal(@RequestBody NovoPlacanjeRequest request) {
		
		ZahtevZaPlacanjeDTO dto = new ZahtevZaPlacanjeDTO();
		dto.setCena(request.getCena());
		dto.setIdProdavca(1);
		dto.setValuta("USD");
		ResponseEntity<String> response = restTemplate.postForEntity("https://localhost:8200/novoPlacanje", dto, String.class);
		HashMap<String, String> map = new HashMap<>();
        map.put("url", response.getBody());
        System.out.println(map.values());
        System.out.println(response.getBody());
        return map;
	}

}
