
import 'package:flutter/material.dart';
import 'package:mobile/config/routes.dart';
import 'package:mobile/security/token_shared_pref.dart';

class SplashScreen extends StatefulWidget {
  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {

  @override
  void initState() {
    super.initState();
    TokenSharedPref.getToken().then(onTokenValue);
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
      Navigator.of(context).pushReplacementNamed(mainScreenRoute);
    } else {
      Navigator.of(context).pushReplacementNamed(loginScreenRoute);
    }
  }
}
