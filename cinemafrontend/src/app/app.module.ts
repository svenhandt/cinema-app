import {LOCALE_ID, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PresentationsComponent } from './presentations/presentations.component';
import { PresentationDetailsComponent } from './presentation-details/presentation-details.component';
import { BookingDataFormComponent } from './booking-data-form/booking-data-form.component';
import { BookingConfirmationComponent } from './booking-confirmation/booking-confirmation.component';
import {HttpClientModule} from "@angular/common/http";
import {registerLocaleData} from "@angular/common";
import localeDe from '@angular/common/locales/de';
import localeDeExtra from '@angular/common/locales/extra/de';
import {FormsModule} from "@angular/forms";
import { ExpiryDateDirective } from './booking-data-form/expiry-date.directive';

registerLocaleData(localeDe, localeDeExtra);

@NgModule({
  declarations: [
    AppComponent,
    PresentationsComponent,
    PresentationDetailsComponent,
    BookingDataFormComponent,
    BookingConfirmationComponent,
    ExpiryDateDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    {provide: LOCALE_ID, useValue: 'de'}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
