import 'dart:async';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/enums/request_state.dart';
import 'package:mobile/models/user_panel_list_of_annoncements_dto.dart';
import 'package:mobile/repositories/notifications_repository.dart';
import 'package:rxdart/rxdart.dart';

class NotificationsBloc extends BlocBase {
  NotificationsRepository notificationsRepository;

  PublishSubject<RequestState> _isLoadingSubject = PublishSubject();
  Stream<RequestState> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<List<UserPanelListOfAnnoncementsDto>> _notificationsResponseSubject = PublishSubject();
  Stream<List<UserPanelListOfAnnoncementsDto>> get notificationsResponseObservable => _notificationsResponseSubject.stream;

  PublishSubject<Object> _errorResponseSubject = PublishSubject();
  Stream<Object> get errorResponseObservable => _errorResponseSubject.stream;

  NotificationsBloc(this.notificationsRepository);

  Future getListOfNotifications() async {
    _isLoadingSubject.add(RequestState.LOADING);
    notificationsRepository.getListOfNotifications().then(_onSuccess).catchError(_onError);
  }

  void _onSuccess(List<UserPanelListOfAnnoncementsDto> listofUserPanelListOfAnnoncementsDto) {
    _notificationsResponseSubject.add(listofUserPanelListOfAnnoncementsDto);
    _isLoadingSubject.add(RequestState.OK);
  }

  void _onError(Object obj) {
    _isLoadingSubject.add(RequestState.ERROR);
    _errorResponseSubject.add(obj);
  }
}