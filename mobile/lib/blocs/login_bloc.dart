import 'package:bloc_pattern/bloc_pattern.dart';
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

  LoginBloc(this._loginRepository);

  Future attemptToLogin(LoginCommandDto loginCommandDto) async {
    //TODO usunąć consta który ułatwia logowanie
    final loing = LoginCommandDto(login: "test123", password: "test");
    _isLoadingSubject.add(true);
    _loginRepository.attemptToLogin(loing)
        .then(onSuccessLogin)
        .catchError(onLoginFailed);
  }

  void onSuccessLogin(Token token) async {
    await TokenSharedPref.setToken(token);
    _isLoggedInSubject.add(null);
    _isLoadingSubject.add(false);
  }

  void onLoginFailed(e) {
    _isLoadingSubject.add(false);
    print("login error: ${e.toString()}");
  }

}