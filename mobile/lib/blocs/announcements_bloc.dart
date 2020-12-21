import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:dio/dio.dart';
import 'package:mobile/enums/errorType.dart';
import 'package:mobile/enums/request_state.dart';
import 'package:mobile/models/announcements_dto.dart';
import 'package:mobile/repositories/announcements_repository.dart';
import 'package:rxdart/rxdart.dart';

class AnnouncementsBloc extends BlocBase {

  AnnouncementsRepository announcementsRepository;

  PublishSubject<String> _searchSubject = PublishSubject();

  PublishSubject<RequestState> _isLoadingSubject = PublishSubject();
  Stream<RequestState> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<List<AnnouncementsDto>> _announcementsResponseSubject = PublishSubject();
  Stream<List<AnnouncementsDto>> get announcementsResponseObservable => _announcementsResponseSubject.stream;

  PublishSubject<ErrorType> _errorResponseSubject = PublishSubject();
  Stream<ErrorType> get errorResponseObservable => _errorResponseSubject.stream;

  AnnouncementsBloc(this.announcementsRepository){
    _searchSubject
        .debounceTime(Duration(seconds: 1))
        ..listen((event) async {
          if(event.length >2) {
            _isLoadingSubject.add(RequestState.LOADING);
            _errorResponseSubject.add(null);
            announcementsRepository.getAnnouncements(event).then(_onSuccess).catchError(_onError);
          } else {
            _announcementsResponseSubject.add(null);
          }
    });
  }

  Function(String title) get getAnnouncements => _searchSubject.add;

  void _onSuccess(List<AnnouncementsDto> announcementsDto) {
    _isLoadingSubject.add(RequestState.OK);
    _announcementsResponseSubject.add(announcementsDto);
  }

  void _onError(Object obj) {
    final dioError = obj as DioError;
    final res = dioError.response;
    if((obj as DioError).error.toString().toLowerCase().contains("connection failed")) {
      _errorResponseSubject.add(ErrorType.CONNECTION_ERROR);
    }
    else if(res.statusCode >= 500) {
      _errorResponseSubject.add(ErrorType.SERVER_ERROR);
    }
    _isLoadingSubject.add(RequestState.ERROR);
    print("Answer error: ${obj.toString()}");
  }
}