import 'package:dio/dio.dart';
import 'package:mobile/models/change_email_command_dto.dart';
import 'package:mobile/models/change_password_command_dto.dart';
import 'package:mobile/models/change_phone_number_command_dto.dart';
import 'package:mobile/models/response_transfer.dart';
import 'package:retrofit/http.dart';
import 'package:retrofit/retrofit.dart';

part 'account_source.g.dart';

@RestApi(baseUrl: "http://192.168.43.228:8080/user")
abstract class AccountSource{

  factory AccountSource(Dio dio, {String baseUrl})= _AccountSource;

  @POST("/change-password")
  Future<ResponseTransfer> changePassowrd(@Body() ChangePasswordCommandDto changePasswordCommandDto);

  @POST("/change-phonenumber")
  Future<ResponseTransfer> changePhoneNumber(@Body() ChangePhoneNumberCommandDto changePhoneNumberCommandDto);

  @POST("/change-email")
  Future<ResponseTransfer> changeEmail(@Body() ChangeEmailCommandDto changeEmailCommandDto);

}