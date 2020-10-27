import 'package:mobile/models/login_command_dto.dart';
import 'package:mobile/models/token.dart';
import 'package:retrofit/http.dart';
import 'package:dio/dio.dart';
import 'package:retrofit/retrofit.dart';

part 'authorization_source.g.dart';

@RestApi(baseUrl: "http://192.168.43.228:8080")
abstract class AuthorizationSource{

  factory AuthorizationSource(Dio dio, {String baseUrl})= _AuthorizationSource;

  @POST("/login")
  Future<HttpResponse> attemptToLogin(@Body() LoginCommandDto loginCommandDto);

}