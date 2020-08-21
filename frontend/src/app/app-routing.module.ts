import { NgModule } from '@angular/core';
import { Routes, RouterModule, CanActivate } from '@angular/router';
import { AuthGuardService as AuthGuard } from './security/auth-guard.service';
import { RoleGuardService as RoleGuard } from './security/role-guard.service';

import { HomePageComponent } from './components/home-page/home-page.component';
import { AdminPanelComponent } from './components/panels/admin-panel/admin-panel.component';
import { HrPanelComponent } from './components/panels/hr-panel/hr-panel.component';
import { CeoPanelComponent } from './components/panels/ceo-panel/ceo-panel.component';
import { CompanyPanelComponent } from './components/panels/company-panel/company-panel.component';
import { UserPanelComponent } from './components/panels/user-panel/user-panel.component';
import { QuizCreatePanelComponent } from './components/panels/quiz-create-panel/quiz-create-panel.component';
import { QuizPanelComponent } from './components/panels/quiz-panel/quiz-panel.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePageComponent },
  {
    path: 'admin-panel', component: AdminPanelComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['ADMIN']
    }
  },
  {
    path: 'hr-panel', component: HrPanelComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['HR', 'ADMIN']
    }
  },
  {
    path: 'ceo-panel', component: CeoPanelComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['CEO', 'ADMIN']
    }
  },
  {
    path: 'company-panel', component: CompanyPanelComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['HR', 'CEO', 'ADMIN']
    }
  },
  {
    path: 'user-panel', component: UserPanelComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['USER', 'HR', 'CEO', 'ADMIN']
    }
  },
  {
    path: 'quiz-panel', component: QuizCreatePanelComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['USER', 'HR', 'CEO', 'ADMIN']
    }
  }, //TODO :id
  {
    path: 'quiz-create-panel', component: QuizPanelComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['HR', 'CEO', 'ADMIN']
    }
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
