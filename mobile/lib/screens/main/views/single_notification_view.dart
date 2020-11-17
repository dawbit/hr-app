import 'package:flutter/material.dart';
import 'package:mobile/models/user_panel_list_of_annoncements_dto.dart';
import 'package:mobile/screens/main/widgets/test_code_warning_dialog.dart';

class SingleNotificationView extends StatelessWidget {

  final UserPanelListOfAnnoncementsDto userPanelListOfAnnoncementsDto;

  SingleNotificationView({this.userPanelListOfAnnoncementsDto});

  @override
  Widget build(BuildContext context) {
    return Container(
        color: Colors.white,
        child: Stack(
          children: [
            Column(
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
                                    child: Text("Firma: ${userPanelListOfAnnoncementsDto.companyName}", textAlign: TextAlign.start,)
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
                                    child: Text("Ogłoszenie: ${userPanelListOfAnnoncementsDto.announcementName}", textAlign: TextAlign.start,)
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
                    child: Column(
                      children: [
                        Expanded(
                            flex: 1,
                            child: SizedBox()
                        ),
                        Expanded(
                            flex: 0,
                            child: Text("Quiz Code")
                        ),
                        Expanded(
                          flex: 0,
                          child: Container(
                            width: MediaQuery.of(context).size.width,
                            height: 100,
                            margin: EdgeInsets.all(30),
                            decoration: BoxDecoration(
                              border: Border.all(width: 1)
                            ),
                            child: Container(
                              margin: EdgeInsets.all(15),
                              decoration: BoxDecoration(
                                  border: Border.all(width: 1)
                              ),
                              child:  MaterialButton(
                                onPressed: (){showDialog(
                                    context: context,
                                    builder: (context) {
                                      String testCode = userPanelListOfAnnoncementsDto.quizCode;
                                      return TestCodeWarningDialog(quizCode: testCode,
                                      );
                                    }
                                );
                                },
                                child: Text(userPanelListOfAnnoncementsDto.quizCode != null ? userPanelListOfAnnoncementsDto.quizCode : "sdga"),
                                color: Colors.cyan,
                              ),
                            ),
                          ),
                        ),
                        Expanded(
                            flex: 1,
                            child: SizedBox()
                        ),
                      ],
                    )
                ),
              ],
            ),
          ],
        )
    );
  }
}
