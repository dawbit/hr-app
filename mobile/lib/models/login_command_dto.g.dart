// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'login_command_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

LoginCommandDto _$LoginCommandDtoFromJson(Map<String, dynamic> json) {
  return LoginCommandDto(
    login: json['login'] as String,
    password: json['password'] as String,
  );
}

Map<String, dynamic> _$LoginCommandDtoToJson(LoginCommandDto instance) =>
    <String, dynamic>{
      'login': instance.login,
      'password': instance.password,
    };
