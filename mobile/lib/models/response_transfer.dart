import 'package:json_annotation/json_annotation.dart';

part 'response_transfer.g.dart';

@JsonSerializable()
class ResponseTransfer {
  @JsonKey(name: "message")
  String message;

  @JsonKey(name: "exception")
  String exception;

  ResponseTransfer({this.message, this.exception});

  factory ResponseTransfer.fromJson(Map<String, dynamic> json) => _$ResponseTransferFromJson(json);

  Map<String, dynamic> toJson() => _$ResponseTransferToJson(this);
}