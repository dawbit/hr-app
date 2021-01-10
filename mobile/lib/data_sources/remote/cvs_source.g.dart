// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'cvs_source.dart';

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

class _CvsSource implements CvsSource {
  _CvsSource(this._dio, {this.baseUrl}) {
    ArgumentError.checkNotNull(_dio, '_dio');
    baseUrl ??= 'http://192.168.43.228:8080/cvs';
  }

  final Dio _dio;

  String baseUrl;

  @override
  Future<UploadFileResponseDto> attemptToSendCv(file) async {
    ArgumentError.checkNotNull(file, 'file');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = FormData();
    _data.files.add(MapEntry(
        'file',
        MultipartFile.fromFileSync(file.path,
            filename: file.path.split(Platform.pathSeparator).last)));
    final _result = await _dio.request<Map<String, dynamic>>('/uploadCv',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'POST',
            headers: <String, dynamic>{},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    final value = UploadFileResponseDto.fromJson(_result.data);
    return value;
  }
}
