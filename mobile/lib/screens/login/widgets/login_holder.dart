import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/login_bloc.dart';
import 'package:mobile/enums/error_login_type.dart';
import 'package:mobile/enums/login_state.dart';
import 'package:mobile/injections/login_module.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/login_command_dto.dart';
import 'package:mobile/utils/toast_util.dart';
import 'package:mobile/values/sizes.dart';
import 'package:mobile/widgets/login_loading_screen.dart';

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

  StreamSubscription errorStream;

  @override
  void initState() {
    loginTextEditingController = TextEditingController();
    passwordTextEditingController = TextEditingController();
    _loginBloc = LoginModule.injector.getBloc();
    errorStream = _loginBloc.errorObservable.listen(_onError);
    super.initState();
  }

  @override
  void dispose() {
    errorStream.cancel();
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
      child: Stack(
        children: [
          SingleChildScrollView(
            child: Container(
              color: Color(0xaaffffff),
              height: MediaQuery.of(context).size.height,
              width: MediaQuery.of(context).size.width,
              child: Column(
                children: <Widget>[
                  Container(
                    width: MediaQuery.of(context).size.width,
                    padding: EdgeInsets.all(20),
                      color: Color(0xff33b5e6),
                      child: Text(Lang.of(context).translate("login"),
                        style: TextStyle(
                            fontSize:
                            Sizes.bigSize,
                            color: Colors.white,
                          fontWeight: FontWeight.w500
                        ),
                      textAlign: TextAlign.center,
                      )
                  ),
                  Padding(
                    padding: const EdgeInsets.only(top: 50, bottom: 10, left: 10, right: 10),
                    child: TextFormField(
                      maxLines: 1,
                      controller: loginTextEditingController,
                      style: TextStyle(fontSize: 21,),
                      decoration: InputDecoration(
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
                      obscureText: true,
                      controller: passwordTextEditingController,
                      style: TextStyle(fontSize: 21,),
                      decoration: InputDecoration(
                        contentPadding: EdgeInsets.all(5),
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
                          color: Theme.of(context).primaryColor,
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
          ),
          StreamBuilder<bool>(
              stream: _loginBloc.isLoadingObservable,
              initialData: false,
              builder: (context, snapshot) {
                return Visibility(
                    visible: snapshot.data,
                    child: LoginLoadingScreen()
                );
              }
          ),
        ],
      ),
    );
  }

  void attemptToLogin() {
    final login = loginTextEditingController.text;
    final password = passwordTextEditingController.text;
    final loginCommandDto = LoginCommandDto(login: login, password: password);
    _loginBloc.attemptToLogin(loginCommandDto);
  }

  void _onError(ErrorLoginType errorLoginType) {
    switch(errorLoginType) {
      case ErrorLoginType.CONNECTION_ERROR:
        showToast(context, Lang.of(context).translate("connection_error"));
        break;
      case ErrorLoginType.CANT_LOGIN:
        showToast(context, Lang.of(context).translate("cant_login"));
        break;
      case ErrorLoginType.SERVER_ERROR:
        showToast(context, Lang.of(context).translate("server_error"));
        break;
    }
  }
}
