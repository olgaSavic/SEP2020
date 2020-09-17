import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from "@angular/forms";
import {HomepageComponent} from "./components/homepage/homepage.component";
import {RegisterComponent} from './components/register/register.component';
import {LoginComponent} from './components/login/login.component';
import {IzborCasopisaComponent} from './components/izbor-casopisa/izbor-casopisa.component';
import {SuccessComponent} from './components/success/success.component';
import {ErrorComponent} from './components/error/error.component';
import {CancelComponent} from './components/cancel/cancel.component';


const routes: Routes = [
  {path: '', component: HomepageComponent , pathMatch: 'full'},
  {path: 'pocetna', component: HomepageComponent },
  {path: 'prijava', component: LoginComponent },
  {path: 'registracija', component: RegisterComponent },
  {path: 'izborCasopisa', component: IzborCasopisaComponent },
  {path: 'placanje/uspesno', component: SuccessComponent },
  {path: 'placanje/greska', component: ErrorComponent },
  {path: 'placanje/otkazano', component: CancelComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
