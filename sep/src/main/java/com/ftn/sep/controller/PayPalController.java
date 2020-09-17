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
	
	
	@PostMapping("novoPlacanje")
	public String novoPlacanje(@RequestBody ZahtevZaPlacanjeDTO pr) {	
		try {
			Payment payment = paymentService.novoPlacanje(pr);
			
			for(Links link:payment.getLinks()) {
				System.out.println("LINK: " + link.getRel());
				System.out.println("LINK: " + link.getHref());
				if(link.getRel().equals("approval_url")) {	
					return link.getHref(); // u slucaju da je uspesno placanje, posalje kupca na sandbox stranicu za placanje
				}
			}			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		//throw new com.ftn.sep.exception.BadRequestException("Neuspelo placanje!");
		return URL_GRESKA;
	}
	
	@GetMapping(value = "/placanje/uspesno/{id}")
    public RedirectView completePay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @PathVariable Long id){
        try {
            Payment payment = paymentService.plati(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                return new RedirectView(URL_USPESNO);
            }
        } catch (PayPalRESTException e) {
        	e.printStackTrace();
        }
        return new RedirectView(URL_GRESKA);

    }
	
	@GetMapping(value = "/placanje/otkazano/{id}")
    public RedirectView cancelPayment(@PathVariable Long id) {   	
    	paymentService.otkaziNalogZaPlacanje(id);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new RedirectView(URL_OTKAZANO);
    }

	

    
	

}
