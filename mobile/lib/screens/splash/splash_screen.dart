
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
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
        resizeToAvoidBottomInset: false,
        body: Container(
          height: MediaQuery.of(context).size.height,
          width: MediaQuery.of(context).size.width,
          decoration: BoxDecoration(
            image: DecorationImage(
              fit: BoxFit.fitHeight,
              image: AssetImage('assets/images/background-01.jpg'),
            )
          ),
          child: Center(child: Text("HR-APP", style: TextStyle(fontSize: 60, fontFamily: 'Smallville1'),))
        )
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
