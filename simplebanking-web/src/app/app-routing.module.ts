import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from './customers/customers.component'
import {CustomerInfoComponent} from './customer-info/customer-info.component'
import {OpenAccountComponent} from './open-account/open-account.component'

const routes: Routes = [
  { path: 'customers', component: CustomersComponent },
  { path: 'info/:id', component: CustomerInfoComponent },
  { path: 'account/open/:id', component: OpenAccountComponent }
]

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
