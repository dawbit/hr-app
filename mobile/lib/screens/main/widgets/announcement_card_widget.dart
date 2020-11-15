import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:mobile/models/announcements_dto.dart';
import 'package:mobile/values/sizes.dart';

class AnnouncementCardWidget extends StatelessWidget {

  final String companyImage = 'https://images.unsplash.com/photo-1435575653489-b0873ec954e2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80';
  final AnnouncementsDto data;

  AnnouncementCardWidget({@required this.data});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: Colors.red,
        borderRadius: BorderRadius.all(Radius.circular(10)),
      ),
      height: 120,
      padding:EdgeInsets.all(10),
      margin: EdgeInsets.all(Sizes.smallSpace),
      child: InkWell(
        onTap: (){Navigator.of(context).pushNamed("/two");},
        child: Row(
          children: [
            Expanded(
                flex: 0,
                child: Container(
                  margin: EdgeInsets.only(right: 10),
                  height: 100,
                  width: 100,
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(5),
                      child: Image.network(companyImage,
                        fit: BoxFit.fitHeight,
                      ),
                    )
                )
            ),
            Expanded(
                flex: 1,
                child: Column(
                  mainAxisSize: MainAxisSize.max,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text("Company: ${data.companyName}" , maxLines: 1, overflow: TextOverflow.ellipsis,),
                    Text("Ogloszenie: ${data.announcementTitle}" , maxLines: 1, overflow: TextOverflow.ellipsis,),
                    SizedBox(width: 40,),
                    Divider(height: 10, color: Colors.blue,),
                    Flexible(
                        child: LayoutBuilder(
                          builder: (context, constraints) {
                            double textSize = Theme.of(context).textTheme.bodyText2.fontSize*1.1;
                            int maxLines = (constraints.biggest.height/textSize).floor();
                            return Text("O ogloszeniu: ${data.announcementDescription}",
                              overflow: TextOverflow.ellipsis, maxLines: maxLines,);
                          }
                        )
                    )
                  ],
                )
            )
          ],
        ),
      ),
    );
  }
}
