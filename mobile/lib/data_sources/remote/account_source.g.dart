// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'account_source.dart';

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

class _AccountSource implements AccountSource {
  _AccountSource(this._dio, {this.baseUrl}) {
    ArgumentError.checkNotNull(_dio, '_dio');
    baseUrl ??= 'http://192.168.43.228:8080/user';
  }

  final Dio _dio;

  String baseUrl;

  @override
  Future<ResponseTransfer> changePassowrd(changePasswordCommandDto) async {
    ArgumentError.checkNotNull(
        changePasswordCommandDto, 'changePasswordCommandDto');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    _data.addAll(changePasswordCommandDto?.toJson() ?? <String, dynamic>{});
    _data.removeWhere((k, v) => v == null);
    final _result = await _dio.request<Map<String, dynamic>>('/change-password',
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
  Future<ResponseTransfer> changePhoneNumber(
      changePhoneNumberCommandDto) async {
    ArgumentError.checkNotNull(
        changePhoneNumberCommandDto, 'changePhoneNumberCommandDto');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    _data.addAll(changePhoneNumberCommandDto?.toJson() ?? <String, dynamic>{});
    _data.removeWhere((k, v) => v == null);
    final _result = await _dio.request<Map<String, dynamic>>(
        '/change-phonenumber',
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
  Future<ResponseTransfer> changeEmail(changeEmailCommandDto) async {
    ArgumentError.checkNotNull(changeEmailCommandDto, 'changeEmailCommandDto');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    _data.addAll(changeEmailCommandDto?.toJson() ?? <String, dynamic>{});
    _data.removeWhere((k, v) => v == null);
    final _result = await _dio.request<Map<String, dynamic>>('/change-email',
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
}
