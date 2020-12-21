// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'change_phone_number_command_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ChangePhoneNumberCommandDto _$ChangePhoneNumberCommandDtoFromJson(
    Map<String, dynamic> json) {
  return ChangePhoneNumberCommandDto(
    phoneNumber: json['phoneNumber'] as String,
    password: json['password'] as String,
  );
}

Map<String, dynamic> _$ChangePhoneNumberCommandDtoToJson(
        ChangePhoneNumberCommandDto instance) =>
    <String, dynamic>{
      'password': instance.password,
      'phoneNumber': instance.phoneNumber,
    };
