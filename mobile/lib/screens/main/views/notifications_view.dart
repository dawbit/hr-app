import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/notifications_bloc.dart';
import 'package:mobile/enums/request_state.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/user_panel_list_of_annoncements_dto.dart';
import 'package:mobile/screens/main/widgets/card_widget.dart';
import 'package:mobile/screens/main/widgets/sub_card_widget.dart';
import 'package:mobile/widgets/connection_error.dart';
import 'package:mobile/widgets/loading.dart';
import 'package:mobile/widgets/no_data.dart';
import 'package:mobile/widgets/server_error.dart';

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
      child: Column(
        children: [
          Expanded(
            flex: 0,
              child: CardWidget(icon: Icon(Icons.notifications), cardTitle: Lang.of(context).translate("notifications"),)
          ),
          Expanded(
            flex: 1,
            child: Container(
              child: StreamBuilder<RequestState>(
                stream: notificationsBloc.isLoadingObservable,
                initialData: RequestState.OK,
                builder: (context, snapshotState) {
                  return Stack(
                    children: [
                      Visibility(
                        child: LoadingWidget(),
                        visible: snapshotState.data == RequestState.LOADING,
                      ),
                      Container(
                        child: StreamBuilder<List<UserPanelListOfAnnoncementsDto>>(
                            stream: notificationsBloc.notificationsResponseObservable,
                            builder: (context, snapshotData) {
                              return Visibility(
                                visible: snapshotState.data == RequestState.OK,
                                  child: okView(snapshotData)
                              );
                            }
                        ),
                      ),
                      StreamBuilder<Object>(
                          stream: notificationsBloc.errorResponseObservable,
                          builder: (context, snapshotError) {
                            if(snapshotError.hasData) {
                              final dioError = (snapshotError.data as DioError);
                              return Visibility(
                                  visible:  snapshotState.data == RequestState.ERROR,
                                  child: errorWidget(dioError)
                              );
                            } else {
                              return Container();
                            }
                          }
                      ),
                    ],
                  );
                }
              ),
            ),
          ),
      ],
      ),
    );
  }

  Widget errorWidget(DioError dioError) {
    if((dioError).error.toString().toLowerCase().contains("connection failed")) {
      return ConnectionError(() => notificationsBloc.getListOfNotifications());
    }
    else {
      return ServerError(() => notificationsBloc.getListOfNotifications());
    }
  }

  Widget okView(AsyncSnapshot snapshotData) {
    if (!snapshotData.hasData) {
      return Container();
    }
    else {
      if (snapshotData.data.length > 0) {
        return Container(
          child: ListView.builder(
            padding: EdgeInsets.all(0),
            itemCount: snapshotData.data.length,
            itemBuilder: (_, index) =>
                SubCardWidget(icon: Icon(Icons.notifications_none),
                  userPanelListOfAnnoncementsDto: snapshotData.data[index],),
          ),
        );
      } else {
        return NoDataWidget();
      }
    }
  }
}
