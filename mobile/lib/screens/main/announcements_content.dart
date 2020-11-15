import 'package:flutter/material.dart';
import 'package:mobile/models/quiz_information_dto.dart';
import 'package:mobile/screens/main/views/announcement_search_view.dart';
import 'package:mobile/screens/main/views/single_announcement_view.dart';
import 'package:mobile/widgets/nested_navigator.dart';

import '../../Application.dart';

class AnnouncementsContent extends StatefulWidget {
  @override
  _AnnouncementsContentState createState() => _AnnouncementsContentState();
}

class _AnnouncementsContentState extends State<AnnouncementsContent> {

  QuizInformationDto quizInformationDto = QuizInformationDto();

  @override
  void initState() {
    super.initState();
    navigationKey= GlobalKey<NavigatorState>();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: NestedNavigator(
        navigationKey: navigationKey,
        initialRoute: '/',
        routes: {
          '/': (context) => AnnouncementSearchView(),
          '/two': (context) => SingleAnnouncementView(),
        },
      ),
    );
  }
}