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

  PublishSubject<String> _announcementsResponseSubject = PublishSubject();
  Stream<String> get announcementsResponseObservable => _announcementsResponseSubject.stream;

  AnnouncementApplyBloc(this.announcementsRepository);

  Future announcementApply(AnnouncementsDto announcementsDto) async {
    _isLoadingSubject.add(true);

    announcementsRepository.announcementApply(announcementsDto.announcementId).then(_onSuccess).catchError(_onError);
  }

  void _onSuccess(ResponseTransfer responseTransfer) {
    _announcementsResponseSubject.add("Prośba została wysłana");
    _isLoadingSubject.add(false);
  }

  void _onError(Object obj) {
    _isLoadingSubject.add(false);
    _announcementsResponseSubject.add("Nie można wysłać już prośby");
    print("Answer error: ${obj.toString()}");
  }
}