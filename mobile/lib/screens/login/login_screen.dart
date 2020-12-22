import 'package:flutter/material.dart';
import 'package:mobile/screens/login/login_content.dart';

class LoginScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      backgroundColor: Theme.of(context).backgroundColor,
      body: SingleChildScrollView(
          child: LoginContent()
      ),
    );
  }
}
