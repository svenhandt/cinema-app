import {Component, OnDestroy, OnInit} from '@angular/core';
import {BookingService} from "../booking.service";
import {BookingModel} from "../models/booking.model";
import {Subscription} from "rxjs";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";

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
  backendValidationError = false;

  constructor(private bookingService: BookingService, private router: Router) { }

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
    if (!form.valid) {
      return;
    }
    const bookingCommand = this.createBookingCommand(form);
    this.bookingService.createBookingInBackend(bookingCommand).subscribe(
      (result: any) => {
        this.handleCreateBookingResult(result);
      }
    );
  }

  createBookingCommand(form: NgForm) {
    const bookingCommand = {
      presentationId: this.booking?.presentation?.id,
      roomId: this.booking?.presentation?.room?.roomId,
      seats: this.booking?.seats,
      cardName: form.value.cardName,
      cardNumber: form.value.cardNumber,
      expiryDate: {
        expiryMonth: form.value.expiryMonth,
        expiryYear: form.value.expiryYear
      },
      cvv: form.value.cvv
    };
    return bookingCommand;
  }

  handleCreateBookingResult(result: any) {
    if(result['createBookingStatus'] === 'FORM_ERROR') {
      this.backendValidationError = true;
    }
    else if(result['createBookingStatus'] === 'OK') {
      this.backendValidationError = false;
      this.bookingService.clearSessionBookings();
      const bookingId = result['bookingId']
      this.router.navigate(['/bookingConfirmation/' + bookingId]);
    }
  }

  ngOnDestroy() {
    this.bookingSubscription?.unsubscribe();
  }

}
