import 'package:flutter/material.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/user_panel_list_of_annoncements_dto.dart';
import 'package:mobile/screens/main/widgets/test_code_warning_dialog.dart';

class SingleNotificationView extends StatelessWidget {

  final UserPanelListOfAnnoncementsDto userPanelListOfAnnoncementsDto;

  SingleNotificationView({this.userPanelListOfAnnoncementsDto});

  @override
  Widget build(BuildContext context) {
    return Container(
        decoration: BoxDecoration(
            color: Color(0xaaffffff),
            image: DecorationImage(
              fit: BoxFit.fitHeight,
              image: AssetImage('assets/images/background-01.jpg'),
            )
        ),
        child: Stack(
          children: [
            Column(
              children: [
                Expanded(
                  flex: 2,
                  child: Container(
                    color: Theme.of(context).accentColor,
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
                                    child: Text(
                                      "${Lang.of(context).translate("company")}: ${userPanelListOfAnnoncementsDto.companyName}",
                                      textAlign: TextAlign.start,
                                    style: TextStyle(color: Colors.white),)
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
                                    child: Text("${Lang.of(context).translate("announcement")}: "
                                        "${userPanelListOfAnnoncementsDto.announcementName}", textAlign: TextAlign.start, style: TextStyle(color: Colors.white),)
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
                            child: Text("${Lang.of(context).translate("quiz_code")}:")
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
                                child: Text(userPanelListOfAnnoncementsDto.quizCode, style: TextStyle(
                                  color: Colors.white
                                ),),
                                color: Theme.of(context).primaryColor
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
