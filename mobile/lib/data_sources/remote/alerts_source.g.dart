// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'alerts_source.dart';

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

class _AlertsSource implements AlertsSource {
  _AlertsSource(this._dio, {this.baseUrl}) {
    ArgumentError.checkNotNull(_dio, '_dio');
    baseUrl ??= 'http://192.168.43.228:8080/alerts';
  }

  final Dio _dio;

  String baseUrl;

  @override
  Future<int> getAnnouncements() async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.request<int>('/user',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'GET',
            headers: <String, dynamic>{},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    final value = _result.data;
    return value;
  }

  @override
  Future<ResponseTransfer> announcementApply(testParticipantId) async {
    ArgumentError.checkNotNull(testParticipantId, 'testParticipantId');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.request<Map<String, dynamic>>(
        '/user/$testParticipantId/setAsRead',
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
