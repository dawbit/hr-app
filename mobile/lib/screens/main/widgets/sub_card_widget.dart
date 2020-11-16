import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/values/sizes.dart';

class SubCardWidget extends StatelessWidget {

  final Icon icon;
  final String cardTitle;
  final Function onClick;

  SubCardWidget({this.icon, this.cardTitle, this.onClick});

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
        onTap: (){onClick();},
        child: Container(
          margin: EdgeInsets.only(left: 30),
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
      ),
    );
  }
}
