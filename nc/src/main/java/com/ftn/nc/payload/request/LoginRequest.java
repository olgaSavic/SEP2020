package com.ftn.nc.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	@NotBlank
	private String email;

	@NotBlank
	private String lozinka;


}