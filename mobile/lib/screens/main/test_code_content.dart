import 'package:flutter/material.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/screens/main/widgets/test_code_warning_dialog.dart';
import 'package:mobile/values/sizes.dart';

class TestCodeContent extends StatefulWidget {
  @override
  _TestCodeContentState createState() => _TestCodeContentState();
}

class _TestCodeContentState extends State<TestCodeContent> {

  TextEditingController _textEditingController = TextEditingController();
  String errorMessage;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(Sizes.bigSpace),
      height: MediaQuery.of(context).size.height,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
              flex: 0,
              child: Text(Lang.of(context).translate("test_code"), style: TextStyle(fontSize: Sizes.bigSize),)
          ),
          Expanded(
              flex: 1,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text("${Lang.of(context).translate("enter_your_test_code_below")}:"),
                  TextField(
                    onChanged: (_) {
                      if(errorMessage!= null) {
                        setState(() {
                          errorMessage = null;
                        });
                      }
                    },
                    controller: _textEditingController,
                    decoration: InputDecoration(
                      errorText: errorMessage
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.only(top: Sizes.giantSpace),
                    child: MaterialButton(
                        height: Sizes.hugeSize,
                        onPressed: (){
                          if(_textEditingController.text.length<1) {
                            setState(() {
                              errorMessage = Lang.of(context).translate("empty_field");
                            });
                          } else {
                            showDialog(
                                context: context,
                                builder: (context) {
                                  String testCode = _textEditingController.text;
                                  _textEditingController.text="";
                                  return TestCodeWarningDialog(quizCode: testCode,
                                  );
                                }
                            );
                          }
                        },
                        child: Container(
                          width: MediaQuery.of(context).size.width,
                          child: Center(
                              child: Text(Lang.of(context).translate("start_test"))
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
