// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'announcements_source.dart';

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

class _AnnouncementsSource implements AnnouncementsSource {
  _AnnouncementsSource(this._dio, {this.baseUrl}) {
    ArgumentError.checkNotNull(_dio, '_dio');
    baseUrl ??= 'http://192.168.43.228:8080/announcements';
  }

  final Dio _dio;

  String baseUrl;

  @override
  Future<List<AnnouncementsDto>> getAnnouncements(query) async {
    ArgumentError.checkNotNull(query, 'query');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{r'q': query};
    final _data = <String, dynamic>{};
    final _result = await _dio.request<List<dynamic>>('/find',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'GET',
            headers: <String, dynamic>{},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    var value = _result.data
        .map(
            (dynamic i) => AnnouncementsDto.fromJson(i as Map<String, dynamic>))
        .toList();
    return value;
  }

  @override
  Future<ResponseTransfer> announcementApply(id) async {
    ArgumentError.checkNotNull(id, 'id');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.request<Map<String, dynamic>>('/apply/$id',
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
