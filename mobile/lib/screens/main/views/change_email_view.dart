import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/account_bloc.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/change_email_command_dto.dart';
import 'package:mobile/utils/toast_util.dart';
import 'package:mobile/values/sizes.dart';
import 'package:string_validator/string_validator.dart';

class ChangeEmailView extends StatefulWidget {
  @override
  _ChangeEmailViewState createState() => _ChangeEmailViewState();
}

class _ChangeEmailViewState extends State<ChangeEmailView> {

  AccountBloc _accountBloc;

  TextEditingController newEmailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  StreamSubscription newEmailSuccessStream;
  StreamSubscription newEmailErrorStream;

  String newEmailError;
  String passwordError;

  @override
  void initState() {
    super.initState();
    _accountBloc = AppModule.injector.getBloc();
    newEmailErrorStream = _accountBloc.errorBodyObservable.listen((event) {
      _onError(event);
    });
    newEmailSuccessStream = _accountBloc.changeEmailResponseObservable.listen((event) {
      _onSuccess();
    });
  }

  @override
  void dispose() {
    newEmailSuccessStream.cancel();
    newEmailErrorStream.cancel();
    newEmailController.dispose();
    passwordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(Sizes.bigSpace),
      height: MediaQuery.of(context).size.height,
      width: MediaQuery.of(context).size.width,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Container(
            child: Text(Lang.of(context).translate("change_email"), style: TextStyle(fontSize: Sizes.bigSize),)
          ),
          Padding(
            padding: const EdgeInsets.only(top: 100, bottom: 10, left: 10, right: 10),
            child: TextFormField(
              controller: newEmailController,
              maxLines: 1,
              onChanged: (_) {
                if(newEmailError!=null) {
                  setState(() {
                    newEmailError=null;
                  });
                }
              },
              style: TextStyle(fontSize: 21,),
              decoration: InputDecoration(
                errorText: newEmailError,
                border: OutlineInputBorder(
                  borderSide: BorderSide.none,
                ),
                fillColor: Colors.black38,
                contentPadding: EdgeInsets.all(5),
                filled: true,
                labelText: Lang.of(context).translate('new_email'),
              ),
            ),
          ),

          Padding(
            padding: const EdgeInsets.all(10),
            child: TextFormField(
              maxLines: 1,
              controller: passwordController,
              onChanged: (_) {
                if(passwordError!=null) {
                  setState(() {
                    passwordError=null;
                  });
                }
              },
              obscureText: true,
              style: TextStyle(fontSize: 21,),
              decoration: InputDecoration(
                errorText: passwordError,
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
            padding: EdgeInsets.symmetric(horizontal: 10),
            child: InkWell(
              onTap: () {onButtonPress();},
              child: Container(
                height: 50,
                child: Center(child:
                Text(Lang.of(context).translate("change_email"), style: TextStyle(color: Colors.white),)),
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(10),
                  color: Color(0xfffa526c),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  void onButtonPress() {
    final newEmail = newEmailController.text;
    final password = passwordController.text;

    emailValidation(newEmail);

    if(newEmailError == null && passwordError == null) {
      _accountBloc.changeEmail(
          ChangeEmailCommandDto(
              newEmail: newEmail,
              password: password
          )
      );
    }
  }

  void emailValidation(String value) {
    if(!isEmail(value)) {
      setState(() {
        newEmailError =  Lang.of(context).translate("wrong_email_format");
      });
    }
  }

  void _onSuccess() {
    showToast(context, Lang.of(context).translate("success_change_email"));
    Navigator.of(context).pop();
  }

  void _onError(Object obj) {
    final dioError = obj as DioError;
    final res = dioError.response;
    if((obj as DioError).error.toString().toLowerCase().contains("connection failed")) {
      showToast(context, Lang.of(context).translate("connection_error"));
    }
    else if(res.statusCode >= 500) {
      showToast(context, Lang.of(context).translate("server_error"));
    }
    else if(res.toString().contains("WRONG_PASSWORD")) {
      setState(() {
        passwordError = Lang.of(context).translate("wrong_current_password");
      });
    }
    else if(res.toString().contains("EMAIL_EXISTS")) {
      setState(() {
        newEmailError = Lang.of(context).translate("email_exists");
      });
    }
  }
}
