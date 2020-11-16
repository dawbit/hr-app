// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_panel_source.dart';

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

class _UserPanelSource implements UserPanelSource {
  _UserPanelSource(this._dio, {this.baseUrl}) {
    ArgumentError.checkNotNull(_dio, '_dio');
    baseUrl ??= 'http://192.168.43.228:8080/user';
  }

  final Dio _dio;

  String baseUrl;

  @override
  Future<UserPanelListOfAnnoncementsDto> getListOfApplications() async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.request<Map<String, dynamic>>(
        '/list-of-applications',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'POST',
            headers: <String, dynamic>{},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    final value = UserPanelListOfAnnoncementsDto.fromJson(_result.data);
    return value;
  }
}
