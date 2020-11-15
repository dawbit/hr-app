import 'package:mobile/data_sources/remote/announcements_source.dart';
import 'package:mobile/models/announcements_dto.dart';

class AnnouncementsRepository {
  AnnouncementsSource _announcementsSource;

  AnnouncementsRepository(this._announcementsSource);

  Future<List<AnnouncementsDto>> getAnnouncements(String query) =>
      _announcementsSource.getAnnouncements(query);
}