package com.ftn.sep.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NalogZaPlacanje {
	
	@Id
	@GeneratedValue()
	private long id;
	
	private String idPlacanja; // poslato sa paypal-a
	private String idKupca; // poslato sa paypal-a
	
	@ManyToOne
	private Prodavac prodavac;
	
	private double cena;
	//private String namera;
	
	//inicijalno status je 'KREIRAN'
	private StatusNalogaZaPlacanje status = StatusNalogaZaPlacanje.KREIRAN;


}
