import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {ZapocniPlacanjeModel} from '../model/zapocniPlacanje.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable({
  providedIn: 'root'
})
export class  PaymentService {

  private BASE_URL_NC = 'http://localhost:8080/api/nc'

  constructor(private http: HttpClient, private router: Router) { }

  zapocniPlacanje(z: ZapocniPlacanjeModel) {
    return this.http.post<ZapocniPlacanjeModel>(`${this.BASE_URL_NC}/zapocniPlacanje`, z);
  }
  
}
