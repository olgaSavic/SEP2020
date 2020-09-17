import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {ZapocniPlacanjeModel} from '../model/zapocniPlacanje.model';
import {DOCUMENT} from '@angular/common';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable({
  providedIn: 'root'
})
export class  PaymentService {

  private BASE_URL_NC = 'http://localhost:8080/api/nc'

  constructor(private http: HttpClient, @Inject(DOCUMENT) private document: Document, private router: Router) { }

  rutirajNaPaypal(z: ZapocniPlacanjeModel) {
    return this.http.post<ZapocniPlacanjeModel>(`${this.BASE_URL_NC}/rutirajNaPaypal`, z);
  }

  rutiraj2(z: ZapocniPlacanjeModel) {
    return this.http.post<ZapocniPlacanjeModel>(`${this.BASE_URL_NC}/rutirajNaPaypal`, z).subscribe(url => {

      this.document.location.href = url['url'];
      this.http.get(url['url']).subscribe(ret => {
        console.log('success');
      });
    }, error => {
      this.http.get(error).subscribe(value => {

        console.log('error');
      });
    });
  }
  
}
