import 'package:flutter/material.dart';
import 'package:mobile/localizations/app_localization.dart';

class NoDataWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Expanded(
            flex: 1,
            child: SizedBox()
        ),
        Expanded(
            flex: 0,
            child: Container(
              child: Column(
                children: [
                  Icon(Icons.work_off, size: 50,),
                  SizedBox(height: 10,),
                  Text(Lang.of(context).translate("nothing_found")),
                  SizedBox(height: 10,),
                ],
              ),
            )
        ),
        Expanded(
            flex: 1,
            child: SizedBox()
        ),
      ],
    );
  }
}
