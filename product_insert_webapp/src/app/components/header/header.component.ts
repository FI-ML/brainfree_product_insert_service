import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {InfoDialogComponent} from "../dialog/info-dialog/info-dialog.component";
import {TemplateTO} from "../../model/templateTO";
import {LanguageService} from "../../services/language/language.service";
import {Language} from "../../model/language";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  withSQL = true;
  languages!: Language[];
  selectedLanguage!:Language;
  openMenu = false;


  constructor(public dialog: MatDialog,
              private readonly languageService: LanguageService) { }

  ngOnInit(): void {
    this.initLanguages();
  }

  setWithSQL():void {
    this.withSQL = !this.withSQL;
  }

  initLanguages():void {
   this.languages = this.languageService.languages;
   this.selectedLanguage = this.languages[0];
  }

  openOrCloeMenu():void {
    this.openMenu = !this.openMenu;
  }

  setSelectedLanguage(index:number):void{
    this.selectedLanguage = this.languages[index];
  }


}
