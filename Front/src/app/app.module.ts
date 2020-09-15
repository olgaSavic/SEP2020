import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgxPaginationModule} from "ngx-pagination";

import { MatTabsModule } from '@angular/material/tabs';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import {AuthInterceptor} from "./http-interceptor/AuthInterceptor";
import {
  IgxTabsModule,
  IgxNavbarModule,
  IgxIconModule,
  IgxButtonGroupModule,
  IgxAvatarModule,
  IgxCheckboxModule,
  IgxDatePickerModule
} from 'igniteui-angular';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AgmCoreModule } from '@agm/core';


import {MatCheckboxModule, MatDatepickerModule, MatGridListModule} from '@angular/material';
import {MatTableModule} from '@angular/material';

import { HomepageComponent } from './components/homepage/homepage.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import {UserService} from "./service/user.service";
import {AuthService} from "./service/auth.service";

import {AuthGuard} from "./guards/AuthGuard";
import {RandomGuard} from "./guards/RandomGuard";

@NgModule({
  declarations: [
    AppComponent,

    HomepageComponent,

    RegisterComponent,

    LoginComponent,


  ],
  imports: [
    BrowserModule,
    NgxPaginationModule,
    AppRoutingModule,
    RouterModule,
    NgbModule,
    CommonModule,
    MatTabsModule,
    BrowserAnimationsModule,
    FormsModule,
    IgxTabsModule,
    IgxNavbarModule,
    IgxIconModule,
    IgxButtonGroupModule,
    ReactiveFormsModule,
    HttpClientModule,
    IgxAvatarModule,
    MatGridListModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyAXwTtS46pzFqyqxA9lzUMkpmyBsVtkGHs',
      libraries: ['places']
    }),
    MatTableModule,
    MatCheckboxModule,
    IgxCheckboxModule,
    IgxDatePickerModule,
    MatDatepickerModule,
  ],
  providers: [
    UserService,
    AuthService,
    AuthGuard,
    RandomGuard,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  entryComponents: [],
  bootstrap: [AppComponent],
})
export class AppModule {
}
