

export class SeatModel {

  private _id: number | undefined;
  private _seatRow: number | undefined;
  private _numberInSeatRow: number | undefined;
  private _seatType: string | undefined;


  get id(): number | undefined {
    return this._id;
  }

  set id(value: number | undefined) {
    this._id = value;
  }

  get seatRow(): number | undefined {
    return this._seatRow;
  }

  set seatRow(value: number | undefined) {
    this._seatRow = value;
  }

  get numberInSeatRow(): number | undefined {
    return this._numberInSeatRow;
  }

  set numberInSeatRow(value: number | undefined) {
    this._numberInSeatRow = value;
  }


  get seatType(): string | undefined {
    return this._seatType;
  }

  set seatType(value: string | undefined) {
    this._seatType = value;
  }
}
