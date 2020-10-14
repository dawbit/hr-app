import 'package:flutter/material.dart';
import 'package:mobile/screens/login/login_content.dart';

class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
          child: LoginContent()
      ),
    );
  }
}
