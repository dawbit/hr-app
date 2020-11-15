// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'response_transfer.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ResponseTransfer _$ResponseTransferFromJson(Map<String, dynamic> json) {
  return ResponseTransfer(
    message: json['message'] as String,
    exception: json['exception'] as String,
  );
}

Map<String, dynamic> _$ResponseTransferToJson(ResponseTransfer instance) =>
    <String, dynamic>{
      'message': instance.message,
      'exception': instance.exception,
    };
