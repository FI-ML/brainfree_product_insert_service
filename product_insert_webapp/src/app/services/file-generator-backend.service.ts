import {Injectable} from '@angular/core';
import {AppSettings} from "../app.settings";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {TemplateTO} from "../model/templateTO";
import {FileStatusTO} from "../model/fileStatusTO";

@Injectable({
  providedIn: 'root'
})
export class FileGeneratorBackendService {

  url = AppSettings.BASE_URL;

  constructor(private readonly http: HttpClient) {
  }

  createTemplate = (to: TemplateTO): Observable<FileStatusTO> => {
    return this.http.post<FileStatusTO>(this.url, to);
  }

  download = (withSql: boolean) =>{
    const downLoadUrl = `${this.url}download/${withSql}`;
    return this.http.get(downLoadUrl,{observe:'response',responseType:'blob'});
  }
}
