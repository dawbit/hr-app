import 'package:flutter/material.dart';
import 'package:mobile/widgets/loading.dart';

class LoginLoadingScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Color(0xdddddddd),
      height: MediaQuery.of(context).size.height,
      width: MediaQuery.of(context).size.width,
      child: LoadingWidget(),
    );
  }
}
