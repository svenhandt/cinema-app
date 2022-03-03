import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PresentationsComponent } from './presentations/presentations.component';
import { PresentationDetailsComponent } from './presentation-details/presentation-details.component';
import { BookingDataFormComponent } from './booking-data-form/booking-data-form.component';
import { BookingConfirmationComponent } from './booking-confirmation/booking-confirmation.component';
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    PresentationsComponent,
    PresentationDetailsComponent,
    BookingDataFormComponent,
    BookingConfirmationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
