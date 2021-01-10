import 'dart:io';

import 'package:dio/dio.dart';
import 'package:mobile/models/upload_file_response_dto.dart';
import 'package:retrofit/http.dart';
import 'package:retrofit/retrofit.dart';

part 'cvs_source.g.dart';

@RestApi(baseUrl: "http://192.168.43.228:8080/cvs")
abstract class CvsSource{

  factory CvsSource(Dio dio, {String baseUrl})= _CvsSource;

  @POST("/uploadCv")
  @MultiPart()
  Future<UploadFileResponseDto> attemptToSendCv(@Part(name: "file") File file);
}