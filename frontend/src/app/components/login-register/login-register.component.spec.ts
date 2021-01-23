// import { ComponentFixture, TestBed } from '@angular/core/testing';
// import { NO_ERRORS_SCHEMA } from '@angular/core';
// import { FormBuilder } from '@angular/forms';
// import { UserService } from './../../services/user.service';
// import { TokenStorageService } from './../../services/security/token-storage.service';
// import { ToastService } from './../../services/toast.service';
// import { FormsModule } from '@angular/forms';
// import { LoginRegisterComponent } from './login-register.component';
// import { TranslateModule } from '@ngx-translate/core';
// import { of } from 'rxjs';

// describe('LoginRegisterComponent', () => {
//   let component: LoginRegisterComponent;
//   let fixture: ComponentFixture<LoginRegisterComponent>;

//   beforeEach(() => {
//     const formBuilderStub = () => ({ group: object => ({}) });
//     const userServiceStub = () => ({
//       login: value => ({ subscribe: f => f({}) }),
//       register: user => ({ subscribe: f => f({}) })
//     });
//     const tokenStorageServiceStub = () => ({
//       saveUserInLocalStorage: authorizationInfo => ({})
//     });
//     const toastServiceStub = () => ({
//       showSuccess: string => ({}),
//       showWarning: string => ({}),
//       showError: string => ({})
//     });
//     TestBed.configureTestingModule({
//       imports: [FormsModule,
//         TranslateModule.forRoot(),
//       ],
//       schemas: [NO_ERRORS_SCHEMA],
//       declarations: [LoginRegisterComponent],
//       providers: [
//         { provide: FormBuilder, useFactory: formBuilderStub },
//         { provide: UserService, useFactory: userServiceStub },
//         { provide: TokenStorageService, useFactory: tokenStorageServiceStub },
//         { provide: ToastService, useFactory: toastServiceStub }
//       ]
//     });
//     fixture = TestBed.createComponent(LoginRegisterComponent);
//     component = fixture.componentInstance;
//   });

//   it('can load instance', () => {
//     expect(component).toBeTruthy();
//   });

//   it(`selectedForm has default value`, () => {
//     expect(component.selectedForm).toEqual(`login`);
//   });

//   describe('ngOnInit', () => {
//     it('makes expected calls', () => {
//       const formBuilderStub: FormBuilder = fixture.debugElement.injector.get(
//         FormBuilder
//       );
//       spyOn(formBuilderStub, 'group').and.callThrough();
//       component.ngOnInit();
//       expect(formBuilderStub.group).toHaveBeenCalled();
//     });
//   });

//   describe('loginSubmit', () => {
//     it('makes expected calls', () => {
//       const userServiceStub: UserService = fixture.debugElement.injector.get(
//         UserService
//       );
//       const tokenStorageServiceStub: TokenStorageService = fixture.debugElement.injector.get(
//         TokenStorageService
//       );
//       const toastServiceStub: ToastService = fixture.debugElement.injector.get(
//         ToastService
//       );
//       spyOn(userServiceStub, 'login').and.callThrough();
//       spyOn(
//         tokenStorageServiceStub,
//         'saveUserInLocalStorage'
//       ).and.callThrough();
//       spyOn(toastServiceStub, 'showSuccess').and.callThrough();
//       spyOn(toastServiceStub, 'showWarning').and.callThrough();
//       spyOn(toastServiceStub, 'showError').and.callThrough();
//       component.loginSubmit();
//       expect(userServiceStub.login).toHaveBeenCalled();
//       expect(tokenStorageServiceStub.saveUserInLocalStorage).toHaveBeenCalled();
//       expect(toastServiceStub.showSuccess).toHaveBeenCalled();
//       expect(toastServiceStub.showWarning).toHaveBeenCalled();
//       expect(toastServiceStub.showError).toHaveBeenCalled();
//     });
//   });

//   describe('registerSubmit', () => {
//     it('makes expected calls', () => {
//       const userServiceStub: UserService = fixture.debugElement.injector.get(
//         UserService
//       );
//       const toastServiceStub: ToastService = fixture.debugElement.injector.get(
//         ToastService
//       );
//       const mockedResponse = {response: 200};
//       spyOn(userServiceStub, 'register').and.callFake(() => {
//         return of(mockedResponse);
//       });
//       spyOn(toastServiceStub, 'showSuccess').and.callThrough();
//       spyOn(toastServiceStub, 'showError').and.callThrough();
//       component.registerSubmit();
//       expect(userServiceStub.register).toHaveBeenCalled();
//       expect(toastServiceStub.showSuccess).toHaveBeenCalled();
//       expect(toastServiceStub.showError).toHaveBeenCalled();
//     });
//   });
// });
