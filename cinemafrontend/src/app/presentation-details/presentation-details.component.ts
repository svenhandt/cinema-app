import {Component, OnDestroy, OnInit} from '@angular/core';
import {PresentationsService} from "../presentations.service";
import {BookingModel} from "../models/booking.model";
import {Subscription} from "rxjs";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {SeatModel} from "../models/seat.model";
import {BookingService} from "../booking.service";

@Component({
  selector: 'app-presentation-details',
  templateUrl: './presentation-details.component.html',
  styleUrls: ['./presentation-details.component.css']
})
export class PresentationDetailsComponent implements OnInit, OnDestroy {

  id: number = -1;
  bookingForPresentationDetails: BookingModel | undefined;
  presentationDetailsSubscription: Subscription | undefined;

  constructor(private route: ActivatedRoute,
              private presentationsService: PresentationsService, private bookingService: BookingService, private router: Router) { }

  ngOnInit(): void {
    this.initRouteParams();
    let initedFromSessionStorage = this.initFromSessionBookingsStorage();
    if(!initedFromSessionStorage) {
      this.initFromHttp();
    }
  }

  initRouteParams() {
    this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
      }
    );
  }

  initFromSessionBookingsStorage(): boolean {
    let initedFromStorage = false;
    this.bookingForPresentationDetails = this.bookingService.getSessionBookingForPresentationId(this.id);
    if(this.bookingForPresentationDetails !== undefined) {
      initedFromStorage = true;
    }
    return initedFromStorage;
  }

  initFromHttp() {
    this.presentationDetailsSubscription = this.presentationsService.fetchPresentationDetails(this.id).subscribe( bookingForPresentationDetails => {
      this.bookingForPresentationDetails = bookingForPresentationDetails;
      this.bookingService.addToSessionBookings(bookingForPresentationDetails);
    });
  }

  onAddSeatToBooking(seat: SeatModel) {
    this.bookingForPresentationDetails?.seats?.push(seat);
    this.adjustTotalPrice('ADD');
  }

  isInBooking(seatToCheck: SeatModel): boolean {
    let result = false;
    this.bookingForPresentationDetails?.seats?.forEach((currentSeat, index) => {
      if(seatToCheck.id === currentSeat.id) {
        result = true;
      }
    });
    return result;
  }

  onRemoveSeatFromBooking(seatToDelete: SeatModel) {
    this.bookingForPresentationDetails?.seats?.forEach((currentSeat, index) =>{
      if(seatToDelete.id === currentSeat.id) {
        this.bookingForPresentationDetails?.seats?.splice(index, 1);
        this.adjustTotalPrice('SUBTRACT');
      }
    });
  }

  adjustTotalPrice(operation: string) {
    if(this.bookingForPresentationDetails?.totalPrice !== undefined && this.bookingForPresentationDetails?.presentation?.price !== undefined) {
      if('ADD' === operation) {
        this.bookingForPresentationDetails.totalPrice = this.bookingForPresentationDetails.totalPrice + this.bookingForPresentationDetails.presentation.price;
      }
      else if('SUBTRACT' === operation) {
        this.bookingForPresentationDetails.totalPrice = this.bookingForPresentationDetails.totalPrice - this.bookingForPresentationDetails.presentation.price;
      }
    }
  }

  onInitiateBooking() {
    if(this.bookingForPresentationDetails !== undefined) {
      this.bookingService.currentBookingSubject.next(this.bookingForPresentationDetails);
      this.router.navigate(['/bookingDataForm']);
    }
  }

  ngOnDestroy() {
    this.presentationDetailsSubscription?.unsubscribe();
  }

}
