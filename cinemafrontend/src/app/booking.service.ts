import {Injectable} from '@angular/core';
import {BookingModel} from "./models/booking.model";
import {BehaviorSubject} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../environments/environment";
import {map} from "rxjs/operators";
import {PresentationModel} from "./models/presentation.model";
import {SeatModel} from "./models/seat.model";

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  sessionBookings: BookingModel[] = [];
  currentBookingSubject = new BehaviorSubject<BookingModel>(new BookingModel());

  constructor(private http: HttpClient) { }

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

  createBookingInBackend(bookingCommand: any) {
    return this.http.post(environment.cinemaBaseUrl + 'booking/save', bookingCommand);
  }

  fetchBookingConfirmation(id: number) {
    let params = new HttpParams();
    if(id !== undefined) {
      params = params.append('bookingId', id);
    }
    return this.http.get<any>(environment.cinemaBaseUrl + 'booking/confirmation', {
      params: params
    }).pipe(
      map(responseData => {
        return this.createBookingForConfirmation(responseData);
      })
    );
  }

  private createBookingForConfirmation(responseData: any) {
    let booking = new BookingModel();
    booking.id = responseData['id'];
    booking.name = responseData['name'];
    booking.creditCardNo = responseData['creditCardNo'];
    booking.totalPrice = responseData['totalPrice'];
    booking.presentation = this.createPresentationForBookingInConfirmation(responseData['presentationView']);
    booking.seats = this.createSeatsForBookingInConfirmation(responseData['seatsMap']);
    return booking;
  }

  private createPresentationForBookingInConfirmation(presentationView: any) {
    let presentation = new PresentationModel();
    presentation.id = presentationView['id'];
    presentation.dayOfWeek = presentationView['dayOfWeek'];
    presentation.price = presentationView['price'];
    presentation.startTime = presentationView['startTime'];
    presentation.film = presentationView['filmView'];
    presentation.room = presentationView['roomView'];
    return presentation;
  }

  private createSeatsForBookingInConfirmation(seatsMap: Map<number, any>) {
    let seatViews : any[] = Object.values(seatsMap);
    let seats: SeatModel[] = [];
    for(let seatView of seatViews) {
      let seat = new SeatModel();
      seat.id = seatView['id'];
      seat.seatRow = seatView['seatRow'];
      seat.numberInSeatRow = seatView['numberInSeatRow'];
      seats.push(seat);
    }
    console.log(seats);
    return seats;
  }


}
