import 'package:flutter/material.dart';

import 'bottom_clipper.dart';

class TopClipPath extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ClipPath(
      clipper: RoundedBottomClipper(),
      child: Container(
        width: MediaQuery.of(context).size.width,
        color: Theme.of(context).accentColor
      ),
    );
  }
}
