import 'dart:async';

import 'package:flutter/material.dart';
import 'package:mobile/config/routes.dart';
import 'package:mobile/data_sources/remote/api_client/api_provider.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/screens/main/account_content.dart';
import 'package:mobile/screens/main/announcements_content.dart';
import 'package:mobile/screens/main/test_code_content.dart';
import 'package:mobile/utils/toast_util.dart';

import '../../Application.dart';

class MainScreen extends StatefulWidget {
  @override
  _MainScreenState createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {

  int bottomNavigationBarIndex;
  DateTime currentBackPressTime;

  StreamSubscription logoutObservable;

  @override
  void dispose() {
    logoutObservable.cancel();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    bottomNavigationBarIndex=0;
    logoutObservable = logoutStream.listen(_onLogout);
  }


  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: onWillPop,
      child: Scaffold(
        resizeToAvoidBottomInset: false,
        backgroundColor: Theme.of(context).backgroundColor,
        body: Container(
            decoration: BoxDecoration(
                image: DecorationImage(
                  fit: BoxFit.fitHeight,
                  image: AssetImage('assets/images/background-01.jpg'),
                )
            ),
            child: SafeArea(child: _mainScreenContent())),
        bottomNavigationBar: BottomNavigationBar(
          backgroundColor: Theme.of(context).accentColor,
          unselectedItemColor: Color(0xffaaaaaa),

          selectedItemColor: Color(0xffffffff),
          currentIndex: bottomNavigationBarIndex,
          onTap: (index){changeView(index);},
          type: BottomNavigationBarType.fixed,
          items: [
            BottomNavigationBarItem(
              icon: Icon(Icons.info),
                label: Lang.of(context).translate("announcements")
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.code),
                label: Lang.of(context).translate("test")
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.person),
                label: Lang.of(context).translate("account")
            ),
          ],
        ),
      ),
    );
  }

  Widget _mainScreenContent()  {
    switch(bottomNavigationBarIndex) {
      case 0:
        return AnnouncementsContent();
        break;
      case 1:
        return TestCodeContent();
        break;
      case 2:
        return AccountContent();
        break;
      default:
        return AnnouncementsContent();
        break;
    }
  }

  Future<bool> onWillPop() async {
    if(navigationKey.currentState.canPop()) {
      navigationKey.currentState.maybePop();
    } else {
      DateTime now = DateTime.now();
      if (currentBackPressTime == null ||
          now.difference(currentBackPressTime) > Duration(seconds: 2)) {
        currentBackPressTime = now;
        showToast(context, "Klikniej jeszcze raz żeby wyjść");
        return Future.value(false);
      }
      return Future.value(true);
    }
  }

  void changeView(int index) {
    setState(() {
      bottomNavigationBarIndex=index;
    });
  }

  void _onLogout(_) {
    Navigator.of(context).pushReplacementNamed(loginScreenRoute);
  }

}
