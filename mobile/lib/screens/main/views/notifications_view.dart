import 'package:flutter/material.dart';
import 'package:mobile/screens/main/widgets/card_widget.dart';
import 'package:mobile/screens/main/widgets/sub_card_widget.dart';

class NotificationsView extends StatefulWidget {
  @override
  _NotificationsViewState createState() => _NotificationsViewState();
}

class _NotificationsViewState extends State<NotificationsView> {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.purple,
      child: Column(
        children: [
          CardWidget(icon: Icon(Icons.notifications), cardTitle: "Powiadomienia",),
          SubCardWidget(icon: Icon(Icons.notifications), cardTitle: "Powiadomienia",),
          SubCardWidget(icon: Icon(Icons.notifications), cardTitle: "Powiadomienia",),
          SubCardWidget(icon: Icon(Icons.notifications), cardTitle: "Powiadomienia",),
          SubCardWidget(icon: Icon(Icons.notifications), cardTitle: "Powiadomienia",),
      ],
      ),
    );
  }
}
