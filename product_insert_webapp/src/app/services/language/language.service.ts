import {Injectable} from '@angular/core';
import {Language} from "../../model/language";

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  constructor() {
  }

  public get languages(): Language[] {
    return [
      {value: 'Deutsch', abbreviation: 'DE'},
      {value: 'English', abbreviation: 'EN'}
    ];
  }
}
