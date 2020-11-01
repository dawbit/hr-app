import 'package:flutter/material.dart';
import 'package:mobile/values/sizes.dart';

class SearchViewWidget extends StatefulWidget {
  @override
  _SearchViewWidgetState createState() => _SearchViewWidgetState();
}

class _SearchViewWidgetState extends State<SearchViewWidget> {

  TextEditingController _textEditingController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Container(
      height: Sizes.giantSize,
      child: ListTile(
        title: TextField(
          autofocus: false,
          controller: _textEditingController,
          decoration: InputDecoration(
            fillColor: Theme.of(context).focusColor,
            border: InputBorder.none,
          ),
        ),
        trailing: IconButton(
          onPressed: () {},
          icon: Icon(Icons.search),
        )
      ),
    );
  }
}
