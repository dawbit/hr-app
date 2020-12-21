import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:dio/dio.dart';
import 'package:mobile/enums/error_register_type.dart';
import 'package:mobile/models/register_command_dto.dart';
import 'package:mobile/models/response_transfer.dart';
import 'package:mobile/repositories/login_repository.dart';
import 'package:rxdart/rxdart.dart';

class RegistrationBloc extends BlocBase {

  LoginRepository _loginRepository;

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject _isRegisteredSubject = PublishSubject();
  Stream get isRegisteredObservable => _isRegisteredSubject.stream;

  PublishSubject<ErrorRegisterType> _errorSubject = PublishSubject();
  Stream<ErrorRegisterType> get errorObservable => _errorSubject.stream;

  RegistrationBloc(this._loginRepository);

  Future attemptToLogin(RegisterCommandDto registerCommandDto) async {
    _isLoadingSubject.add(true);
    _loginRepository.attemptToRegister(registerCommandDto)
        .then(onSuccessLogin)
        .catchError(onLoginFailed);
  }

  void onSuccessLogin(ResponseTransfer responseTransfer) async {
    _isRegisteredSubject.add(null);
    _isLoadingSubject.add(false);
  }

  void onLoginFailed(Object obj) {
    _isLoadingSubject.add(false);
    final dioError = obj as DioError;
    final res = dioError.response;
    if((dioError).error.toString().toLowerCase().contains("connection failed")) {
      _errorSubject.add(ErrorRegisterType.CONNECTION_ERROR);
    }
    else if(res.statusCode >= 500) {
      _errorSubject.add(ErrorRegisterType.SERVER_ERROR);
    } else if(res.toString().contains("LOGIN_EXISTS")){
      _errorSubject.add(ErrorRegisterType.LOGIN_EXISTS);
    } else if(res.toString().contains("EMAIL_EXISTS")){
      _errorSubject.add(ErrorRegisterType.EMAIL_EXISTS);
    }
    print("registration error: ${obj.toString()}");
  }
}