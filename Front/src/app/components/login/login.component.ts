import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserModel} from "../../model/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../service/auth.service";
import {UserService} from "../../service/user.service";
import {TokenStorageService} from '../../service/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public form: FormGroup;

  public email: AbstractControl;
  public lozinka: AbstractControl;

  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';

  user : UserModel = new UserModel();

  constructor(private authService : AuthService, public fb: FormBuilder,
              private route: ActivatedRoute,
              private http: HttpClient, private userService: UserService,
              private router: Router,
              private tokenStorage: TokenStorageService) {
    this.form = this.fb.group({

      'email': ['', Validators.compose([Validators.required])],
      'lozinka': ['', Validators.compose([Validators.required])]
    })

    this.email = this.form.controls['email'];
    this.lozinka = this.form.controls['lozinka'];
  }

  ngOnInit() {
  }


  confirmClick(){

      this.user.email = this.email.value;
      this.user.lozinka = this.lozinka.value;
      this.authService.login2(this.user).subscribe(
        data => {

          this.tokenStorage.saveToken(data.accessToken);
          this.tokenStorage.saveUser(data);

          this.isLoginFailed = false;
          this.isLoggedIn = true;

          this.router.navigateByUrl('/izborCasopisa');
        },
        err => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      );

  }


}
