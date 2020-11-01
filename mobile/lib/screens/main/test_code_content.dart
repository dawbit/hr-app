import 'package:flutter/material.dart';
import 'package:mobile/values/styles.dart';

class TestCodeContent extends StatefulWidget {
  @override
  _TestCodeContentState createState() => _TestCodeContentState();
}

class _TestCodeContentState extends State<TestCodeContent> {

  TextEditingController _textEditingController;

  @override
  Widget build(BuildContext context) {
    return Container(
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
              child: Text("Please enter your test code below: ")
          ),
          Expanded(
              flex: 1,
              child: TextField(
                controller: _textEditingController,
              )
          ),
        ],
      ),
    );
  }
}
