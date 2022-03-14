import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PresentationsComponent} from "./presentations/presentations.component";
import {PresentationDetailsComponent} from "./presentation-details/presentation-details.component";
import {BookingDataFormComponent} from "./booking-data-form/booking-data-form.component";
import {BookingConfirmationComponent} from "./booking-confirmation/booking-confirmation.component";

const routes: Routes = [
  {path: '', redirectTo: '/presentations', pathMatch: 'full'},
  {path: 'presentations', component: PresentationsComponent},
  {path: 'presentationDetails/:id', component: PresentationDetailsComponent},
  {path: 'bookingDataForm', component: BookingDataFormComponent},
  {path: 'bookingConfirmation/:id', component: BookingConfirmationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
