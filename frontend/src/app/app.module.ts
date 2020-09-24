import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
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
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
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
    UserListComponent
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
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: httpTranslateLoader,
        deps: [HttpClient]
      }
    }),
    AuthModule
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
    JwtHelperService,
  ],
  bootstrap: [AppComponent],
  schemas: [NO_ERRORS_SCHEMA]
})
export class AppModule { }

// AOT compilation support
export function httpTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
