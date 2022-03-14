import {Component, OnDestroy, OnInit} from '@angular/core';
import {BookingService} from "../booking.service";
import {ActivatedRoute, Params} from "@angular/router";
import {BookingModel} from "../models/booking.model";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-booking-confirmation',
  templateUrl: './booking-confirmation.component.html',
  styleUrls: ['./booking-confirmation.component.css']
})
export class BookingConfirmationComponent implements OnInit, OnDestroy {

  subscription: Subscription | undefined;
  booking: BookingModel | undefined;


  constructor(private bookingService: BookingService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    let bookingId = this.initRouteParams();
     this.subscription = this.bookingService.fetchBookingConfirmation(bookingId).subscribe(
       booking => {
         this.booking = booking;
       }
     );
  }

  initRouteParams() {
    let bookingId = 0;
    this.route.params.subscribe(
      (params: Params) => {
        bookingId = +params['id'];
      }
    );
    return bookingId;
  }

  ngOnDestroy() {
    if(this.subscription !== undefined) {
      this.subscription.unsubscribe();
    }
  }

}
