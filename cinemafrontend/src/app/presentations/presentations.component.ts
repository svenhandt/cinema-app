import {Component, OnDestroy, OnInit} from '@angular/core';
import {PresentationsService} from "../presentations.service";
import {FilmPresentationsMatrixModel} from "../models/film-presentations-matrix.model";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-presentations',
  templateUrl: './presentations.component.html',
  styleUrls: ['./presentations.component.css']
})
export class PresentationsComponent implements OnInit, OnDestroy {

  filmPresentationMatrixModels: FilmPresentationsMatrixModel[] = [];
  presentationsSubscription: Subscription | undefined;

  constructor(private presentationsService: PresentationsService, private router: Router) { }

  ngOnInit(): void {
    this.presentationsSubscription = this.presentationsService.fetchPresentations().subscribe(filmPresentationMatrixModels => {
      this.filmPresentationMatrixModels = filmPresentationMatrixModels;
    });
  }

  navigateToPresentation(presentationId: number | undefined) {
    if(presentationId != undefined) {
      this.router.navigate(['/presentationDetails/' + presentationId]);
    }
  }

  ngOnDestroy() {
    if(this.presentationsSubscription !== undefined) {
      this.presentationsSubscription.unsubscribe();
    }
  }

}
