import 'dart:async';

import 'package:flutter/material.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/utils/toast_util.dart';

class TimerWidget extends StatefulWidget {

  final double timeForQuiz;

  TimerWidget(this.timeForQuiz);

  @override
  _TimerWidgetState createState() => _TimerWidgetState();
}

class _TimerWidgetState extends State<TimerWidget> {

  String timeLeft = "0:00";
  int timeLeftInt;
  Timer timer;

  @override
  void initState() {
    super.initState();
    timeLeftInt = Duration(milliseconds: widget.timeForQuiz.toInt()).inSeconds -1;
    timer = Timer.periodic(Duration(seconds: 1), (timer) {
      int minutes = Duration(seconds: timeLeftInt).inMinutes;
      int seconds = Duration(seconds: timeLeftInt).inSeconds %60;
      if(minutes==0 && seconds == 0) {
        timer.cancel();
        showToast(context, Lang.of(context).translate("time_over"));
        Navigator.of(context).pop();
      }
      timeLeftInt -= 1;
      setState(() {
        timeLeft = "$minutes:$seconds";
      });
    });
  }

  @override
  void dispose() {
    timer.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Text("${Lang.of(context).translate("time_left")}: $timeLeft"),
    );
  }
}
