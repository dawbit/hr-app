import 'package:flutter/material.dart';
import 'package:mobile/config/routes.dart';

class SplashScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.green,
      body: RaisedButton(
        onPressed: (){Navigator.of(context).pushReplacementNamed(mainScreenRoute);},
      ),
    );
  }
}
