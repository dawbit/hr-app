import 'package:flutter/material.dart';
import 'package:mobile/values/sizes.dart';

class SubAccountCardWidget extends StatelessWidget {
  final Icon icon;
  final String title;
  final Function onTapFunction;

  SubAccountCardWidget({this.icon, this.title, this.onTapFunction});

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
        onTap: () => onTapFunction(),
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
                  child: Text(title)
              ),
            ],
          ),
        ),
      ),
    );
  }
}
