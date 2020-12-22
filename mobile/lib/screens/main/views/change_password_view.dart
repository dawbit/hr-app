import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/account_bloc.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/change_password_command_dto.dart';
import 'package:mobile/utils/toast_util.dart';
import 'package:mobile/values/sizes.dart';

class ChangePasswordView extends StatefulWidget {
  @override
  _ChangePasswordViewState createState() => _ChangePasswordViewState();
}

class _ChangePasswordViewState extends State<ChangePasswordView> {

  TextEditingController passwordController = TextEditingController();
  TextEditingController newPasswordController = TextEditingController();

  StreamSubscription newPasswordSuccessStream;
  StreamSubscription newPasswordErrorStream;

  String newPasswordError;
  String passwordError;

  AccountBloc _accountBloc;

  @override
  void dispose() {
    newPasswordErrorStream.cancel();
    newPasswordSuccessStream.cancel();
    newPasswordController.dispose();
    passwordController.dispose();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    _accountBloc = AppModule.injector.getBloc();
    newPasswordErrorStream = _accountBloc.changePasswordResponseObservable.listen((event) {
      _onSuccess();
    });
    newPasswordErrorStream = _accountBloc.errorBodyObservable.listen((event) {
      _onError(event);
    });
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
              child: Text(Lang.of(context).translate("change_password"), style: TextStyle(fontSize: Sizes.bigSize),)
          ),
          Padding(
            padding: const EdgeInsets.only(top: 100, bottom: 10, left: 10, right: 10),
            child: TextFormField(
              controller: newPasswordController,
              maxLines: 1,
              onChanged: (_) {
                if(newPasswordError!=null) {
                  setState(() {
                    newPasswordError=null;
                  });
                }
              },
              style: TextStyle(fontSize: 21,),
              decoration: InputDecoration(
                errorText: newPasswordError,
                border: OutlineInputBorder(
                  borderSide: BorderSide.none,
                ),
                fillColor: Colors.black38,
                contentPadding: EdgeInsets.all(5),
                filled: true,
                labelText: Lang.of(context).translate('new_password'),
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
                Text(Lang.of(context).translate("change_password"), style: TextStyle(color: Colors.white),)),
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
    final newPassword = newPasswordController.text;
    final password = passwordController.text;

    passwordValidation(newPassword);

    if(newPasswordError == null && passwordError == null) {
      _accountBloc.changePassword(
          ChangePasswordCommandDto(
            newPassword: newPassword,
              password: password
          )
      );
    }
  }

  void passwordValidation(String value) {
    if(value.length <6) {
      setState(() {
        newPasswordError =  Lang.of(context).translate("value_too_short");
      });
    }
  }

  void _onSuccess() {
    showToast(context, Lang.of(context).translate("success_change_password"));
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
  }
}
