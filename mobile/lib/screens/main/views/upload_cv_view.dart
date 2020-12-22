import 'package:flutter/material.dart';
import 'package:mobile/security/account_data_shared_pref.dart';

class UploadCvView extends StatefulWidget {
  @override
  _UploadCvViewState createState() => _UploadCvViewState();
}

class _UploadCvViewState extends State<UploadCvView> {

  String one= "";
  String two="";
  String three= "";

  @override
  void initState() {
    super.initState();
    AccountDataSharedPref.getAccountData().then((value) {
      setState(() {
        one = value.email;
        two = value.cv;
        three = value.phoneNumber;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          Text(one),
          Text(two),
          Text(three),
        ],
      ),
    );
  }
}
