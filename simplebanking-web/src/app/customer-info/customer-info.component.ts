import { Component, Input, OnInit } from '@angular/core';
import {CustomerService} from '../customer.service';
import {CustomerInfo} from '../customer-info';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-customer-info',
  templateUrl: './customer-info.component.html',
  styleUrls: ['./customer-info.component.css']
})
export class CustomerInfoComponent implements OnInit {

  constructor(private customerService: CustomerService,
    private route: ActivatedRoute) { }
  
  customerInfo: CustomerInfo;


  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.customerService.getCustomerInfo(id)
      .subscribe(customerInfo => this.customerInfo = customerInfo);
  }

}
