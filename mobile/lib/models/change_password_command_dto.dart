import 'package:json_annotation/json_annotation.dart';

part 'change_password_command_dto.g.dart';

@JsonSerializable()
class ChangePasswordCommandDto {
  @JsonKey(name: "password")
  String password;

  @JsonKey(name: "newPassword")
  String newPassword;

  ChangePasswordCommandDto({this.newPassword, this.password});

  factory ChangePasswordCommandDto.fromJson(Map<String, dynamic> json) => _$ChangePasswordCommandDtoFromJson(json);

  Map<String, dynamic> toJson() => _$ChangePasswordCommandDtoToJson(this);
}