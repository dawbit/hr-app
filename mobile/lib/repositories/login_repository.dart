import 'package:mobile/data_sources/remote/authorization_source.dart';
import 'package:mobile/models/login_command_dto.dart';
import 'package:mobile/models/token.dart';

class LoginRepository {

  AuthorizationSource _authorizationSource;

  LoginRepository(this._authorizationSource);

  Future<Token> attemptToLogin(LoginCommandDto loginCommandDto) =>
      _authorizationSource.attemptToLogin(loginCommandDto)
          .then((onValue) => Token(accessToken: onValue.response.headers.value("Authorization")));

}