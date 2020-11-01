import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:mobile/values/sizes.dart';

class AnnouncementCardWidget extends StatelessWidget {

  final String companyImage = 'https://images.unsplash.com/photo-1435575653489-b0873ec954e2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80';

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.red,
      height: 120,
      margin: EdgeInsets.all(Sizes.giantSpace),
      child: Row(
        children: [
          Expanded(
              flex: 2,
              child: Container(
                height: 100,
                width: 30,
                decoration: BoxDecoration(
                  border: Border.all(),
                  borderRadius: BorderRadius.all(Radius.circular(5)),
                ),
                  margin: EdgeInsets.all(10),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(5),
                    child: Image.network(companyImage,
                      fit: BoxFit.fitHeight,
                    ),
                  )
              )
          ),
          Expanded(
              flex: 3,
              child: Column(
                children: [
                  Text("xDDDDDDDDDDD")
                ],
              )
          )
        ],
      ),
    );
  }
}
