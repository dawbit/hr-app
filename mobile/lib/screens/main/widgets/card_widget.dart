import 'package:flutter/material.dart';
import 'package:mobile/values/sizes.dart';

class CardWidget extends StatelessWidget {

  final Icon icon;

  CardWidget({this.icon});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: Sizes.giantSize,
      color: Colors.blue,
      child: Row(
        children: [
          Expanded(
              flex: 0,
              child: Container(
                padding: EdgeInsets.symmetric(horizontal: Sizes.bigSpace),
                child: icon,
              )
          ),
          Expanded(
              flex: 3,
              child: Text("Jakis tekst elementu")
          ),
        ],
      ),
    );
  }
}
