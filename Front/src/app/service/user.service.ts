import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {UserModel} from "../model/user.model";


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class UserService {
  private BASE_URL = 'http://localhost:8080/api/korisnici';

  constructor(private http: HttpClient) {

  }

  register(k: UserModel) {
    return this.http.post<UserModel>(`${this.BASE_URL}/registracija`, k);
  }



}
