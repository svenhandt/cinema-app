import {SeatRowModel} from "./seat-row.model";

export class RoomModel {

  private _roomId: number | undefined;
  private _roomName: string | undefined;
  private _seatRows: SeatRowModel[] | undefined;


  set roomId(value: number | undefined) {
    this._roomId = value;
  }

  set roomName(value: string | undefined) {
    this._roomName = value;
  }

  set seatRows(value: SeatRowModel[] | undefined) {
    this._seatRows = value;
  }

  get roomId(): number | undefined {
    return this._roomId;
  }

  get roomName(): string | undefined {
    return this._roomName;
  }

  get seatRows(): SeatRowModel[] | undefined {
    return this._seatRows;
  }
}
