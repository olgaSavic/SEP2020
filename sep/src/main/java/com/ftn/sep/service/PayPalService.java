package com.ftn.sep.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sep.dto.ZahtevZaPlacanjeDTO;
import com.ftn.sep.model.NalogZaPlacanje;
import com.ftn.sep.model.PodaciOProdavcu;
import com.ftn.sep.model.StatusNalogaZaPlacanje;
import com.ftn.sep.repository.NalogZaPlacanjeRepository;
import com.ftn.sep.repository.PodaciOProdavcuRepository;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PayPalService {
	
	private static final String SUCCESS_URL = "/payment/complete";
	private static final String CANCEL_URL = "/payment/cancel";
	
	@Autowired
    private APIContext apiContext;
	
	@Autowired
	private PodaciOProdavcuRepository prodavacRepository;
	@Autowired
	private NalogZaPlacanjeRepository nalogRepository;
	
	public Payment kreirajPlacanje(ZahtevZaPlacanjeDTO paymentRequest) throws PayPalRESTException {
		
		PodaciOProdavcu prodavac = prodavacRepository.findOneById(paymentRequest.getIdProdavca());
		
		NalogZaPlacanje nalog = new NalogZaPlacanje();
		nalog.setProdavac(prodavac);
		nalog.setCena(nalog.getCena());
		nalogRepository.save(nalog);
		
		Amount amount = new Amount();
		amount.setCurrency("USD");
		double total = new BigDecimal(paymentRequest.getCena()).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

		Transaction transaction = new Transaction();
		transaction.setDescription("");
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("PAYPAL");
		
		Payment payment = new Payment();
		payment.setIntent("SALE");
		payment.setPayer(payer);  
		payment.setTransactions(transactions);
		
		String cancelUrl = "";
        String successUrl = "";
        successUrl = "https://localhost:8200/" + SUCCESS_URL + "/"+ nalog.getId();
        cancelUrl = "https://localhost:8200/" + CANCEL_URL + "/" + nalog.getId();
				
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
				
		payment = payment.create(apiContext);
		
		nalog.setIdPlacanja(payment.getId());
		nalog.setNamera(payment.getIntent());
		nalogRepository.save(nalog);
		
		return payment;
	}
	
	public Payment executePayment(String idPlacanja, String idKupca) throws PayPalRESTException {
				
		NalogZaPlacanje nalog = nalogRepository.findOneByIdPlacanja(idPlacanja);
		PodaciOProdavcu prodavac = nalog.getProdavac();
		
		Payment payment = new Payment();
		payment.setId(idPlacanja);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(idKupca);
		
		payment =  payment.execute(apiContext, paymentExecute);
		
		if(payment.getState().equals("approved")) {
			nalog.setStatus(StatusNalogaZaPlacanje.PLACEN);
		} else {
			nalog.setStatus(StatusNalogaZaPlacanje.NEUSPESAN);
		}
		
		nalog.setIdKupca(payment.getPayer().getPayerInfo().getPayerId());
		nalog.setNamera(payment.getIntent());
		nalogRepository.save(nalog);
		
		return payment;
	}

	
	public void otkaziNalogZaPlacanje(long id) {
		NalogZaPlacanje nalog = nalogRepository.findOneById(id);
		nalog.setStatus(StatusNalogaZaPlacanje.OTKAZAN);
		nalogRepository.save(nalog);
	}

	public Double getCenaNalogaZaPlacanje(String nalogId) {
		return nalogRepository.findOneByIdPlacanja(nalogId).getCena();
	}

}
