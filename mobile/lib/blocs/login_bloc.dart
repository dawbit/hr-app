
import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:dio/dio.dart';
import 'package:mobile/enums/error_login_type.dart';
import 'package:mobile/models/login_command_dto.dart';
import 'package:mobile/models/token.dart';
import 'package:mobile/repositories/login_repository.dart';
import 'package:mobile/security/token_shared_pref.dart';
import 'package:rxdart/rxdart.dart';

class LoginBloc extends BlocBase {

  LoginRepository _loginRepository;

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject _isLoggedInSubject = PublishSubject();
  Stream get isLoggedInObservable => _isLoggedInSubject.stream;

  PublishSubject<ErrorLoginType> _errorSubject = PublishSubject();
  Stream<ErrorLoginType> get errorObservable => _errorSubject.stream;

  LoginBloc(this._loginRepository);

  Future attemptToLogin(LoginCommandDto loginCommandDto) async {
    _isLoadingSubject.add(true);
    _loginRepository.attemptToLogin(loginCommandDto)
        .then(onSuccessLogin)
        .catchError(onLoginFailed);
  }

  void onSuccessLogin(Token token) async {
    await TokenSharedPref.setToken(token);
    _isLoggedInSubject.add(null);
    _isLoadingSubject.add(false);
  }

  void onLoginFailed(Object obj) {
    _isLoadingSubject.add(false);
    final res = (obj as DioError).response;
    if((obj as DioError).error.toString().toLowerCase().contains("connection failed")) {
      _errorSubject.add(ErrorLoginType.CONNECTION_ERROR);
    }
    else if(res.statusCode >= 500) {
      _errorSubject.add(ErrorLoginType.SERVER_ERROR);
    } else {
      _errorSubject.add(ErrorLoginType.CANT_LOGIN);
    }
    print("login error: ${obj.toString()}");
  }
}