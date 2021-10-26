export class MeliProduct {
  id: string;
  siteId: string;
  idPublication: string;
  subTitle: string;
  title: string;
  sellerId: string;
  categoryId: string;
  price: number;
  currencyId: string;
  availableQuantity: number;
  buyingMode: string;
  condition: string;
  listingTypeId: string;
  videoId: string;
  description: any;
  salesTerms: any[];
  pictures: Pictures[];
  attributes: any[];
  category: Category;
}

export interface Category{
  id: string,
  name: string
}

export interface Description {
  plainText: string,
}

export interface SalesTerms {
  id: string,
  valueName: string,
}

export interface Pictures {
  source: string
}

export interface Attributes {
  id: string,
  valueName: string
}
