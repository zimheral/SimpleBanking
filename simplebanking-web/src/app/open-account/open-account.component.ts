import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { take } from 'rxjs/operators';
import { AccountService } from '../account.service';
import {Account} from '../customer-info'
import {CustomerInfo} from '../customer-info';
import { CustomerService } from '../customer.service';


@Component({
  selector: 'app-open-account',
  templateUrl: './open-account.component.html',
  styleUrls: ['./open-account.component.css']
})
export class OpenAccountComponent implements OnInit {

  constructor(private accountService: AccountService,
    private customerService: CustomerService,
    private route: ActivatedRoute) { }

  customerId: number;  
  customerInfo: CustomerInfo;
  account: Account;
  credit: Number;

  ngOnInit(): void {
    this.customerId = +this.route.snapshot.paramMap.get('id');
    this.customerService.getCustomerInfo(this.customerId)
      .subscribe(customerInfo => this.customerInfo = customerInfo);
     
  }

  process(credit: number): void {
    if (!credit) { return; }
    this.accountService.createAccount(this.customerId, credit)
      .subscribe(account => this.reloadAfterProcessing(account));
       
  }

  reloadAfterProcessing(account: Account){
    this.account = account;
    window.location.reload();
  }

}
