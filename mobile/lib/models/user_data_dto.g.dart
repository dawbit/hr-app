// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_data_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserDataDto _$UserDataDtoFromJson(Map<String, dynamic> json) {
  return UserDataDto(
    phoneNumber: json['phoneNumber'] as String,
    email: json['email'] as String,
    cv: json['fileName'] as String,
  );
}

Map<String, dynamic> _$UserDataDtoToJson(UserDataDto instance) =>
    <String, dynamic>{
      'fileName': instance.cv,
      'email': instance.email,
      'phoneNumber': instance.phoneNumber,
    };
