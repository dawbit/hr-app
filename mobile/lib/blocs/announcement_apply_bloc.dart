import 'dart:async';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/models/announcements_dto.dart';
import 'package:mobile/models/response_transfer.dart';
import 'package:mobile/repositories/announcements_repository.dart';
import 'package:rxdart/rxdart.dart';

class AnnouncementApplyBloc extends BlocBase {
  AnnouncementsRepository announcementsRepository;

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject _announcementsResponseSubject = PublishSubject();
  Stream get announcementsResponseObservable => _announcementsResponseSubject.stream;

  PublishSubject<Object> _errorResponseSubject = PublishSubject();
  Stream<Object> get errorResponseObservable => _errorResponseSubject.stream;

  AnnouncementApplyBloc(this.announcementsRepository);

  Future announcementApply(AnnouncementsDto announcementsDto) async {
    _isLoadingSubject.add(true);

    announcementsRepository.announcementApply(announcementsDto.announcementId).then(_onSuccess).catchError(_onError);
  }

  void _onSuccess(ResponseTransfer responseTransfer) {
    _announcementsResponseSubject.add(null);
    _isLoadingSubject.add(false);
  }

  void _onError(Object obj) {
    _isLoadingSubject.add(false);
    _errorResponseSubject.add(obj);
    print("Answer error: ${obj.toString()}");
  }
}