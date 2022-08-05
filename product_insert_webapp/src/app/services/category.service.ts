import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  category = [
    'Baby Products',
    'Beauty',
    'Beer, Wine and Liquor',
    'Book',
    'Car and Motorbike',
    'Clothing',
    'Computer and Accessories',
    'Drugstore and Body Care',
    'Electronic',
    'Food',
    'Home and Garden',
    'Home Improvement Store',
    'Hygiene',
    'Lighting',
    'Music',
    'Office',
    'Toys and Children Products'
  ]

  constructor() { }

  public getCategory = () => {
    return this.category;
  }
}
