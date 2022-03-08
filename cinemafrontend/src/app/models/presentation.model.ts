import {FilmModel} from "./film.model";
import {RoomModel} from "./room.model";

export class PresentationModel {

  private _id: number | undefined;
  private _dayOfWeek: string | undefined;
  private _startTime: string | undefined;
  private _film: FilmModel | undefined;
  private _room: RoomModel | undefined;
  private _price: number | undefined;


  get id(): number | undefined {
    return this._id;
  }

  set id(value: number | undefined) {
    this._id = value;
  }

  get dayOfWeek(): string | undefined {
    return this._dayOfWeek;
  }

  set dayOfWeek(value: string | undefined) {
    this._dayOfWeek = value;
  }

  get startTime(): string | undefined {
    return this._startTime;
  }

  set startTime(value: string | undefined) {
    this._startTime = value;
  }

  get film(): FilmModel | undefined {
    return this._film;
  }

  set film(value: FilmModel | undefined) {
    this._film = value;
  }

  get room(): RoomModel | undefined {
    return this._room;
  }

  set room(value: RoomModel | undefined) {
    this._room = value;
  }

  get price(): number | undefined {
    return this._price;
  }

  set price(value: number | undefined) {
    this._price = value;
  }
}
