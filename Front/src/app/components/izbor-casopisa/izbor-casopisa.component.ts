import {Component, Inject, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PaymentService} from '../../service/payment.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DOCUMENT} from '@angular/common';
import {ZapocniPlacanjeModel} from '../../model/zapocniPlacanje.model';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-izbor-casopisa',
  templateUrl: './izbor-casopisa.component.html',
  styleUrls: ['./izbor-casopisa.component.css']
})
export class IzborCasopisaComponent implements OnInit {

  zapocniPlacanje: ZapocniPlacanjeModel = {
    cena : 0,
    tipPlacanja : "",
    casopis: ""
  }

  public form: FormGroup;

  public nazivCasopisa: AbstractControl;
  public cena: AbstractControl;
  public tipPlacanja: AbstractControl ;


  constructor(protected  router: Router,
              public fb: FormBuilder,
              private route: ActivatedRoute,
              private http: HttpClient,
              private paymentService: PaymentService, @Inject(DOCUMENT) private document: Document) {


    this.form = this.fb.group({
      'cena': ['', Validators.compose([Validators.required, Validators.pattern('^-?[0-9]{1,5}$')])],
      'nazivCasopisa': ['', Validators.compose([Validators.required])],
      'tipPlacanja': ['', Validators.compose([Validators.required])]
    })
    this.nazivCasopisa = this.form.controls['nazivCasopisa'];
    this.tipPlacanja =  this.form.controls['tipPlacanja'];
    this.cena = this.form.controls['cena'];

    this.form.controls['tipPlacanja'].setValue("PAYPAL");
    this.zapocniPlacanje.tipPlacanja = "PAYPAL";


  }

  ngOnInit() {
  }

  calculate() {
    if (this.nazivCasopisa.value == "") {
      this.zapocniPlacanje.cena = 0;
    }
    else if (this.nazivCasopisa.value == "Nacionalna geografija") {
      this.zapocniPlacanje.cena = 100;
    }
    else if (this.nazivCasopisa.value == "Sat") {
      this.zapocniPlacanje.cena = 200;
    }
    else if (this.nazivCasopisa.value == "Nin") {
      this.zapocniPlacanje.cena = 300;
    }
    else if (this.nazivCasopisa.value == "Magazin") {
      this.zapocniPlacanje.cena = 400;
    }
    else if (this.nazivCasopisa.value == "Neven") {
      this.zapocniPlacanje.cena = 10000;
    }

  }

  confirmClick() {
    const model = new ZapocniPlacanjeModel();
    model.cena = this.cena.value ;
    model.tipPlacanja = this.tipPlacanja.value ;
    model.casopis = this.nazivCasopisa.value;

    this.paymentService.rutirajNaPaypal(model).subscribe(url => {
      this.document.location.href = url['url'];

    })

  }

  exit() {
    this.router.navigateByUrl("/pocetna");

  }


}
