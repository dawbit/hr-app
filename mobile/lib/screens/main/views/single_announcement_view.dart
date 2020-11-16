import 'package:flutter/material.dart';
import 'package:mobile/models/announcements_dto.dart';

class SingleAnnouncementView extends StatelessWidget {

  final AnnouncementsDto announcementsDto;

  SingleAnnouncementView(this.announcementsDto);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          Expanded(
            flex: 2,
            child: Container(
              color: Colors.red,
              child: Column(
                children: [
                  Expanded(
                    flex: 0,
                      child: SizedBox(
                        height: 20,
                      )
                  ),
                  Expanded(
                    flex: 1,
                    child: Row(
                      children: [
                        Expanded(
                            flex: 1,
                            child: SizedBox()
                        ),
                        Expanded(
                          flex: 4,
                            child: Text("Firma: ${announcementsDto.companyName}", textAlign: TextAlign.start,)
                        ),
                    ]
                    ),
                  ),
                  Expanded(
                    flex: 1,
                    child: Row(
                        children: [
                          Expanded(
                              flex: 1,
                              child: SizedBox()
                          ),
                          Expanded(
                              flex: 4,
                              child: Text("Ogłoszenie: ${announcementsDto.announcementTitle}", textAlign: TextAlign.start,)
                          ),
                        ]
                    ),
                  ),
                  Expanded(
                      flex: 0,
                      child: SizedBox(
                        height: 20,
                      )
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            flex: 6,
            child: Container(
              margin: EdgeInsets.all(30),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Expanded(
                    flex: 0,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text("Lokalizacja firmy: ${announcementsDto.companyLocation}"),
                          SizedBox(height: 20,),
                          Text("O firmie: ${announcementsDto.companyAbout}"),
                          SizedBox(height: 20,),
                        ],
                      )
                  ),
                  Expanded(
                      flex: 0,
                      child: Divider(height: 10,thickness: 1.5,color: Colors.grey, )
                  ),
                  Expanded(
                      flex: 1,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Expanded(
                              flex: 1,
                              child: Text("O ogłoszeniu: ${announcementsDto.announcementDescription}")
                          ),
                          Expanded(
                              flex: 0,
                              child: MaterialButton(
                                onPressed: (){},
                                child: Container(
                                  width: MediaQuery.of(context).size.width,
                                  height: 50,
                                  child: Center(child: Text("Aplikuj")),
                                ),
                              )
                          ),
                        ],
                      )
                  ),
                ],
              ),
            )
          ),
        ],
      )
    );
  }
}
