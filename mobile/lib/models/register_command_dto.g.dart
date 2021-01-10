// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'register_command_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

RegisterCommandDto _$RegisterCommandDtoFromJson(Map<String, dynamic> json) {
  return RegisterCommandDto(
    login: json['login'] as String,
    password: json['password'] as String,
    email: json['email'] as String,
    firstName: json['firstName'] as String,
    middleName: json['middleName'] as String,
    phoneNumber: json['phoneNumber'] as String,
    surname: json['surname'] as String,
  );
}

Map<String, dynamic> _$RegisterCommandDtoToJson(RegisterCommandDto instance) =>
    <String, dynamic>{
      'firstName': instance.firstName,
      'middleName': instance.middleName,
      'surname': instance.surname,
      'email': instance.email,
      'phoneNumber': instance.phoneNumber,
      'login': instance.login,
      'password': instance.password,
    };
