import {Component, OnDestroy, OnInit} from '@angular/core';
import {BookingService} from "../booking.service";
import {BookingModel} from "../models/booking.model";
import {Subscription} from "rxjs";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-booking-data-form',
  templateUrl: './booking-data-form.component.html',
  styleUrls: ['./booking-data-form.component.css']
})
export class BookingDataFormComponent implements OnInit, OnDestroy {

  booking: BookingModel | undefined;
  bookingSubscription: Subscription | undefined;
  expiryMonths = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
  expiryYears: string[] = [];

  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.bookingSubscription = this.bookingService.currentBookingSubject.subscribe((booking: BookingModel) => {
      this.booking = booking;
    });
    this.initExpiryYears();
  }

  getNameForReservedSeats() {
    let result = '';
    let seatsCount = this.booking?.seats?.length;
    if(seatsCount !== undefined && seatsCount > 1) {
      result = seatsCount + ' Plätze';
    }
    else if(seatsCount !== undefined && seatsCount === 1) {
      result = '1 Platz';
    }
    return result;
  }

  private initExpiryYears() {
    let currentYear = new Date().getFullYear();
    for(let i = currentYear; i < (currentYear + 10); i++) {
      this.expiryYears.push(i.toString());
    }
  }

  onSubmitBookingForm(form: NgForm) {

  }

  ngOnDestroy() {
    this.bookingSubscription?.unsubscribe();
  }

}
