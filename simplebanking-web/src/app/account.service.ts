import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import {Account} from './customer-info';
import {Credit} from './credit';
import { catchError } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private accountUrl = 'http://localhost:8080/account/open';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  createAccount(customerId:Number, creditVal:Number): Observable<Account> {
    const url = `${this.accountUrl}/${customerId}`
    return this.http.post(url, {credit: creditVal}, this.httpOptions).pipe(
      catchError(this.handleError<any>('createAccount'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error("error ocurred: ");
      console.error(error);
      return of(result as T);
    };
  }
}
