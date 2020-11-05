import 'package:flutter/material.dart';
import 'package:mobile/screens/main/widgets/test_code_warning_dialog.dart';
import 'package:mobile/values/sizes.dart';
import 'package:mobile/values/styles.dart';

class TestCodeContent extends StatefulWidget {
  @override
  _TestCodeContentState createState() => _TestCodeContentState();
}

class _TestCodeContentState extends State<TestCodeContent> {

  TextEditingController _textEditingController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(Sizes.giantSpace),
      height: MediaQuery.of(context).size.height,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
              flex: 0,
              child: Text("Test code", style: Styles.textTitleStyle,)
          ),
          Expanded(
              flex: 1,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text("Please enter your test code below: "),
                  TextField(
                    controller: _textEditingController,
                  ),
                  Container(
                    margin: EdgeInsets.only(top: Sizes.giantSpace),
                    child: MaterialButton(
                        height: Sizes.hugeSize,
                        onPressed: (){showDialog(
                            context: context,
                            builder: (context) {
                              return TestCodeWarningDialog(quizCode: 'testowe',
                              );
                            }
                        );},
                        child: Container(
                          width: MediaQuery.of(context).size.width,
                          child: Center(
                              child: Text("Start Test")
                          ),
                        )
                    ),
                  )
                ],
              )
          ),
        ],
      ),
    );
  }
}
