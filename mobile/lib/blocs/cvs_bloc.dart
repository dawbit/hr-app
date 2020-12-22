import 'dart:io';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/models/upload_file_response_dto.dart';
import 'package:mobile/repositories/cvs_repository.dart';
import 'package:rxdart/rxdart.dart';

class CvsBloc extends BlocBase {

  CvsRepository cvsRepository;

  CvsBloc(this.cvsRepository);

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<UploadFileResponseDto> _answerResponseSubject = PublishSubject();
  Stream<UploadFileResponseDto> get answerResponseObservable => _answerResponseSubject.stream;

  PublishSubject<Object> _errorSubject = PublishSubject();
  Stream<Object> get errorObservable => _errorSubject.stream;

  Future attemptToSendCv(File file) async {
    _isLoadingSubject.add(true);
    cvsRepository.attemptToSendCv(file).then(_onSuccess).catchError(_onError);
  }

  void _onSuccess(UploadFileResponseDto uploadFileResponseDto) {
    _answerResponseSubject.add(uploadFileResponseDto);
    _isLoadingSubject.add(false);
  }

  void _onError(Object obj) {
    _isLoadingSubject.add(false);
    _errorSubject.add(obj);
  }
}