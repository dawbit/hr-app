// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'upload_file_response_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UploadFileResponseDto _$UploadFileResponseDtoFromJson(
    Map<String, dynamic> json) {
  return UploadFileResponseDto(
    fileType: json['fileType'] as String,
    fileName: json['fileName'] as String,
    fileDownloadUri: json['fileDownloadUri'] as String,
    size: (json['size'] as num)?.toDouble(),
  );
}

Map<String, dynamic> _$UploadFileResponseDtoToJson(
        UploadFileResponseDto instance) =>
    <String, dynamic>{
      'fileName': instance.fileName,
      'fileDownloadUri': instance.fileDownloadUri,
      'fileType': instance.fileType,
      'size': instance.size,
    };
