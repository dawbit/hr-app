// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'authorization_source.dart';

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

class _AuthorizationSource implements AuthorizationSource {
  _AuthorizationSource(this._dio, {this.baseUrl}) {
    ArgumentError.checkNotNull(_dio, '_dio');
    baseUrl ??= 'http://192.168.43.228:8080';
  }

  final Dio _dio;

  String baseUrl;

  @override
  Future<HttpResponse<dynamic>> attemptToLogin(loginCommandDto) async {
    ArgumentError.checkNotNull(loginCommandDto, 'loginCommandDto');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    _data.addAll(loginCommandDto?.toJson() ?? <String, dynamic>{});
    _data.removeWhere((k, v) => v == null);
    final _result = await _dio.request('/login',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'POST',
            headers: <String, dynamic>{},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    final value = _result.data;
    final httpResponse = HttpResponse(value, _result);
    return httpResponse;
  }

  @override
  Future<ResponseTransfer> attemptToRegister(registerCommandDto) async {
    ArgumentError.checkNotNull(registerCommandDto, 'registerCommandDto');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    _data.addAll(registerCommandDto?.toJson() ?? <String, dynamic>{});
    _data.removeWhere((k, v) => v == null);
    final _result = await _dio.request<Map<String, dynamic>>('/user/register',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'POST',
            headers: <String, dynamic>{},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    final value = ResponseTransfer.fromJson(_result.data);
    return value;
  }

  @override
  Future<UserDataDto> getUserData(token) async {
    ArgumentError.checkNotNull(token, 'token');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.request<Map<String, dynamic>>('/user/getdata',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'GET',
            headers: <String, dynamic>{r'Authorization': token},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    final value = UserDataDto.fromJson(_result.data);
    return value;
  }
}
