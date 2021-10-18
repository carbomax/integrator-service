/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { Auth.interceptor.httpService } from './auth.interceptor.http.service';

describe('Service: Auth.interceptor.http', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [Auth.interceptor.httpService]
    });
  });

  it('should ...', inject([Auth.interceptor.httpService], (service: Auth.interceptor.httpService) => {
    expect(service).toBeTruthy();
  }));
});
