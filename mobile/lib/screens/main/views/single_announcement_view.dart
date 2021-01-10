import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/announcement_apply_bloc.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/announcements_dto.dart';
import 'package:mobile/utils/toast_util.dart';
import 'package:mobile/widgets/loading.dart';

class SingleAnnouncementView extends StatefulWidget {

  final AnnouncementsDto announcementsDto;

  SingleAnnouncementView({this.announcementsDto});

  @override
  _SingleAnnouncementViewState createState() => _SingleAnnouncementViewState();
}

class _SingleAnnouncementViewState extends State<SingleAnnouncementView> {

  AnnouncementApplyBloc announcementApplyBloc;
  StreamSubscription responseMessage;
  StreamSubscription errorMessage;

  @override
  void initState() {
    super.initState();
    announcementApplyBloc = AppModule.injector.getBloc();
    responseMessage = announcementApplyBloc.announcementsResponseObservable.listen((_) => _onApplyResponse());
    errorMessage = announcementApplyBloc.errorResponseObservable.listen((event) {_onError(event);});
  }

  @override
  void dispose() {
    errorMessage.cancel();
    responseMessage.cancel();
    super.dispose();
  }

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
                                    child: Text("${Lang.of(context).translate("company")}: ${widget.announcementsDto.companyName}", textAlign: TextAlign.start,
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
                                    child: Text("${Lang.of(context).translate("announcement")}: ${widget.announcementsDto.announcementTitle}", textAlign: TextAlign.start,
                                      style: TextStyle(color: Colors.white),)
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
                                  Text("${Lang.of(context).translate("company_localization")}: ${widget.announcementsDto.companyLocation}"),
                                  SizedBox(height: 20,),
                                  Text("${Lang.of(context).translate("company_about")}: ${widget.announcementsDto.companyAbout}"),
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
                                      child: Text("${Lang.of(context).translate("about_announcement")}: ${widget.announcementsDto.announcementDescription}")
                                  ),
                                  Expanded(
                                      flex: 0,
                                      child: MaterialButton(
                                        color: Theme.of(context).primaryColor,
                                        onPressed: (){announcementApplyBloc.announcementApply(widget.announcementsDto);},
                                        child: Container(
                                          width: MediaQuery.of(context).size.width,
                                          height: 50,
                                          child: Center(child: Text(Lang.of(context).translate("apply"), style: TextStyle(color: Colors.white),)),
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
            ),
            Column(
              children: [
                Expanded(
                  flex: 1,
                  child: StreamBuilder<bool>(
                      stream: announcementApplyBloc.isLoadingObservable,
                      initialData: false,
                      builder: (context, snapshot) {
                        return Visibility(
                            visible: snapshot.data,
                            child: LoadingWidget()
                        );
                      }
                  ),
                ),
                Expanded(
                    flex: 3,
                    child: Container()
                ),
              ],
            ),
          ],
        )
    );
  }

  void _onApplyResponse() {
    showToast(context, Lang.of(context).translate("applied_successfully"));
  }
  
  void _onError(Object obj) {
    final dioError = obj as DioError;
    final res = dioError.response;
    if((obj as DioError).error.toString().toLowerCase().contains("connection failed")) {
      showToast(context, Lang.of(context).translate("connection_error"));
    }
    else if(res.statusCode >= 500) {
      showToast(context, Lang.of(context).translate("server_error"));
    }
    else if(res.toString().contains("You have already taken part")) {
      showToast(context, Lang.of(context).translate("already_applied"));
    }
    else if(res.toString().contains("HR users and CEOs are not allowed to apply")) {
      showToast(context, Lang.of(context).translate("hr_ceo_users_are_not_allowed"));
    }
  }
}
