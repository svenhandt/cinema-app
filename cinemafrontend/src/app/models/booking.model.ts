import {PresentationModel} from "./presentation.model";
import {SeatModel} from "./seat.model";

export class BookingModel {

  private _id: number | undefined;
  private _presentation: PresentationModel | undefined;
  private _totalPrice: number | undefined; // change depending on REST-answer
  private _seats: SeatModel[] | undefined;
  private _name: string | undefined;
  private _creditCardNo: string | undefined;


  get id(): number | undefined {
    return this._id;
  }

  set id(value: number | undefined) {
    this._id = value;
  }

  get presentation(): PresentationModel | undefined {
    return this._presentation;
  }

  set presentation(value: PresentationModel | undefined) {
    this._presentation = value;
  }

  get totalPrice(): number | undefined {
    return this._totalPrice;
  }

  set totalPrice(value: number | undefined) {
    this._totalPrice = value;
  }

  get seats(): SeatModel[] | undefined {
    return this._seats;
  }

  set seats(value: SeatModel[] | undefined) {
    this._seats = value;
  }

  get name(): string | undefined {
    return this._name;
  }

  set name(value: string | undefined) {
    this._name = value;
  }

  get creditCardNo(): string | undefined {
    return this._creditCardNo;
  }

  set creditCardNo(value: string | undefined) {
    this._creditCardNo = value;
  }
}
