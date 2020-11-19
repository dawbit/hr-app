import 'package:mobile/data_sources/remote/announcements_source.dart';
import 'package:mobile/models/announcements_dto.dart';
import 'package:mobile/models/response_transfer.dart';

class AnnouncementsRepository {
  AnnouncementsSource _announcementsSource;

  AnnouncementsRepository(this._announcementsSource);

  Future<List<AnnouncementsDto>> getAnnouncements(String query) =>
      _announcementsSource.getAnnouncements(query);

  Future<ResponseTransfer> announcementApply(int id) => _announcementsSource.announcementApply(id);
}