import {PresentationModel} from "./presentation.model";

export class PresentationDayModel {

  private _weekday: string | undefined;
  private _presentations: PresentationModel[] | undefined;


  constructor(weekday: string | undefined, presentations: PresentationModel[] | undefined) {
    this._weekday = weekday;
    this._presentations = presentations;
  }


  get weekday(): string | undefined {
    return this._weekday;
  }

  get presentations(): PresentationModel[] | undefined {
    return this._presentations;
  }
}
