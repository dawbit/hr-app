import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/account_bloc.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/change_phone_number_command_dto.dart';
import 'package:mobile/security/account_data_shared_pref.dart';
import 'package:mobile/utils/toast_util.dart';
import 'package:mobile/values/sizes.dart';
import 'package:string_validator/string_validator.dart';

class ChangePhoneNumberView extends StatefulWidget {
  @override
  _ChangePhoneNumberViewState createState() => _ChangePhoneNumberViewState();
}

class _ChangePhoneNumberViewState extends State<ChangePhoneNumberView> {

  TextEditingController newPhoneNumberController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  StreamSubscription newPhoneNumberSuccessStream;
  StreamSubscription newPhoneNumberErrorStream;

  AccountBloc _accountBloc;

  String newPhoneNumberHolder;

  String newPhoneNumberError;
  String passwordError;

  @override
  void initState() {
    super.initState();
    _accountBloc = AppModule.injector.getBloc();
    newPhoneNumberSuccessStream = _accountBloc.changePhoneNumberResponseObservable.listen((event) {
      _onSuccess();
    });
    newPhoneNumberErrorStream = _accountBloc.errorBodyObservable.listen((event) {
      _onError(event);
    });
  }

  @override
  void dispose() {
    newPhoneNumberErrorStream.cancel();
    newPhoneNumberSuccessStream.cancel();
    newPhoneNumberController.dispose();
    passwordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          color: Color(0xaaffffff),
          image: DecorationImage(
            fit: BoxFit.fitHeight,
            image: AssetImage('assets/images/background-01.jpg'),
          )
      ),
      padding: EdgeInsets.all(Sizes.bigSpace),
      height: MediaQuery.of(context).size.height,
      width: MediaQuery.of(context).size.width,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Container(
              child: Text(Lang.of(context).translate("change_phone_number"), style: TextStyle(fontSize: Sizes.bigSize),)
          ),
          Padding(
            padding: const EdgeInsets.only(top: 100, bottom: 10, left: 10, right: 10),
            child: TextFormField(
              controller: newPhoneNumberController,
              maxLines: 1,
              onChanged: (_) {
                if(newPhoneNumberError!=null) {
                  setState(() {
                    newPhoneNumberError=null;
                  });
                }
              },
              style: TextStyle(fontSize: 21,),
              decoration: InputDecoration(
                errorText: newPhoneNumberError,
                contentPadding: EdgeInsets.all(5),
                filled: true,
                labelText: Lang.of(context).translate('new_phone_number'),
              ),
            ),
          ),

          Padding(
            padding: const EdgeInsets.all(10),
            child: TextFormField(
              maxLines: 1,
              controller: passwordController,
              obscureText: true,
              onChanged: (_) {
                if(passwordError!=null) {
                  setState(() {
                    passwordError=null;
                  });
                }
              },
              style: TextStyle(fontSize: 21,),
              decoration: InputDecoration(
                errorText: passwordError,
                contentPadding: EdgeInsets.all(5),
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
                Text(Lang.of(context).translate("change_phone_number"), style: TextStyle(color: Colors.white),)),
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(10),
                  color: Theme.of(context).primaryColor,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  void onButtonPress() {
    newPhoneNumberHolder = newPhoneNumberController.text;
    final password = passwordController.text;

    phoneNumberValidation(newPhoneNumberHolder);

    if(newPhoneNumberError == null && passwordError == null) {
      _accountBloc.changePhoneNumber(
        ChangePhoneNumberCommandDto(
          phoneNumber: newPhoneNumberHolder,
          password: password
        )
      );
    }
  }

  void phoneNumberValidation(String value) {
    if(value.length <6 && value.length>13) {
      setState(() {
        newPhoneNumberError =  Lang.of(context).translate("wrong_phone_nubmer");
      });
    } else if(!isNumeric(value)) {
      setState(() {
        newPhoneNumberError =  Lang.of(context).translate("wrong_phone_nubmer");
      });
    }
  }

  void _onSuccess() async {
    await AccountDataSharedPref.setPhoneNumber(newPhoneNumberHolder);
    showToast(context, Lang.of(context).translate("success_change_phone_number"));
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
    else if(res.toString().contains("TOO_SHORT_NUMBER")) {
      setState(() {
        newPhoneNumberError =  Lang.of(context).translate("wrong_phone_nubmer");
      });
    }
    else if(res.toString().contains("ONLY_DIGITS_ALLOWED")) {
      setState(() {
        newPhoneNumberError =  Lang.of(context).translate("wrong_phone_nubmer");
      });
    }
    else if(res.toString().contains("WRONG_PASSWORD")) {
      setState(() {
        passwordError = Lang.of(context).translate("wrong_current_password");
      });
    }
  }
}
