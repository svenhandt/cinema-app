
export class FilmModel {

  private _id: number | undefined;
  private _title: string | undefined;


  get id(): number | undefined {
    return this._id;
  }

  set id(value: number | undefined) {
    this._id = value;
  }

  get title(): string | undefined {
    return this._title;
  }

  set title(value: string | undefined) {
    this._title = value;
  }
}
