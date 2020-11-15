import 'package:flutter/material.dart';
import 'package:mobile/screens/main/widgets/card_widget.dart';

class AccountContent extends StatefulWidget {
  @override
  _AccountContentState createState() => _AccountContentState();
}

class _AccountContentState extends State<AccountContent> {
  @override
  Widget build(BuildContext context) {
    return Container(color: Colors.purple,
    child: Column(
      children: [
        CardWidget(icon: Icon(Icons.person), cardTitle: "hehe",),
        CardWidget(icon: Icon(Icons.person), cardTitle: "hehe",),
        CardWidget(icon: Icon(Icons.person), cardTitle: "hehe",),
        CardWidget(icon: Icon(Icons.person), cardTitle: "hehe",),
        CardWidget(icon: Icon(Icons.person), cardTitle: "hehe",),
        CardWidget(icon: Icon(Icons.person), cardTitle: "hehe",),
      ],
    ),
    );
  }
}
