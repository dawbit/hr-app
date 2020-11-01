import 'package:flutter/material.dart';
import 'package:mobile/screens/main/widgets/announcement_card_widget.dart';
import 'package:mobile/screens/main/widgets/search_view_widget.dart';

class AnnouncementsContent extends StatefulWidget {
  @override
  _AnnouncementsContentState createState() => _AnnouncementsContentState();
}

class _AnnouncementsContentState extends State<AnnouncementsContent> {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          Expanded(
            flex: 0,
              child: SearchViewWidget()
          ),
          Expanded(
              flex: 1,
              child: Container(
                child: ListView(
                  children: [
                    AnnouncementCardWidget(),
                    AnnouncementCardWidget(),
                    AnnouncementCardWidget(),
                    AnnouncementCardWidget(),
                    AnnouncementCardWidget(),
                    AnnouncementCardWidget(),
                    AnnouncementCardWidget(),
                    AnnouncementCardWidget(),
                    AnnouncementCardWidget(),
                    AnnouncementCardWidget(),
                  ],
                ),
              )
          )
        ],
      ),
    );
  }
}
