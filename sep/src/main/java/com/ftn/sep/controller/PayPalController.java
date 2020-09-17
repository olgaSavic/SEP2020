package com.ftn.sep.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ftn.sep.dto.ZahtevZaPlacanjeDTO;
import com.ftn.sep.service.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController("/")
public class PayPalController {
	
	@Autowired
	private PayPalService paymentService;
	
	private static final String URL_USPESNO = "http://localhost:4200/placanje/uspesno";
	private static final String URL_OTKAZANO = "http://localhost:4200/placanje/otkazano";
	private static final String URL_GRESKA = "http://localhost:4200/placanje/greska";
	
	// KORAK 1 -> Create a Payment
	@PostMapping("novoPlacanje")
	public String novoPlacanje(@RequestBody ZahtevZaPlacanjeDTO pr) {	
		try {
			Payment payment = paymentService.novoPlacanje(pr);
			
			// postoje 3 linka kod placanja - self, approval i execute
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {	
					// KORAK 2 -> Approve the Payment
					return link.getHref(); // u slucaju da je uspesno placanje, posalje kupca na sandbox stranicu za placanje
					// korisnik se prebaci na stranicu gde vidi informacije o placanju i treba da ga odobri (ili otkaze)
					// ovde se vrsi autentifikacija korisnika
				}
			}			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return URL_GRESKA;
	}
	
	// KORAK 3 -> Handle Approval Callback
	// PayerID i paymentId su parametri zahteva postavljeni od strane PayPal-a
	@GetMapping(value = "/placanje/uspesno/{id}")
    public RedirectView completePay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @PathVariable Long id){
        try {
        	// KORAK 4 -> Execute The Payment
        	// Salje se zahtev od strane aplikacije ka PayPal-u, koristeci PayerID i paymentId - definise ko placa i koliko
            Payment payment = paymentService.plati(paymentId, payerId);
            if (payment.getState().equals("approved")) { // ukoliko ima successful response, placanje se smatra uspesnim
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                return new RedirectView(URL_USPESNO); // redirekcija korisnika na stranicu za uspesno placanje
            }
        } catch (PayPalRESTException e) {
        	e.printStackTrace();
        }
        return new RedirectView(URL_GRESKA); // redirekcija korisnika na stranicu za neuspesno placanje

    }
	
	// KORAK 3 -> Handle Approval Callback
	@GetMapping(value = "/placanje/otkazano/{id}")
    public RedirectView cancelPayment(@PathVariable Long id) {   	
    	paymentService.otkaziNalogZaPlacanje(id);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new RedirectView(URL_OTKAZANO); // redirekcija korisnika na stranicu za otkaz
    }

	

    
	

}
