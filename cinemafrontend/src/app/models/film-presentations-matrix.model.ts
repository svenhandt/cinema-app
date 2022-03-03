import {FilmModel} from "./film.model";
import {PresentationDayModel} from "./presentation-day.model";


export class FilmPresentationsMatrixModel {

  private _film: FilmModel | undefined;
  private _presentationDays: PresentationDayModel[] | undefined;


  constructor(film: FilmModel | undefined, presentationDays: PresentationDayModel[] | undefined) {
    this._film = film;
    this._presentationDays = presentationDays;
  }


  get film(): FilmModel | undefined {
    return this._film;
  }

  get presentationDays(): PresentationDayModel[] | undefined {
    return this._presentationDays;
  }
}
