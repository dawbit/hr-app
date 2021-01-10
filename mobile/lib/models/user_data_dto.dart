import 'package:json_annotation/json_annotation.dart';

part 'user_data_dto.g.dart';

@JsonSerializable()
class UserDataDto {
  @JsonKey(name: "fileName")
  String cv;

  @JsonKey(name: "email")
  String email;

  @JsonKey(name: "phoneNumber")
  String phoneNumber;

  UserDataDto({this.phoneNumber, this.email, this.cv});

  factory UserDataDto.fromJson(Map<String, dynamic> json) => _$UserDataDtoFromJson(json);

  Map<String, dynamic> toJson() => _$UserDataDtoToJson(this);
}