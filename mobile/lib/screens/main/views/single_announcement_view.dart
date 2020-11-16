import 'dart:async';

import 'package:flutter/material.dart';
import 'package:mobile/blocs/announcement_apply_bloc.dart';
import 'package:mobile/injections/app_module.dart';
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

  @override
  void initState() {
    super.initState();
    announcementApplyBloc = AppModule.injector.getBloc();
    responseMessage = announcementApplyBloc.announcementsResponseObservable.listen(_onApplyResponse);
  }

  @override
  void dispose() {
    responseMessage.cancel();
    super.dispose();
  }

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
                                    child: Text("Firma: ${widget.announcementsDto.companyName}", textAlign: TextAlign.start,)
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
                                    child: Text("Ogłoszenie: ${widget.announcementsDto.announcementTitle}", textAlign: TextAlign.start,)
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
                                  Text("Lokalizacja firmy: ${widget.announcementsDto.companyLocation}"),
                                  SizedBox(height: 20,),
                                  Text("O firmie: ${widget.announcementsDto.companyAbout}"),
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
                                      child: Text("O ogłoszeniu: ${widget.announcementsDto.announcementDescription}")
                                  ),
                                  Expanded(
                                      flex: 0,
                                      child: MaterialButton(
                                        onPressed: (){announcementApplyBloc.announcementApply(widget.announcementsDto);},
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

  void _onApplyResponse(String message) {
    showToast(context, message);
  }
}
