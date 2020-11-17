import { Component, OnInit } from '@angular/core';
import {CustomerService} from '../customer.service';
import {Customer} from '../customer';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {

  constructor(private customerService: CustomerService) { }
  customers: Customer[];

  public error: any;

  ngOnInit(): void {
    this.customerService.getCustomers()
      .subscribe(customers => {
        this.customers = customers;
        console.log(customers);
      }, error => {
        console.log(error);
        this.error = error;
      });
  }
}
