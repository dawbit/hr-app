import 'package:flutter/material.dart';
import 'package:mobile/blocs/login_bloc.dart';
import 'package:mobile/enums/login_state.dart';
import 'package:mobile/injections/login_module.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/login_command_dto.dart';

class LoginHolder extends StatefulWidget {
  final Function changeLoginContentStateToRegister;
  final bool isVisible;

  LoginHolder({this.changeLoginContentStateToRegister, this.isVisible});

  @override
  _LoginHolderState createState() => _LoginHolderState();
}

class _LoginHolderState extends State<LoginHolder> {

  TextEditingController loginTextEditingController;
  TextEditingController passwordTextEditingController;
  LoginBloc _loginBloc;

  @override
  void initState() {
    loginTextEditingController = TextEditingController();
    passwordTextEditingController = TextEditingController();
    _loginBloc = LoginModule.injector.getBloc();
    super.initState();
  }

  @override
  void dispose() {
    loginTextEditingController.dispose();
    passwordTextEditingController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {

    return AnimatedPositioned(
      right: widget.isVisible ? 0 : -MediaQuery.of(context).size.width,
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
                controller: loginTextEditingController,
                style: TextStyle(fontSize: 21,),
                decoration: InputDecoration(

                  border: OutlineInputBorder(
                    borderSide: BorderSide.none,
                  ),
                  fillColor: Colors.black38,
                  contentPadding: EdgeInsets.all(5),
                  filled: true,
                  labelText: Lang.of(context).translate('login'),
                ),
              ),
            ),

            Padding(
              padding: const EdgeInsets.all(10),
              child: TextFormField(
                maxLines: 1,
                controller: passwordTextEditingController,
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
                onTap: () {attemptToLogin();},
                child: Container(
                  height: 50,
                  child: Center(child:
                  Text(Lang.of(context).translate("login"), style: TextStyle(color: Colors.white),)),
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
                  widget.changeLoginContentStateToRegister(LoginState.REGISTER);
                },
                child: Text( "${Lang.of(context).translate("noAccount")} ${Lang.of(context).translate("register")}"),
              ),
            )
          ],
        ),
      ),
    );
  }

  void attemptToLogin() {
    final login = loginTextEditingController.text;
    final password = passwordTextEditingController.text;
    final loginCommandDto = LoginCommandDto(login: login, password: password);
    _loginBloc.attemptToLogin(loginCommandDto);
  }
}
