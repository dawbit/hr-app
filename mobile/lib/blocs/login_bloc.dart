import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/models/login_command_dto.dart';
import 'package:mobile/models/token.dart';
import 'package:mobile/repositories/login_repository.dart';
import 'package:mobile/security/token_shared_pref.dart';
import 'package:rxdart/rxdart.dart';

class LoginBloc extends BlocBase {

  LoginRepository _loginRepository;
  TokenSharedPref tokenSharedPref;

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<bool> _isLoggedInSubject = PublishSubject();
  Stream<bool> get isLoggedInObservable => _isLoggedInSubject.stream;

  LoginBloc(this._loginRepository) {
    tokenSharedPref = TokenSharedPref();
  }

  Future attemptToLogin(LoginCommandDto loginCommandDto) async {
    //TODO usunąć consta który ułatwia logowanie
    final loing = LoginCommandDto(login: "test123", password: "test");
    _isLoadingSubject.add(true);
    _loginRepository.attemptToLogin(loing)
        .then(onSuccessLogin)
        .catchError(onLoginFailed);
  }

  void onSuccessLogin(Token token) {
    tokenSharedPref.setToken(token);
    _isLoggedInSubject.add(true);
    _isLoadingSubject.add(false);
  }

  void onLoginFailed(e) {
    _isLoggedInSubject.add(false);
    _isLoadingSubject.add(false);
  }

}