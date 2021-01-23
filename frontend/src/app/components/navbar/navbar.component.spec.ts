import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { CompanyService } from './../../services/company.service';
import { AlertsService } from './../../services/alerts.service';
import { Router } from '@angular/router';
import { TokenStorageService } from './../../services/security/token-storage.service';
import { RouterTestingModule } from '@angular/router/testing';
import { NavbarComponent } from './navbar.component';
import { TranslateModule } from '@ngx-translate/core';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(() => {
    const companyServiceStub = () => ({
      getCurrentCompany: () => ({ subscribe: f => f({}) })
    });
    const alertsServiceStub = () => ({
      getUserAlerts: () => ({ subscribe: f => f({}) }),
      getHrAlerts: () => ({ subscribe: f => f({}) })
    });
    const routerStub = () => ({ navigate: array => ({}) });
    const tokenStorageServiceStub = () => ({
      isAuthenticated: () => ({}),
      getRole: () => ({}),
      getUser: () => ({}),
      deleteUserFromLocalStorage: () => ({})
    });
    TestBed.configureTestingModule({
      imports: [RouterTestingModule,
        TranslateModule.forRoot()
      ],
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [NavbarComponent],
      providers: [
        { provide: CompanyService, useFactory: companyServiceStub },
        { provide: AlertsService, useFactory: alertsServiceStub },
        { provide: Router, useFactory: routerStub },
        { provide: TokenStorageService, useFactory: tokenStorageServiceStub }
      ]
    });
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
  });

  it('can load instance', () => {
    expect(component).toBeTruthy();
  });

  it(`userAlerts has default value`, () => {
    expect(component.userAlerts).toEqual(0);
  });

  it(`hrAlerts has default value`, () => {
    expect(component.hrAlerts).toEqual(0);
  });

  it(`isLogged has default value`, () => {
    expect(component.isLogged).toEqual(false);
  });

  it(`userRole has default value`, () => {
    expect(component.userRole).toEqual(`USER`);
  });

  describe('ngOnInit', () => {
    it('makes expected calls', () => {
      const tokenStorageServiceStub: TokenStorageService = fixture.debugElement.injector.get(
        TokenStorageService
      );
      spyOn(component, 'checkForAlerts').and.callThrough();
      spyOn(tokenStorageServiceStub, 'isAuthenticated').and.callThrough();
      spyOn(tokenStorageServiceStub, 'getRole').and.callThrough();
      spyOn(tokenStorageServiceStub, 'getUser').and.callThrough();
      component.ngOnInit();
      expect(component.checkForAlerts).toHaveBeenCalled();
      expect(tokenStorageServiceStub.isAuthenticated).toHaveBeenCalled();
      expect(tokenStorageServiceStub.getRole).toHaveBeenCalled();
      expect(tokenStorageServiceStub.getUser).toHaveBeenCalled();
    });
  });

  describe('checkForAlerts', () => {
    it('makes expected calls', () => {
      const companyServiceStub: CompanyService = fixture.debugElement.injector.get(
        CompanyService
      );
      const alertsServiceStub: AlertsService = fixture.debugElement.injector.get(
        AlertsService
      );
      spyOn(companyServiceStub, 'getCurrentCompany').and.callThrough();
      spyOn(alertsServiceStub, 'getUserAlerts').and.callThrough();
      component.checkForAlerts();
      expect(companyServiceStub.getCurrentCompany).toHaveBeenCalled();
      expect(alertsServiceStub.getUserAlerts).toHaveBeenCalled();
    });
  });
});
