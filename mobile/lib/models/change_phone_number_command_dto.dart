import 'package:json_annotation/json_annotation.dart';

part 'change_phone_number_command_dto.g.dart';

@JsonSerializable()
class ChangePhoneNumberCommandDto {
  @JsonKey(name: "password")
  String password;

  @JsonKey(name: "phoneNumber")
  String phoneNumber;

  ChangePhoneNumberCommandDto({this.phoneNumber, this.password});

  factory ChangePhoneNumberCommandDto.fromJson(Map<String, dynamic> json) => _$ChangePhoneNumberCommandDtoFromJson(json);

  Map<String, dynamic> toJson() => _$ChangePhoneNumberCommandDtoToJson(this);
}