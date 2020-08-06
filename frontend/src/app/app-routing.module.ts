import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserLoginComponent } from './user/user-login/user-login.component';
import { AuthGuard } from './security/guards/auth.guard';
import { UserCreateComponent } from './user/user-create/user-create.component';
import { HomePageComponent } from './home-page/home-page.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePageComponent },
  { path: 'registry', component: UserCreateComponent},
  { path: 'login', component: UserLoginComponent,
  canActivate: [AuthGuard]},
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
