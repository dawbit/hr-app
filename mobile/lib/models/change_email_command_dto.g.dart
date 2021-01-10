// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'change_email_command_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ChangeEmailCommandDto _$ChangeEmailCommandDtoFromJson(
    Map<String, dynamic> json) {
  return ChangeEmailCommandDto(
    newEmail: json['newEmail'] as String,
    password: json['password'] as String,
  );
}

Map<String, dynamic> _$ChangeEmailCommandDtoToJson(
        ChangeEmailCommandDto instance) =>
    <String, dynamic>{
      'password': instance.password,
      'newEmail': instance.newEmail,
    };
