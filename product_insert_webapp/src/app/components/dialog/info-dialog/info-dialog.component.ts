import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FileGeneratorCardComponent} from "../../file-generator-card/file-generator-card.component";

@Component({
  selector: 'app-info-dialog',
  templateUrl: './info-dialog.component.html',
  styleUrls: ['./info-dialog.component.scss']
})
export class InfoDialogComponent implements OnInit {

  withSQL = false;

  constructor(
    private dialogRef: MatDialogRef<FileGeneratorCardComponent>,
    @Inject(MAT_DIALOG_DATA) data: any) {
    this.withSQL = data.withSQL;
  }

  ngOnInit(): void {
  }

}
