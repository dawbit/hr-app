import 'dart:async';

import 'package:flutter/material.dart';
import 'package:mobile/config/routes.dart';
import 'package:mobile/security/token_shared_pref.dart';

class SplashScreen extends StatefulWidget {
  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {

  TokenSharedPref tokenSharedPref;

  @override
  void initState() {
    super.initState();
    // tokenSharedPref = AppModule.injector.get();
    // tokenSharedPref.getToken().then(onTokenValue);
    Future.delayed(Duration(seconds: 3), (){onTokenValue("asgasgasg");});
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.green,
        body: Container()
    );
  }

  void onTokenValue(String token) {
    if(token!=null) {
      print(token);
      Navigator.of(context).pushReplacementNamed(mainScreenRoute);
    } else {
      print('asfafasfas');
      Navigator.of(context).pushReplacementNamed(loginScreenRoute);
    }
  }
}
