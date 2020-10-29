import 'package:flutter/material.dart';
import 'package:mobile/enums/login_state.dart';
import 'package:mobile/localizations/app_localization.dart';

class RegisterHolder extends StatefulWidget {

  final Function changeLoginContentStateToLogin;
  final bool isVisible;

  RegisterHolder({this.changeLoginContentStateToLogin, this.isVisible});

  @override
  _RegisterHolderState createState() => _RegisterHolderState();
}

class _RegisterHolderState extends State<RegisterHolder> {

  TextEditingController emailTextEditingController;
  TextEditingController passwordTextEditingController;
  TextEditingController repeatedPasswordTextEditingController;

  @override
  Widget build(BuildContext context) {
    return AnimatedPositioned(
      left: widget.isVisible ? 0 : -MediaQuery.of(context).size.width,
      duration: Duration(milliseconds: 300),
      curve: Curves.fastOutSlowIn,
      child: Container(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        child: Column(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.only(top: 100, bottom: 10, left: 10, right: 10),
              child: TextFormField(
                maxLines: 1,
                controller: emailTextEditingController,
                style: TextStyle(fontSize: 21,),
                decoration: InputDecoration(
                  border: OutlineInputBorder(
                    borderSide: BorderSide.none,
                  ),
                  contentPadding: EdgeInsets.all(5),
                  fillColor: Colors.black38,
                  filled: true,
                  labelText: Lang.of(context).translate('email'),
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(10),
              child: TextFormField(
                maxLines: 1,
                controller: emailTextEditingController,
                style: TextStyle(fontSize: 21,),
                decoration: InputDecoration(
                  border: OutlineInputBorder(
                    borderSide: BorderSide.none,
                  ),
                  contentPadding: EdgeInsets.all(5),
                  fillColor: Colors.black38,
                  filled: true,
                  labelText: Lang.of(context).translate('password'),
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(10),
              child: TextFormField(
                maxLines: 1,
                controller: repeatedPasswordTextEditingController,
                style: TextStyle(fontSize: 21,),
                decoration: InputDecoration(
                  border: OutlineInputBorder(
                    borderSide: BorderSide.none,
                  ),
                  contentPadding: EdgeInsets.all(5),
                  fillColor: Colors.black38,
                  filled: true,
                  labelText: Lang.of(context).translate('password'),
                ),
              ),
            ),
            SizedBox(height: 20,),
            Padding(
              padding: EdgeInsets.all(10),
              child: InkWell(
                onTap: () {},
                child: Container(
                  height: 50,
                  child: Center(child:
                  Text(Lang.of(context).translate("register"), style: TextStyle(color: Colors.white),)),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(10),
                    color: Color(0xfffa526c),
                  ),
                ),
              ),
            ),
            SizedBox(height: 10,),
            Padding(
              padding: EdgeInsets.all(10),
              child: GestureDetector(
                onTap: (){
                  widget.changeLoginContentStateToLogin(LoginState.LOGIN);
                },
                child: Text("${Lang.of(context).translate("alreadyRegistered")} ${Lang.of(context).translate("login")}"),
              ),
            )
          ],
        ),
      ),
    );
  }
}
