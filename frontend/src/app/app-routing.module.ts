import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardService as AuthGuard } from './services/security/auth-guard.service';
import { RoleGuardService as RoleGuard } from './services/security/role-guard.service';

import { HomePageComponent } from './components/home-page/home-page.component';
import { AdminPanelComponent } from './components/panels/admin-panel/admin-panel.component';
import { HrPanelComponent } from './components/panels/hr-panel/hr-panel.component';
import { CeoPanelComponent } from './components/panels/ceo-panel/ceo-panel.component';
import { CompanyPanelComponent } from './components/panels/company-panel/company-panel.component';
import { UserPanelComponent } from './components/panels/user-panel/user-panel.component';
import { QuizCreatePanelComponent } from './components/panels/quiz-create-panel/quiz-create-panel.component';
import { QuizPanelComponent } from './components/panels/quiz-panel/quiz-panel.component';
import { CompanyAddComponent } from './components/company/add/company-add/company-add.component';
import { UserDetailsComponent } from './components/panels/admin-panel/user-list/user/user-details/user-details.component';
import { UserUpdateComponent } from './components/panels/admin-panel/user-list/user/user-update/user-update.component';
import { SettingsPanelComponent } from './components/panels/settings-panel/settings-panel.component';
import { QuizSolveComponent } from './components/panels/quiz-solve/quiz-solve.component';
import { CompanyListComponent } from './components/company-list/company-list.component';
import { JobOffersAddComponent } from './components/job-offers-add/job-offers-add.component';
import { JobOffersListComponent } from './components/job-offers-list/job-offers-list.component';
import { ContactPanelComponent } from './components/panels/contact-panel/contact-panel.component';
import { AccountSettingsComponent } from './components/panels/account-settings/account-settings.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePageComponent },
  { path: 'contact', component: ContactPanelComponent },
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
    path: 'settings-panel', component: SettingsPanelComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['USER', 'HR', 'CEO', 'ADMIN']
    }
  },
  {
    path: 'quiz-create-panel', component: QuizCreatePanelComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['HR', 'CEO', 'ADMIN']
    }
  },
  {
    path: 'quiz-solve', component: QuizSolveComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['USER', 'HR', 'CEO', 'ADMIN']
    }
  },
  {
    path: 'quiz-panel', component: QuizPanelComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['HR', 'CEO', 'ADMIN']
    }
  },
  {
    path: 'company-add', component: CompanyAddComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['USER', 'HR', 'CEO', 'ADMIN']
    }
  },
  {
    path: 'company-list', component: CompanyListComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['USER', 'HR', 'CEO', 'ADMIN']
    }
  },
  {
    path: 'job-offer-list', component: JobOffersListComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['USER', 'HR', 'CEO', 'ADMIN']
    }
  },
  {
    path: 'add-job-offer', component: JobOffersAddComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['HR', 'CEO', 'ADMIN']
    }
  },
  {
    path: 'admin-panel/details/:id', component: UserDetailsComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['ADMIN']
    }
  },
  {
    path: 'admin-panel/update/:id', component: UserUpdateComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['ADMIN']
    }
  },
  {
    path: 'my-account', component: AccountSettingsComponent, canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: ['USER', 'HR', 'CEO', 'ADMIN']
    }
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
