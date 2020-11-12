import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/data_sources/remote/api_client/api_provider.dart';
import 'package:mobile/values/sizes.dart';

class CardWidget extends StatelessWidget {

  final Icon icon;
  final String cardTitle;

  CardWidget({this.icon, this.cardTitle});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: Sizes.CardWidgetSize,
      decoration: BoxDecoration(
        color: Colors.red,
          //border: Border.symmetric(horizontal: BorderSide(width: 1, color: Colors.blue, style: BorderStyle.solid),),
        border: Border(bottom: BorderSide(width: 1, color: Colors.blue))
      ),
      child: InkWell(
        onTap: (){logoutCurrentUser();},
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
                child: Text(cardTitle)
            ),
          ],
        ),
      ),
    );
  }
}
