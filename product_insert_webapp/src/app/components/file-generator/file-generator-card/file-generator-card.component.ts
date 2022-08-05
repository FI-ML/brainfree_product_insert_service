import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {InfoDialogComponent} from "../../dialog/info-dialog/info-dialog.component";
import {DownloadDialogComponent} from "../../dialog/download-dialog/download-dialog.component";
import {FileGeneratorBackendService} from "../../../services/file-generator-backend.service";
import {CategoryService} from "../../../services/category.service";
import {map, Observable, startWith} from "rxjs";
import {FileStatusTO} from "../../../model/fileStatusTO";
import {TemplateValueTOS} from "../../../model/templateValueTOS";
import {TemplateTO} from "../../../model/templateTO";

@Component({
  selector: 'app-file-generator-card',
  templateUrl: './file-generator-card.component.html',
  styleUrls: ['./file-generator-card.component.scss']
})
export class FileGeneratorCardComponent implements OnInit {

  title = 'file-generator-ui';
  formGroupFile !: FormGroup;
  formGroupTemplate !: FormGroup;
  fileTemplate!: TemplateTO;
  template !: TemplateValueTOS;
  fileStatus!: FileStatusTO;
  filteredCategories!: Observable<string[]> | undefined;
  submitted = false;
  counter !: number;

  constructor(private readonly formBuilder: FormBuilder,
              private readonly categoryService: CategoryService,
              private readonly backendService: FileGeneratorBackendService,
              public dialog: MatDialog
  ) {
    this.counter = 0;
  }


  ngOnInit(): void {
    this.initFile();
    this.initTemplate();
    this.initFormGroupFile();
    this.initFormGroupTemplate();
    this.setFilteredCategories();

    this.refresh();
  }

  initFormGroupFile = (): FormGroup => {
    return this.formGroupFile = this.formBuilder.group({
      autor: new FormControl(this.getText(this.fileTemplate.autor),
        [Validators.required, Validators.minLength(4)])
    })
  }

  initFormGroupTemplate = (): FormGroup => {
    return this.formGroupTemplate = this.formBuilder.group({

      title: new FormControl(this.getText(this.template.title),
        [Validators.required, Validators.minLength(2)]),

      description: new FormControl(this.getText(this.template.description),
        [Validators.required, Validators.minLength(10)]),

      price: new FormControl(this.getText(this.template.price),
        [Validators.required]),

      priceAccording: new FormControl(this.getText(this.template.priceAccording),
        [Validators.required, Validators.minLength(4)]),

      category: new FormControl(this.getText(this.template.category),
        [Validators.required, Validators.minLength(4)]),
    })
  }


  get controlAutor(): FormControl {
    return (<FormControl>this.formGroupFile.get('autor'));
  }

  get controlTitle(): FormControl {
    return (<FormControl>this.formGroupTemplate.get('title'));
  }

  get controlDescription(): FormControl {
    return (<FormControl>this.formGroupTemplate.get('description'));
  }

  get controlPrice(): FormControl {
    return (<FormControl>this.formGroupTemplate.get('price'));
  }

  get controlPriceAccording(): FormControl {
    return (<FormControl>this.formGroupTemplate.get('priceAccording'));
  }

  get controlCategory(): FormControl {
    return (<FormControl>this.formGroupTemplate.get('category'));
  }

  get categories(): string[] {
    return this.categoryService.getCategory();
  }


  collectTamplateValues(): void {
    let autor = this.controlAutor.value;
    let title = this.controlTitle.value;
    let category = this.controlCategory.value;
    let description = this.controlDescription.value;
    let price = this.controlPrice.value.replace(',' , '.');
    let priceAccording = this.controlPriceAccording.value;

    this.fileTemplate.autor = autor;

    let template = {
      title: title,
      category: category,
      description: description,
      price: price,
      priceAccording: priceAccording
    };

    this.fileTemplate.templateValueTOS.push(template);
    this.refresh();

    this.submitted = true;
    this.counter++;
  }


  private refresh() {
    this.controlTitle.setValue('');
    this.controlCategory.setValue('');
    this.controlDescription.setValue('');
    this.controlPrice.setValue('');
    this.controlPriceAccording.setValue('');
  }

  createTemplate() {
    this.openDownLoadDialog();
    /*  this.resetFileTemplateArray();*/
  }

  isFormsValid(): boolean {
    return this.controlAutor.valid
      && this.controlTitle.valid
      && this.controlCategory.valid
      && this.controlDescription.valid
      && this.controlPrice.valid
      && this.controlPriceAccording.valid
  }

  openInfoDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      withSQL: this.fileTemplate.withSQL
    };

    this.dialog.open(InfoDialogComponent, dialogConfig);
  }

  openDownLoadDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      fileTemplate: this.fileTemplate
    };
    const dialogRef = this.dialog.open(DownloadDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(()=> this.resetFileTemplateArray());
  }


  setWithSQL() {
    this.fileTemplate.withSQL = !this.fileTemplate.withSQL;
  }


  private initFile = (): void => {
    this.fileTemplate = {
      autor: '',
      withSQL: true,
      templateValueTOS: []
    };
  }


  private initTemplate = (): void => {
    this.template = {
      title: '',
      category: '',
      description: '',
      price: '',
      priceAccording: ''
    }
  }

  private getText = (text: string): string => {
    if (text) {
      return text;
    }
    return '';
  }

  private setFilteredCategories(): void {
    this.filteredCategories = this.formGroupTemplate.controls['category'].valueChanges.pipe(
      startWith(''),
      map((value: string) => this.filterCategory(value))
    );
  }

  private filterCategory(value: string): string[] {
    let categories = this.categories;
    return categories.filter(category => category.toLowerCase().indexOf(value.toLowerCase()) === 0);
  }


  private resetFileTemplateArray() {
    this.submitted = false;
    this.fileTemplate.templateValueTOS = [];
    this.counter = 0;
  }

}
