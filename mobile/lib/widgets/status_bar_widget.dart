import 'package:flutter/material.dart';

class StatusBarWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: MediaQuery.of(context).padding.top,
      color: Colors.green,
    );
  }
}
