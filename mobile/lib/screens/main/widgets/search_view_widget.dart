import 'package:flutter/material.dart';
import 'package:mobile/blocs/announcements_bloc.dart';
import 'package:mobile/injections/app_module.dart';

class SearchViewWidget extends StatefulWidget {
  @override
  _SearchViewWidgetState createState() => _SearchViewWidgetState();
}

class _SearchViewWidgetState extends State<SearchViewWidget> {

  TextEditingController _textEditingController = TextEditingController();

  AnnouncementsBloc announcementsBloc;

  @override
  void initState() {
    super.initState();
    announcementsBloc = AppModule.injector.getBloc();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: ListTile(
        title: TextField(
          onChanged: (text) => announcementsBloc.getAnnouncements(text),
          autofocus: false,
          controller: _textEditingController,
          decoration: InputDecoration(
            fillColor: Theme.of(context).focusColor,
            border: InputBorder.none,
          ),
        ),
      ),
    );
  }
}
