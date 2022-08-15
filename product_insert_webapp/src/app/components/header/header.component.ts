import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {LanguageService} from "../../services/language/language.service";
import {Language} from "../../model/language";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  withSQL = true;
  languages!: Language[];
  selectedLanguage!: Language;
  openMenu = false;
  isGermany: boolean = true;

  constructor
  (
    public dialog: MatDialog,
    private readonly translate: TranslateService,
    private readonly languageService: LanguageService,
  ) {
    translate.setDefaultLang('de');
  }

  ngOnInit(): void {
    this.initLanguages();
  }

  setWithSQL(): void {
    this.withSQL = !this.withSQL;
  }

  initLanguages(): void {
    this.languages = this.languageService.languages;
    this.selectedLanguage = this.languages[0];
  }

  openOrCloseMenu(): void {
    this.openMenu = !this.openMenu;
  }

  onLanguageChange(index: number): void {
    this.selectedLanguage = this.languages[index];
    this.translate.use(this.selectedLanguage.abbreviation.toLowerCase());

    this.isGermany = this.selectedLanguage.abbreviation === 'de';
  }


}
