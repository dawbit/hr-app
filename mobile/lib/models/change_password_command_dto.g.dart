// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'change_password_command_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ChangePasswordCommandDto _$ChangePasswordCommandDtoFromJson(
    Map<String, dynamic> json) {
  return ChangePasswordCommandDto(
    newPassword: json['newPassword'] as String,
    password: json['password'] as String,
  );
}

Map<String, dynamic> _$ChangePasswordCommandDtoToJson(
        ChangePasswordCommandDto instance) =>
    <String, dynamic>{
      'password': instance.password,
      'newPassword': instance.newPassword,
    };
