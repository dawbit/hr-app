// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'quiz_source.dart';

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

class _QuizSource implements QuizSource {
  _QuizSource(this._dio, {this.baseUrl}) {
    ArgumentError.checkNotNull(_dio, '_dio');
    baseUrl ??= 'http://192.168.43.228:8080';
  }

  final Dio _dio;

  String baseUrl;

  @override
  Future<QuizInformationDto> getQuizInformation(quizcode) async {
    ArgumentError.checkNotNull(quizcode, 'quizcode');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.request<Map<String, dynamic>>(
        '/quiz/getQuizInformations/$quizcode',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'GET',
            headers: <String, dynamic>{},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    final value = QuizInformationDto.fromJson(_result.data);
    return value;
  }

  @override
  Future<QuestionResultDto> getQuizQuestion(
      quizId, testCode, questionNumber) async {
    ArgumentError.checkNotNull(quizId, 'quizId');
    ArgumentError.checkNotNull(testCode, 'testCode');
    ArgumentError.checkNotNull(questionNumber, 'questionNumber');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.request<Map<String, dynamic>>(
        '/quiz/quizquestion/$quizId/$testCode/$questionNumber',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'GET',
            headers: <String, dynamic>{},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    final value = QuestionResultDto.fromJson(_result.data);
    return value;
  }

  @override
  Future<ResponseTransfer> setAnswer(answerCommandDto) async {
    ArgumentError.checkNotNull(answerCommandDto, 'answerCommandDto');
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _data = <String, dynamic>{};
    _data.addAll(answerCommandDto?.toJson() ?? <String, dynamic>{});
    _data.removeWhere((k, v) => v == null);
    final _result = await _dio.request<Map<String, dynamic>>(
        '/question/setanswer',
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
