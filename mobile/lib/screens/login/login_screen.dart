import 'package:flutter/material.dart';
import 'package:mobile/screens/login/login_content.dart';

class LoginScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      backgroundColor: Theme.of(context).backgroundColor,
      body: Stack(
        fit: StackFit.expand,
        children: [
          FittedBox(
            fit: BoxFit.fitHeight,
            child: Image.asset('assets/images/background-01.jpg'),
          ),
          LoginContent(),
        ],
      ),
    );
  }
}
