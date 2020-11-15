import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/models/announcements_dto.dart';
import 'package:mobile/repositories/announcements_repository.dart';
import 'package:rxdart/rxdart.dart';

class AnnouncementsBloc extends BlocBase {

  AnnouncementsRepository announcementsRepository;

  PublishSubject<String> _searchSubject = PublishSubject();

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<List<AnnouncementsDto>> _announcementsResponseSubject = PublishSubject();
  Stream<List<AnnouncementsDto>> get announcementsResponseObservable => _announcementsResponseSubject.stream;

  AnnouncementsBloc(this.announcementsRepository){
    _searchSubject
        .debounceTime(Duration(seconds: 1))
        ..listen((event) async {
          if(event.length >2) {
            _isLoadingSubject.add(true);
            announcementsRepository.getAnnouncements(event).then(_onSuccess).catchError(_onError);
          } else {
            _announcementsResponseSubject.add(null);
          }
    });
  }

  Function(String title) get getAnnouncements => _searchSubject.add;

  void _onSuccess(List<AnnouncementsDto> announcementsDto) {
    _announcementsResponseSubject.add(announcementsDto);
    _isLoadingSubject.add(false);
  }

  void _onError(e) {
    _isLoadingSubject.add(false);
    print("Answer error: ${e.toString()}");
  }
}