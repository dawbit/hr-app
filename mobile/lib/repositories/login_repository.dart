import 'package:mobile/data_sources/remote/authorization_source.dart';
import 'package:mobile/models/login_command_dto.dart';
import 'package:mobile/models/register_command_dto.dart';
import 'package:mobile/models/response_transfer.dart';
import 'package:mobile/models/token.dart';
import 'package:mobile/models/user_data_dto.dart';

class LoginRepository {

  AuthorizationSource _authorizationSource;

  LoginRepository(this._authorizationSource);

  Future<Token> attemptToLogin(LoginCommandDto loginCommandDto) =>
      _authorizationSource.attemptToLogin(loginCommandDto)
          .then((onValue) => Token(accessToken: onValue.response.headers.value("Authorization")));

  Future<ResponseTransfer> attemptToRegister(RegisterCommandDto registerCommandDto) =>
      _authorizationSource.attemptToRegister(registerCommandDto);

  Future<UserDataDto> getUserData(String token) => _authorizationSource.getUserData("Bearer $token");

}