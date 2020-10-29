import 'package:json_annotation/json_annotation.dart';

part 'login_command_dto.g.dart';

@JsonSerializable()
class LoginCommandDto {
  @JsonKey(name: "login")
  String login;
  @JsonKey(name: "password")
  String password;

  LoginCommandDto({this.login, this.password});

  factory LoginCommandDto.fromJson(Map<String, dynamic> json) => _$LoginCommandDtoFromJson(json);

  Map<String, dynamic> toJson() => _$LoginCommandDtoToJson(this);
}