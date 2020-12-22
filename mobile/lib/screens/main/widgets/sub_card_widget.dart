import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/Application.dart';
import 'package:mobile/models/user_panel_list_of_annoncements_dto.dart';
import 'package:mobile/values/sizes.dart';

class SubCardWidget extends StatelessWidget {

  final Icon icon;
  final UserPanelListOfAnnoncementsDto userPanelListOfAnnoncementsDto;

  SubCardWidget({this.icon, this.userPanelListOfAnnoncementsDto});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: Sizes.CardWidgetSize,
      decoration: BoxDecoration(
          color: Theme.of(context).accentColor,
          //border: Border.symmetric(horizontal: BorderSide(width: 1, color: Colors.blue, style: BorderStyle.solid),),
          border: Border(bottom: BorderSide(width: 1, color: Colors.black))
      ),
      child: InkWell(
        onTap: (){Navigator.of(navigationKey.currentContext).pushNamed('/notificationsDetails', arguments: userPanelListOfAnnoncementsDto);},
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
                  child: Text(userPanelListOfAnnoncementsDto.announcementName)
              ),
            ],
          ),
        ),
      ),
    );
  }
}
