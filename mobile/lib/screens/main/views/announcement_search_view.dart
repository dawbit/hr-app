import 'package:flutter/material.dart';
import 'package:mobile/blocs/announcements_bloc.dart';
import 'package:mobile/enums/errorType.dart';
import 'package:mobile/enums/request_state.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/models/announcements_dto.dart';
import 'package:mobile/screens/main/widgets/announcement_card_widget.dart';
import 'package:mobile/screens/main/widgets/search_view_widget.dart';
import 'package:mobile/widgets/connection_error.dart';
import 'package:mobile/widgets/loading.dart';
import 'package:mobile/widgets/no_data.dart';
import 'package:mobile/widgets/server_error.dart';

class AnnouncementSearchView extends StatefulWidget {
  @override
  _AnnouncementSearchViewState createState() => _AnnouncementSearchViewState();
}

class _AnnouncementSearchViewState extends State<AnnouncementSearchView> {

  AnnouncementsBloc _announcementsBloc;

  @override
  void initState() {
    super.initState();
    _announcementsBloc = AppModule.injector.getBloc();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Expanded(
            flex: 0,
            child: SearchViewWidget()
        ),
        Expanded(
            flex: 2,
            child:
            Listener(
              onPointerUp: (_) {
                FocusScopeNode currentFocus = FocusScope.of(context);
                if (!currentFocus.hasPrimaryFocus && currentFocus.focusedChild != null) {
                  currentFocus.focusedChild.unfocus();
                }
              },
              child: StreamBuilder<RequestState>(
                stream: _announcementsBloc.isLoadingObservable,
                initialData: RequestState.OK,
                builder: (context, snapshotState) {
                  return Stack(
                    children: [
                      Container(
                        child: StreamBuilder<List<AnnouncementsDto>>(
                            stream: _announcementsBloc
                                .announcementsResponseObservable,
                            builder: (context, snapshotData) {
                              return Visibility(
                                  visible: snapshotState.data == RequestState.OK,
                                  child: okView(snapshotData)
                              );
                            }
                        ),
                      ),
                      Visibility(
                        child: LoadingWidget(),
                        visible: snapshotState.data == RequestState.LOADING,
                      ),
                      StreamBuilder<ErrorType>(
                          stream: _announcementsBloc.errorResponseObservable,
                          builder: (context, snapshotError) {
                            return Visibility(
                              visible: snapshotState.data == RequestState.ERROR,
                              child: errorWidget(snapshotError.data),
                            );
                          }
                      ),
                    ],
                  );
                }
              ),
            )
        )
      ],
    );
  }

  Widget okView(AsyncSnapshot snapshotData) {
    if (!snapshotData.hasData) {
      return Container();
    }
    else {
      if (snapshotData.data.length > 0) {
        return ListView.builder(
            itemCount: snapshotData.data.length,
            itemBuilder: (_, index) =>
                AnnouncementCardWidget(
                    data: snapshotData.data[index])
        );
      } else {
        return NoDataWidget();
      }
    }
  }

  Widget errorWidget(ErrorType errorType) {
    if(errorType!= null) {
      switch(errorType) {
        case ErrorType.CONNECTION_ERROR:
          return ConnectionError(callSearch);
          break;
        case ErrorType.SERVER_ERROR:
          return ServerError(callSearch);
          break;
      }
    } else {
      return Container();
    }
  }

  void callSearch() {
    _announcementsBloc.callSearch();
  }

}
