import 'package:flutter/material.dart';
import 'package:mobile/blocs/quiz_information_bloc.dart';
import 'package:mobile/blocs/quiz_question_bloc.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/values/sizes.dart';

class TestCodeWarningDialog extends StatefulWidget {
  @override
  _TestCodeWarningDialogState createState() => _TestCodeWarningDialogState();
}

class _TestCodeWarningDialogState extends State<TestCodeWarningDialog> {

  QuizInformationBloc _quizInformationBloc;
  QuizQuestionBloc _quizQuestionBloc;

  @override
  void initState() {
    super.initState();
    _quizInformationBloc = AppModule.injector.getBloc();
    _quizQuestionBloc = AppModule.injector.getBloc();
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text("Czy jestes pewien ze chcesz rozpoczac ten test?"),
      actions: [
        MaterialButton(
            height: Sizes.hugeSize,
            onPressed: (){Navigator.of(context).pop();},
            child: Container(
              width: MediaQuery.of(context).size.width,
              child: Center(
                  child: Text("Rozpocznij")
              ),
            )
        ),
      ],
    );
  }
}
