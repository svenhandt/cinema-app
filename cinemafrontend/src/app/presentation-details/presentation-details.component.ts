import {Component, OnDestroy, OnInit} from '@angular/core';
import {PresentationsService} from "../presentations.service";
import {BookingModel} from "../models/booking.model";
import {Subscription} from "rxjs";
import {ActivatedRoute, Params} from "@angular/router";

@Component({
  selector: 'app-presentation-details',
  templateUrl: './presentation-details.component.html',
  styleUrls: ['./presentation-details.component.css']
})
export class PresentationDetailsComponent implements OnInit, OnDestroy {

  id: number = -1;
  bookingForPresentationDetails: BookingModel | undefined;
  presentationDetailsSubscription: Subscription | undefined;

  constructor(private route: ActivatedRoute, private presentationsService: PresentationsService) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
      }
    );
    this.presentationDetailsSubscription = this.presentationsService.fetchPresentationDetails(this.id).subscribe( bookingForPresentationDetails => {
      this.bookingForPresentationDetails = bookingForPresentationDetails;
    });
  }

  ngOnDestroy() {
    this.presentationDetailsSubscription?.unsubscribe();
  }

}
