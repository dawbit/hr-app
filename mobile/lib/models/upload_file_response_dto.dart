import 'package:json_annotation/json_annotation.dart';

part 'upload_file_response_dto.g.dart';

@JsonSerializable()
class UploadFileResponseDto {

  @JsonKey(name: "fileName")
  String fileName;

  @JsonKey(name: "fileDownloadUri")
  String fileDownloadUri;

  @JsonKey(name: "fileType")
  String fileType;

  @JsonKey(name: "size")
  double size;

  UploadFileResponseDto({this.fileType, this.fileName, this.fileDownloadUri, this.size});

  factory UploadFileResponseDto.fromJson(Map<String, dynamic> json) => _$UploadFileResponseDtoFromJson(json);

  Map<String, dynamic> toJson() => _$UploadFileResponseDtoToJson(this);
}