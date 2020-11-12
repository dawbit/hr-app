import 'package:dio/dio.dart';
import 'package:mobile/security/token_shared_pref.dart';
import 'package:mobile/utils/logger.dart';
import 'package:rxdart/rxdart.dart';


Dio _apiClient;

PublishSubject _logoutSubject = PublishSubject();
Stream get logoutStream => _logoutSubject.stream;

logoutCurrentUser() => _logoutSubject.add(null);

Dio getApiClient() {
  if (_apiClient == null) {
    _apiClient = Dio();
    _apiClient.interceptors.add(_interceptor);
  }
  return _apiClient;
}

InterceptorsWrapper get _interceptor => InterceptorsWrapper(
    onRequest: _onRequest,
    onResponse: _onResponse,
    onError: _onError
);

Future<RequestOptions> _onRequest(RequestOptions options) async {
  _apiClient.lock();
  logger.i('Request: ${options.path}');
  String token = await TokenSharedPref.getToken();
  if (token == null) {
    _apiClient.clear();
    _apiClient.unlock();
    _logoutSubject.add(null);
    return options;
  }
  _apiClient.unlock();
  return options..headers['Authorization'] = '$token';
}

Future<Response> _onResponse(Response response) async {
  logger.i('Response: (${response.statusCode}) - ${response.request.path}');
  return response;
}

Future<DioError> _onError(DioError error) async {
  logger.w('Error: (${error.request.path})', error.error);
  return error;
}
