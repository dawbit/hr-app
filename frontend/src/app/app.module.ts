import { ChangeUserPhoneNumberComponent } from './components/panels/account-settings/change-user-phone-number/change-user-phone-number.component';
import { ChangeUserPasswordComponent } from './components/panels/account-settings/change-user-password/change-user-password.component';
import { ChangeUserEmailComponent } from './components/panels/account-settings/change-user-email/change-user-email.component';
import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, Injector, NgModule } from '@angular/core';
import { AppComponent } from './app.component';

// components
import { HomePageComponent } from './components/home-page/home-page.component';
import { AdminPanelComponent } from './components/panels/admin-panel/admin-panel.component';
import { HrPanelComponent } from './components/panels/hr-panel/hr-panel.component';
import { CeoPanelComponent } from './components/panels/ceo-panel/ceo-panel.component';
import { CompanyPanelComponent } from './components/panels/company-panel/company-panel.component';
import { UserPanelComponent } from './components/panels/user-panel/user-panel.component';
import { QuizCreatePanelComponent } from './components/panels/quiz-create-panel/quiz-create-panel.component';
import { QuizPanelComponent } from './components/panels/quiz-panel/quiz-panel.component';
import { LoginRegisterComponent } from './components/login-register/login-register.component';
import { CompanyAddComponent } from './components/company/add/company-add/company-add.component';
import { UserListComponent } from './components/panels/admin-panel/user-list/user-list.component';
import { UserUpdateComponent } from './components/panels/admin-panel/user-list/user/user-update/user-update.component';
import { UserDetailsComponent } from './components/panels/admin-panel/user-list/user/user-details/user-details.component';
import { CompanyListComponent } from './components/company-list/company-list.component';
import { SettingsPanelComponent } from './components/panels/settings-panel/settings-panel.component';
import { QuizSolveComponent } from './components/panels/quiz-solve/quiz-solve.component';
import { JobOffersAddComponent } from './components/job-offers-add/job-offers-add.component';
import { ListOfApplicationsComponent } from './components/panels/hr-panel/list-of-applications/list-of-applications.component';
import { QuizAssignModalComponent } from './components/panels/hr-panel/list-of-applications/quiz-assign-modal/quiz-assign-modal/quiz-assign-modal.component';
import { AccountSettingsComponent } from './components/panels/account-settings/account-settings.component';
import { UserListOfApplicationsComponent } from './components/panels/user-panel/user-list-of-applications/user-list-of-applications.component';
import { RandomCompanyPresentationComponent } from './components/home-page/random-company-presentation/random-company-presentation.component';
import { AssignToHrComponent } from './components/panels/ceo-panel/assign-to-hr/assign-to-hr.component';
import { ContactPanelComponent } from './components/panels/contact-panel/contact-panel.component';
import { ContactEmailComponent } from './components/panels/contact-panel/contact-email/contact-email.component';
import { ContactTeamComponent } from './components/panels/contact-panel/contact-team/contact-team.component';
import { MailSubscriptionComponent } from './components/panels/account-settings/mail-subscription/mail-subscription.component';
import { DeleteFromHrComponent } from './components/panels/ceo-panel/delete-from-hr/delete-from-hr.component';
import { CvUploadComponent } from './components/panels/account-settings/cv-upload/cv-upload.component';

// modules
import { PipesModule } from './modules/pipes.module';
import { AngularFileUploaderModule } from 'angular-file-uploader';
import { CountdownModule } from 'ngx-countdown';

// angular materials modules
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';

// angular forms module
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

// angular flex
import { FlexLayoutModule } from '@angular/flex-layout';

// animations module
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// routing module
import { AppRoutingModule } from './app-routing.module';

// NgB modules
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
// import { NavbarModule, WavesModule, ButtonsModule } from 'angular-bootstrap-md';

// ngx translate
import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AuthModule } from './security/auth.module';

// JWT
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';

// Interceptors
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './services/security/token.interceptor';

// Toasts
import { ToastrModule } from 'ngx-toastr';

// JwPagination
import { JwPaginationModule } from 'jw-angular-pagination';
import { CompanyListSingleElementComponent } from './components/company-list/company-list-single-element/company-list-single-element.component';
import { JobOffersListComponent } from './components/job-offers-list/job-offers-list.component';

// factories
import { appInitializerFactory } from './factories/appInitializerFacotry';


@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    AdminPanelComponent,
    HrPanelComponent,
    CeoPanelComponent,
    CompanyPanelComponent,
    UserPanelComponent,
    QuizCreatePanelComponent,
    QuizPanelComponent,
    NavbarComponent,
    LoginRegisterComponent,
    CompanyAddComponent,
    UserListComponent,
    UserUpdateComponent,
    UserDetailsComponent,
    CompanyListComponent,
    CompanyListSingleElementComponent,
    SettingsPanelComponent,
    QuizSolveComponent,
    JobOffersAddComponent,
    JobOffersListComponent,
    ListOfApplicationsComponent,
    QuizAssignModalComponent,
    UserListOfApplicationsComponent,
    RandomCompanyPresentationComponent,
    AssignToHrComponent,
    ContactPanelComponent,
    ContactEmailComponent,
    ContactTeamComponent,
    AccountSettingsComponent,
    MailSubscriptionComponent,
    UserListOfApplicationsComponent,
    DeleteFromHrComponent,
    CvUploadComponent,
    ChangeUserEmailComponent,
    ChangeUserPasswordComponent,
    ChangeUserPhoneNumberComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    BrowserAnimationsModule,
    MDBBootstrapModule.forRoot(),
    ToastrModule.forRoot(),
    BrowserAnimationsModule,
    FormsModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    HttpClientModule,
    JwPaginationModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: httpTranslateLoader,
        deps: [HttpClient]
      }
    }),
    AuthModule,
    PipesModule,
    AngularFileUploaderModule,
    CountdownModule
  ],
  exports: [
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule
  ],
  providers: [
    {
      provide: JWT_OPTIONS,
      useValue: JWT_OPTIONS,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
    {
      provide: APP_INITIALIZER,
      useFactory: appInitializerFactory,
      deps: [TranslateService, Injector],
      multi: true
    },
    JwtHelperService,
  ],
  bootstrap: [AppComponent],
  schemas: [NO_ERRORS_SCHEMA]
})
export class AppModule { }

// AOT compilation support
export function httpTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}
