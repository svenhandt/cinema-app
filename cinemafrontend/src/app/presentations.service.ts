import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {FilmPresentationsMatrixModel} from "./models/film-presentations-matrix.model";
import {environment} from "../environments/environment";
import {map} from "rxjs/operators";
import {FilmModel} from "./models/film.model";
import {PresentationDayModel} from "./models/presentation-day.model";
import {BookingModel} from "./models/booking.model";
import {PresentationModel} from "./models/presentation.model";
import {RoomModel} from "./models/room.model";
import {SeatRowModel} from "./models/seat-row.model";
import {SeatModel} from "./models/seat.model";

@Injectable({
  providedIn: 'root'
})
export class PresentationsService {

  private _weekdays : string[] = ['Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa', 'So'];

  constructor(private http: HttpClient) { }

  fetchPresentations() {
    return this.http.get<any[]>(environment.cinemaBaseUrl + 'presentations')
      .pipe(
        map(responseData => {
          let result : FilmPresentationsMatrixModel[] = [];
          for(let i = 0; i < responseData.length; i++) {
            const filmPresentationMatrixRaw = responseData[i];
            this.populatePresentations(filmPresentationMatrixRaw, result);
          }
          return result;
        })
      );
  }

  private populatePresentations(filmPresentationMatrixRaw: any, result : FilmPresentationsMatrixModel[]) {
    const film: FilmModel = <FilmModel>filmPresentationMatrixRaw['filmView'];
    const presentationDays: PresentationDayModel[] = [];
    const weekdayPresentationMap = filmPresentationMatrixRaw['weekdayPresentationsMap'];
    for(let weekday of this._weekdays) {
      let presentationDayModel: PresentationDayModel = new PresentationDayModel(weekday, weekdayPresentationMap[weekday]);
      presentationDays.push(presentationDayModel);
    }
    result.push(new FilmPresentationsMatrixModel(film, presentationDays));
  }

  fetchPresentationDetails(id: number) {
    let params = new HttpParams();
    if(id !== undefined) {
      params = params.append('id', id);
    }
    return this.http.get<any>(environment.cinemaBaseUrl + 'presentationDetails', {
      params: params
    }).pipe(
      map(responseData => {
        return this.createBookingForDetails(responseData);
      })
    );
  }

  private createBookingForDetails(responseData: any): BookingModel {
    let booking: BookingModel = new BookingModel();
    booking.id = responseData['id'];
    booking.totalPrice = 0;
    booking.seats = [];
    booking.creditCardNo = '';
    booking.presentation = this.createPresentationForDetails(responseData['presentationView']);
    return booking;
  }

  private createPresentationForDetails(presentationView: any): PresentationModel {
    let presentationModel : PresentationModel = new PresentationModel();
    presentationModel.id = presentationView['id'];
    presentationModel.dayOfWeek = presentationView['dayOfWeek'];
    presentationModel.startTime = presentationView['startTime'];
    presentationModel.film = presentationView['filmView'];
    presentationModel.price = presentationView['price'];
    presentationModel.room = this.createRoomForDetails(presentationView['roomView'])
    return presentationModel;
  }

  private createRoomForDetails(roomView: any): RoomModel {
    let roomModel: RoomModel = new RoomModel();
    roomModel.roomId = roomView['roomId'];
    roomModel.roomName = roomView['roomName'];
    roomModel.seatRows = this.createSeatRowsForDetails(roomView['numberSeatRowMapping']);
    return roomModel;
  }

  private createSeatRowsForDetails(numberSeatRowMapping: any): SeatRowModel[] {
    let seatRowModels: SeatRowModel[] = [];
    let i = 1;
    while(numberSeatRowMapping.hasOwnProperty(i)) {
      let seatRowModel: SeatRowModel = new SeatRowModel(i, this.createSeatsForSeatRow(numberSeatRowMapping[i]));
      seatRowModels.push(seatRowModel);
      i++;
    }
    return seatRowModels;
  }

  private createSeatsForSeatRow(seatViewsObj: any): SeatModel[] {
    let seatModels: SeatModel[] = seatViewsObj['seatViews'];
    return seatModels;
  }

}
