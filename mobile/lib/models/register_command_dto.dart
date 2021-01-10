import 'package:json_annotation/json_annotation.dart';

part 'register_command_dto.g.dart';

@JsonSerializable()
class RegisterCommandDto {
  @JsonKey(name: "firstName")
  String firstName;
  @JsonKey(name: "middleName")
  String middleName;
  @JsonKey(name: "surname")
  String surname;
  @JsonKey(name: "email")
  String email;
  @JsonKey(name: "phoneNumber")
  String phoneNumber;
  @JsonKey(name: "login")
  String login;
  @JsonKey(name: "password")
  String password;

  RegisterCommandDto({this.login, this.password, this.email, this.firstName, this.middleName, this.phoneNumber, this.surname});

  factory RegisterCommandDto.fromJson(Map<String, dynamic> json) => _$RegisterCommandDtoFromJson(json);

  Map<String, dynamic> toJson() => _$RegisterCommandDtoToJson(this);
}