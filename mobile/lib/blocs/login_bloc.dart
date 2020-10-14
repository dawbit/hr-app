import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/models/login_command_dto.dart';
import 'package:mobile/models/token.dart';
import 'package:mobile/repositories/login_repository.dart';
import 'package:rxdart/rxdart.dart';

class LoginBloc extends BlocBase {

  LoginRepository _loginRepository;

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  LoginBloc(this._loginRepository);

  Future attemptToLogin(LoginCommandDto loginCommandDto) async {
    _isLoadingSubject.add(true);
    _loginRepository.attemptToLogin(loginCommandDto).then(onSuccessLogin).catchError(onLoginFailed);
  }

  void onSuccessLogin(Token token) {
    print("udalo sie");
    _isLoadingSubject.add(false);
  }

  void onLoginFailed(e) {
    print("nie udalo sie");
    _isLoadingSubject.add(false);
  }

}