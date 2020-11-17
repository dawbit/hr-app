import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/notifications_bloc.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/models/user_panel_list_of_annoncements_dto.dart';
import 'package:mobile/screens/main/widgets/card_widget.dart';
import 'package:mobile/screens/main/widgets/sub_card_widget.dart';
import 'package:mobile/widgets/loading.dart';
import 'package:mobile/widgets/no_data.dart';

class NotificationsView extends StatefulWidget {
  @override
  _NotificationsViewState createState() => _NotificationsViewState();
}

class _NotificationsViewState extends State<NotificationsView> {

  NotificationsBloc notificationsBloc;

  @override
  void initState() {
    super.initState();
    notificationsBloc = AppModule.injector.getBloc();
    notificationsBloc.getListOfNotifications();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.purple,
      child: Column(
        children: [
          Expanded(
            flex: 0,
              child: CardWidget(icon: Icon(Icons.notifications), cardTitle: "Powiadomienia",)
          ),
          Expanded(
            flex: 1,
            child: Container(
              child: Stack(
                children: [
                  StreamBuilder<bool>(
                      stream: notificationsBloc.isLoadingObservable,
                      initialData: true,
                      builder: (context, snapshotLoading) {
                        return Visibility(
                          child: LoadingWidget(),
                          visible: snapshotLoading.data,
                        );
                      }
                  ),
                  Container(
                    child: StreamBuilder<List<UserPanelListOfAnnoncementsDto>>(
                        stream: notificationsBloc.notificationsResponseObservable,
                        builder: (context, snapshotData) {
                          if (!snapshotData.hasData) {
                            return Container();
                          }
                          else {
                            if (snapshotData.data.length > 0) {
                              return Container(
                                child: ListView.builder(
                                  padding: EdgeInsets.all(0),
                                    itemCount: snapshotData.data.length,
                                    itemBuilder: (_, index) => SubCardWidget(icon: Icon(Icons.notifications_none), userPanelListOfAnnoncementsDto: snapshotData.data[index],),
                                ),
                              );
                            } else {
                              return NoDataWidget();
                            }
                          }
                        }
                    ),
                  ),
                ],
              ),
            ),
          ),
      ],
      ),
    );
  }
}
