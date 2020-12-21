import 'package:flutter/material.dart';
import 'package:mobile/data_sources/remote/api_client/api_provider.dart';
import 'package:mobile/screens/main/views/account_view.dart';
import 'package:mobile/screens/main/views/notifications_view.dart';
import 'package:mobile/screens/main/widgets/card_widget.dart';
import 'package:mobile/widgets/nested_navigator.dart';

import '../../Application.dart';

class AccountContent extends StatefulWidget {
  @override
  _AccountContentState createState() => _AccountContentState();
}

class _AccountContentState extends State<AccountContent> {

  @override
  void initState() {
    navigationKey = GlobalKey<NavigatorState>();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return NestedNavigator(
      navigationKey: navigationKey,
      initialRoute: '/',
      routes: {
        '/': (context) => Container(color: Colors.purple,
          child: Column(
            children: [
              CardWidget(icon: Icon(Icons.account_box), cardTitle: "Konto", onClick: onAccountClick,),
              CardWidget(icon: Icon(Icons.notifications), cardTitle: "Powiadomienia", onClick: onNotificationsClick,),
              CardWidget(icon: Icon(Icons.logout), cardTitle: "Wyloguj", onClick: logoutCurrentUser),
            ],
          ),
        ),
        '/notifications': (context) => NotificationsView(),
        '/account': (context) => AccountView(),
      },
    );
  }
  void onNotificationsClick() {
    Navigator.of(navigationKey.currentContext).pushNamed("/notifications");
  }
  void onAccountClick() {
    Navigator.of(navigationKey.currentContext).pushNamed("/account");
  }

}