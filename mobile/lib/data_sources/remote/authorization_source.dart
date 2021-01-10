import 'package:dio/dio.dart';
import 'package:mobile/models/login_command_dto.dart';
import 'package:mobile/models/register_command_dto.dart';
import 'package:mobile/models/response_transfer.dart';
import 'package:mobile/models/user_data_dto.dart';
import 'package:retrofit/http.dart';
import 'package:retrofit/retrofit.dart';

part 'authorization_source.g.dart';

@RestApi(baseUrl: "http://192.168.43.228:8080")
abstract class AuthorizationSource{

  factory AuthorizationSource(Dio dio, {String baseUrl})= _AuthorizationSource;

  @POST("/login")
  Future<HttpResponse> attemptToLogin(@Body() LoginCommandDto loginCommandDto);

  @POST("/user/register")
  Future<ResponseTransfer> attemptToRegister(@Body() RegisterCommandDto registerCommandDto);

  @GET("/user/getdata")
  Future<UserDataDto> getUserData(@Header("Authorization") String token);
}