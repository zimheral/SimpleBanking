import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from './customers/customers.component'
import {CustomerInfoComponent} from './customer-info/customer-info.component'

const routes: Routes = [
  { path: 'customers', component: CustomersComponent },
  { path: 'info/:id', component: CustomerInfoComponent }
]

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
