import 'dart:async';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/models/user_panel_list_of_annoncements_dto.dart';
import 'package:mobile/repositories/notifications_repository.dart';
import 'package:rxdart/rxdart.dart';

class NotificationsBloc extends BlocBase {
  NotificationsRepository notificationsRepository;

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<List<UserPanelListOfAnnoncementsDto>> _notificationsResponseSubject = PublishSubject();
  Stream<List<UserPanelListOfAnnoncementsDto>> get notificationsResponseObservable => _notificationsResponseSubject.stream;

  NotificationsBloc(this.notificationsRepository);

  Future getListOfNotifications() async {
    _isLoadingSubject.add(true);
    notificationsRepository.getListOfNotifications().then(_onSuccess).catchError(_onError);
  }

  void _onSuccess(List<UserPanelListOfAnnoncementsDto> listofUserPanelListOfAnnoncementsDto) {
    _notificationsResponseSubject.add(listofUserPanelListOfAnnoncementsDto);
    _isLoadingSubject.add(false);
  }

  void _onError(e) {
    _isLoadingSubject.add(false);
    print("Answer error: ${e.toString()}");
  }
}