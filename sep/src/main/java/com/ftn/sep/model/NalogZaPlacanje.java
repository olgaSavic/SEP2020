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
	private Long id;
	
	private String idPlacanja;
	
	@ManyToOne
	private PodaciOProdavcu prodavac;
	
	private String idProdavca;
	private double iznos;
	private String metoda;
	private String namera;
	private String opis;
	
	//inicijalno status je 'KREIRAN'
	private StatusNalogaZaPlacanje status = StatusNalogaZaPlacanje.KREIRAN;
	

}
