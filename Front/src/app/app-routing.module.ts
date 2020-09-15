import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from "@angular/forms";
import {HomepageComponent} from "./components/homepage/homepage.component";
import {RegisterComponent} from './components/register/register.component';
import {LoginComponent} from './components/login/login.component';


const routes: Routes = [
  {path: '', component: HomepageComponent , pathMatch: 'full'},
  {path: 'pocetna', component: HomepageComponent },
  {path: 'prijava', component: LoginComponent },
  {path: 'registracija', component: RegisterComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
