import {Injectable} from '@angular/core';
import {BookingModel} from "./models/booking.model";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  sessionBookings: BookingModel[] = [];
  currentBookingSubject = new BehaviorSubject<BookingModel>(new BookingModel());

  constructor() { }

  addToSessionBookings(booking: BookingModel) {
    this.sessionBookings.push(booking);
  }

  getSessionBookingForPresentationId(presentationId: number) {
    let result: BookingModel | undefined;
    for(let sessionBooking of this.sessionBookings) {
      if(presentationId === sessionBooking?.presentation?.id) {
        result = sessionBooking;
      }
    }
    return result;
  }

  clearSessionBookings() {
    this.sessionBookings.splice(0, this.sessionBookings.length);
  }

}
