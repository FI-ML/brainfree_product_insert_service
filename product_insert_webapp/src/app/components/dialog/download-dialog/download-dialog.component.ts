import {Component, Inject, OnInit} from '@angular/core';
import {FileGeneratorBackendService} from "../../../services/file-generator-backend.service";
import {FileGeneratorCardComponent} from "../../file-generator-card/file-generator-card.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FileStatusTO} from "../../../model/fileStatusTO";
import {TemplateTO} from "../../../model/templateTO";

@Component({
  selector: 'app-download-dialog',
  templateUrl: './download-dialog.component.html',
  styleUrls: ['./download-dialog.component.scss']
})
export class DownloadDialogComponent implements OnInit {

  fileStatus!: FileStatusTO;
  fileTemplate!: TemplateTO;

  constructor(
    private readonly backendService: FileGeneratorBackendService,
    private dialogRef: MatDialogRef<FileGeneratorCardComponent>,
    @Inject(MAT_DIALOG_DATA) data: any) {
    this.fileTemplate = data.fileTemplate;
  }


  ngOnInit(): void {
    this.createFile();
  }


  createFile(): void {
    if (this.fileTemplate) {
      this.backendService.createTemplate(this.fileTemplate).subscribe((status) => {
        this.fileStatus = status;
      })
    }
    setTimeout(() => {
      this.downLoadFile()
    }, 5000);
  }


  private downLoadFile(): void {
    if (!this.checkIsFileCreated()) {
      setTimeout(() => {
        this.downLoadFile()
      }, 2000);
    }else{
      this.backendService.download(this.fileTemplate.withSQL).subscribe(response => {
        let blob: Blob = response.body as Blob;
        let a = document.createElement('a');

        let fileType = this.fileTemplate.withSQL ? '.sql' : '.xml';

        a.download = 'insert-product' + fileType;
        a.href = window.URL.createObjectURL(blob);
        a.click();

        this.dialogRef.close();
      })
    }
  }

  private checkIsFileCreated() {
    return this.fileStatus.status === 'SQL FILE WAS CREATED' || this.fileStatus.status === 'XML FILE WAS CREATED';
  }

}
