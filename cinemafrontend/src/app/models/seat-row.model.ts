import {SeatModel} from "./seat.model";

export class SeatRowModel {

  private _id: number | undefined;
  private _seats: SeatModel[] | undefined;

  constructor(id: number | undefined, seatViews: SeatModel[] | undefined) {
    this._id = id;
    this._seats = seatViews;
  }

  get id(): number | undefined {
    return this._id;
  }

  get seats(): SeatModel[] | undefined {
    return this._seats;
  }
}
