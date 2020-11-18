import 'package:mobile/data_sources/remote/user_panel_source.dart';
import 'package:mobile/models/user_panel_list_of_annoncements_dto.dart';

class NotificationsRepository {
  UserPanelSource _userPanelSource;

  NotificationsRepository(this._userPanelSource);

  Future<List<UserPanelListOfAnnoncementsDto>> getListOfNotifications() => _userPanelSource.getListOfApplications();
}