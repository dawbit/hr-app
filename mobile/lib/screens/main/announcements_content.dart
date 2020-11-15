import 'package:flutter/material.dart';
import 'package:mobile/blocs/announcements_bloc.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/screens/main/views/announcement_search_view.dart';
import 'package:mobile/screens/main/views/single_announcement_view.dart';
import 'package:mobile/widgets/nested_navigator.dart';

GlobalKey<NavigatorState> navigationKey= GlobalKey<NavigatorState>();

class AnnouncementsContent extends StatefulWidget {
  @override
  _AnnouncementsContentState createState() => _AnnouncementsContentState();
}

class _AnnouncementsContentState extends State<AnnouncementsContent> {

  AnnouncementsBloc _announcementsBloc;

  @override
  void initState() {
    super.initState();
    _announcementsBloc = AppModule.injector.getBloc();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: NestedNavigator(
        navigationKey: navigationKey,
        initialRoute: '/',
        routes: {
          // default rout as '/' is necessary!
          '/': (context) => AnnouncementSearchView(),
          '/two': (context) => SingleAnnouncementView(),
        },
      ),
    );
  }
}