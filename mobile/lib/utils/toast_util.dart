import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

showToast(context, String message) {
  Fluttertoast.showToast(
      msg: message,
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.BOTTOM,
      timeInSecForIosWeb: 1,
      backgroundColor: Theme.of(context).accentColor,
      textColor: Theme.of(context).colorScheme.onPrimary,
      fontSize: 16.0);
}