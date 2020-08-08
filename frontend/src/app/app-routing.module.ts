import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

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
  { path: 'admin-panel', component: AdminPanelComponent },
  { path: 'hr-panel', component: HrPanelComponent },
  { path: 'ceo-panel', component: CeoPanelComponent },
  { path: 'company-panel', component: CompanyPanelComponent },
  { path: 'user-panel', component: UserPanelComponent },
  { path: 'quiz-panel', component: QuizCreatePanelComponent }, //TODO :id
  { path: 'quiz-create-panel', component: QuizPanelComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
