package com.ftn.sep.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/paypal")
@RestController
public class TestController {
	
	@RequestMapping("/test")
	public String test() 
	{
	return "Sistem za placanje payPal-om radi odlicno!";
	}


}
