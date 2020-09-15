import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  constructor(protected router: Router) {

  }

  public ngOnInit() {
  }

  ulogujSe() {
    this.router.navigateByUrl('/prijava');
  }

  registrujSe() {
    this.router.navigateByUrl('/registracija');
  }


}
