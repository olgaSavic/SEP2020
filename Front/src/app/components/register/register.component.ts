import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from "@angular/common/http";
import {UserModel} from "../../model/user.model";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public message: string;

  ngOnInit(): void {
  }

  public form: FormGroup;

  public ime: AbstractControl;
  public prezime: AbstractControl;
  public email: AbstractControl;
  public lozinka: AbstractControl;

  korisnik: UserModel = new UserModel();

  constructor(private http: HttpClient, public fb: FormBuilder,
              private route: ActivatedRoute,
              private userService: UserService,
              private router: Router) {
    this.form = this.fb.group({
      'ime': ['', Validators.compose([Validators.required,  Validators.pattern('[A-Za-z]+$')])],
      'prezime': ['', Validators.compose([Validators.required,  Validators.pattern('[A-Za-z]+$')])],
      'email': ['', Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9.!#$%&\'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*.com$')])],
      'lozinka': ['', Validators.compose([Validators.required])]
    })

    this.ime = this.form.controls['ime'];
    this.prezime = this.form.controls['prezime'];
    this.email = this.form.controls['email'];
    this.lozinka = this.form.controls['lozinka'];
  }

  confirmClick() {
    this.korisnik.ime = this.ime.value;
    this.korisnik.prezime = this.prezime.value;
    this.korisnik.email = this.email.value;
    this.korisnik.lozinka = this.lozinka.value;

    this.userService.register(this.korisnik).subscribe(
        data => {
          this.router.navigateByUrl('/prijava');
        },
        error => {
          alert("Unet email vec postoji u sistemu!");

        });

  }


}
