import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class FormValidatorService {
  constructor() {}

  required(field: string, form: FormGroup) {
    return (
      this.fieldNotValid(field, form) &&
      form.get(field).errors?.required
    );
  }

  minLength(field: string, form: FormGroup){
    return this.fieldNotValid(field, form) && form.get(field).errors?.minlength
  }

  email(field: string,  form: FormGroup) {
    return this.fieldNotValid(field, form) && form.get(field).errors?.email
  }

  fieldNotValid(field: string, form: FormGroup) {
    return form.invalid
            && form.controls[field].errors
            && (form.touched || form.dirty)
  }

}
