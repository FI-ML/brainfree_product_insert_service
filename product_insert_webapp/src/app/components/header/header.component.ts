import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {InfoDialogComponent} from "../dialog/info-dialog/info-dialog.component";
import {TemplateTO} from "../../model/templateTO";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  withSQL = true;

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  setWithSQL():void {
    this.withSQL = !this.withSQL;
  }

  openInfoDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      withSQL: this.withSQL
    };

    this.dialog.open(InfoDialogComponent, dialogConfig);
  }
}
