package com.ftn.nc.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NovoPlacanjeRequest {
	
	@NotBlank
	private double cena;

	@NotBlank
	private String tipPlacanja;
	
	private String casopis ;

}
