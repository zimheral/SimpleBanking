import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import {Customer} from './customer';
import { catchError } from 'rxjs/operators';
import {CustomerInfo} from './customer-info'



@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private customersUrl = 'http://localhost:8080/customers';


  constructor(private http: HttpClient) { }

  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(this.customersUrl)
      .pipe(
        catchError(this.handleError<Customer[]>('getCustomers', []))
      );
  }

  getCustomerInfo(id:number): Observable<CustomerInfo> {
    const url = `${this.customersUrl}/${id}/info`;
    console.info(`url constructed: ${url}`)
    return this.http.get<CustomerInfo>(url)
      .pipe(
        catchError(this.handleError<CustomerInfo>(`getCustomersInfo id=${id}`))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
