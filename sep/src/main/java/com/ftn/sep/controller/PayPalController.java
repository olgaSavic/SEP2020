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
	
	private static final String SUCCESS_URL = "http://localhost:4200/payment/success";
	private static final String CANCEL_URL = "http://localhost:4200/payment/cancel";
	private static final String ERROR_URL = "http://localhost:4200/payment/error";
	
	
	@PostMapping("createPayment")
	public String createPayment(@RequestBody ZahtevZaPlacanjeDTO pr) {
				
		try {
			Payment payment = paymentService.kreirajPlacanje(pr);
			
			for(Links link:payment.getLinks()) {
				System.out.println("LINK: " + link.getRel());
				System.out.println("LINK: " + link.getHref());
				if(link.getRel().equals("approval_url")) {	
					return link.getHref();
				}
			}			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return ERROR_URL;
	}
	
	@GetMapping(value = "/payment/complete/{id}")
    public RedirectView completePay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @PathVariable Long id){
		System.out.println("Usao u completePay metodu!");
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                return new RedirectView(SUCCESS_URL);
            }
        } catch (PayPalRESTException e) {
            System.out.print(e);
        }
        return new RedirectView(ERROR_URL);

    }
	
	@GetMapping(value = "/payment/cancel/{id}")
    public RedirectView cancelPayment(@PathVariable Long id) {
    	
    	paymentService.otkaziNalogZaPlacanje(id);
    	
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new RedirectView(CANCEL_URL);
    }

	

    
	

}
