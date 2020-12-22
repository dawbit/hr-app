import 'dart:async';

import 'package:flutter/material.dart';
import 'package:mobile/blocs/registration_bloc.dart';
import 'package:mobile/enums/error_register_type.dart';
import 'package:mobile/enums/login_state.dart';
import 'package:mobile/injections/login_module.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/register_command_dto.dart';
import 'package:mobile/utils/toast_util.dart';
import 'package:mobile/values/sizes.dart';
import 'package:mobile/widgets/login_loading_screen.dart';
import 'package:string_validator/string_validator.dart';

class RegisterHolder extends StatefulWidget {

  final Function changeLoginContentStateToLogin;
  final bool isVisible;

  RegisterHolder({this.changeLoginContentStateToLogin, this.isVisible});

  @override
  _RegisterHolderState createState() => _RegisterHolderState();
}

class _RegisterHolderState extends State<RegisterHolder> {

  RegistrationBloc _registrationBloc;

  TextEditingController firstNameTextEditingController;
  TextEditingController middleNameTextEditingController;
  TextEditingController surNameTextEditingController;
  TextEditingController emailTextEditingController;
  TextEditingController phoneNumberTextEditingController;
  TextEditingController loginTextEditingController;
  TextEditingController passwordTextEditingController;

  String firstNameErrorMessage;
  String middleNameErrorMessage;
  String surNameErrorMessage;
  String emailErrorMessage;
  String phoneNumberErrorMessage;
  String loginErrorMessage;
  String passwordErrorMessage;

  StreamSubscription errorStream;

  @override
  void initState() {
    _registrationBloc = LoginModule.injector.getBloc();
    firstNameTextEditingController = TextEditingController();
    middleNameTextEditingController = TextEditingController();
    surNameTextEditingController = TextEditingController();
    emailTextEditingController = TextEditingController();
    phoneNumberTextEditingController = TextEditingController();
    loginTextEditingController = TextEditingController();
    passwordTextEditingController = TextEditingController();
    _registrationBloc.isRegisteredObservable.listen((_) {
      showToast(context, Lang.of(context).translate("register_success"));
    });
    errorStream = _registrationBloc.errorObservable.listen(_onError);
    super.initState();
  }

  @override
  void dispose() {
    errorStream.cancel();
    firstNameTextEditingController.dispose();
    middleNameTextEditingController.dispose();
    surNameTextEditingController.dispose();
    emailTextEditingController.dispose();
    phoneNumberTextEditingController.dispose();
    loginTextEditingController.dispose();
    passwordTextEditingController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedPositioned(
      left: widget.isVisible ? 0 : -MediaQuery.of(context).size.width,
      duration: Duration(milliseconds: 300),
      curve: Curves.fastOutSlowIn,
      child: Stack(
        children: [
          Container(
            color: Color(0xaaffffff),
            padding: EdgeInsets.only(
              bottom: MediaQuery.of(context).viewInsets.bottom
            ),
            height: MediaQuery.of(context).size.height,
            width: MediaQuery.of(context).size.width,
            child: SingleChildScrollView(
              child: Column(
                children: <Widget>[
                  Container(
                      width: MediaQuery.of(context).size.width,
                      padding: EdgeInsets.all(20),
                      color: Color(0xff33b5e6),
                      child: Text(Lang.of(context).translate("register"),
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
                      onChanged: ((_) {setState(() {
                        loginErrorMessage = null;
                      });}),
                      controller: loginTextEditingController,
                      style: TextStyle(fontSize: 21,),
                      decoration: InputDecoration(
                        errorText: loginErrorMessage,
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
                      onChanged: ((_) {setState(() {
                        emailErrorMessage = null;
                      });}),
                      controller: emailTextEditingController,
                      style: TextStyle(fontSize: 21,),
                      decoration: InputDecoration(
                        errorText: emailErrorMessage,
                        contentPadding: EdgeInsets.all(5),
                        filled: true,
                        labelText: Lang.of(context).translate('email'),
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(10),
                    child: TextFormField(
                      maxLines: 1,
                      onChanged: ((_) {setState(() {
                        passwordErrorMessage = null;
                      });}),
                      controller: passwordTextEditingController,
                      style: TextStyle(fontSize: 21,),
                      obscureText: true,
                      decoration: InputDecoration(
                        errorText: passwordErrorMessage,
                        contentPadding: EdgeInsets.all(5),
                        filled: true,
                        labelText: Lang.of(context).translate('password'),
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(10),
                    child: TextFormField(
                      maxLines: 1,
                      controller: firstNameTextEditingController,
                      style: TextStyle(fontSize: 21,),
                      onChanged: ((_) {setState(() {
                        firstNameErrorMessage = null;
                      });}),
                      decoration: InputDecoration(
                        errorText: firstNameErrorMessage,
                        contentPadding: EdgeInsets.all(5),
                        filled: true,
                        labelText: Lang.of(context).translate('firstname'),
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(10),
                    child: TextFormField(
                      maxLines: 1,
                      controller: middleNameTextEditingController,
                      style: TextStyle(fontSize: 21,),
                      decoration: InputDecoration(
                        contentPadding: EdgeInsets.all(5),
                        filled: true,
                        labelText: Lang.of(context).translate('middlename'),
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(10),
                    child: TextFormField(
                      maxLines: 1,
                      onChanged: ((_) {setState(() {
                        surNameErrorMessage = null;
                      });}),
                      controller: surNameTextEditingController,
                      style: TextStyle(fontSize: 21,),
                      decoration: InputDecoration(
                        errorText: surNameErrorMessage,
                        contentPadding: EdgeInsets.all(5),
                        filled: true,
                        labelText: Lang.of(context).translate('surname'),
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(10),
                    child: TextFormField(
                      maxLines: 1,
                      onChanged: ((_) {setState(() {
                        phoneNumberErrorMessage = null;
                      });}),
                      controller: phoneNumberTextEditingController,
                      style: TextStyle(fontSize: 21,),
                      decoration: InputDecoration(
                        errorText: phoneNumberErrorMessage,
                        contentPadding: EdgeInsets.all(5),
                        filled: true,
                        labelText: Lang.of(context).translate('phone_number'),
                      ),
                    ),
                  ),
                  SizedBox(height: 20,),
                  Padding(
                    padding: EdgeInsets.all(10),
                    child: InkWell(
                      onTap: () {_onRegisterClick();},
                      child: Container(
                        height: 50,
                        child: Center(child:
                        Text(Lang.of(context).translate("register"), style: TextStyle(color: Colors.white),)),
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
                        widget.changeLoginContentStateToLogin(LoginState.LOGIN);
                      },
                      child: Text("${Lang.of(context).translate("alreadyRegistered")} ${Lang.of(context).translate("login")}"),
                    ),
                  ),
                  SizedBox(height: 30,)
                ],
              ),
            ),
          ),
          StreamBuilder<bool>(
              stream: _registrationBloc.isLoadingObservable,
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

  void _onRegisterClick() {
    final firstName = firstNameTextEditingController.text.trim();
    final middleName = middleNameTextEditingController.text.trim();
    final surname = surNameTextEditingController.text.trim();
    final email = emailTextEditingController.text.trim();
    final password = passwordTextEditingController.text.trim();
    final login = loginTextEditingController.text.trim();
    final phoneNumber = phoneNumberTextEditingController.text.trim();

    emailValidation(email);
    passwordValidation(password);
    loginValidation(login);
    surNameValidation(surname);
    phoneNumberValidation(phoneNumber);
    firstNameValidation(firstName);

    if(emailErrorMessage== null && passwordErrorMessage == null && loginErrorMessage == null && surNameErrorMessage ==null && phoneNumberErrorMessage ==null && firstNameErrorMessage ==null) {
      _registrationBloc.attemptToLogin(RegisterCommandDto(
        password: password,
        login: login,
        email: email,
        firstName: firstName,
        phoneNumber: phoneNumber,
        middleName: middleName,
        surname: surname,
      ));
    }
  }

  void emailValidation(String value) {
    if(!isEmail(value)) {
      setState(() {
        emailErrorMessage =  Lang.of(context).translate("wrong_email_format");
      });
    }
  }

  void passwordValidation(String value) {
    if(value.length <6) {
      setState(() {
        passwordErrorMessage =  Lang.of(context).translate("value_too_short");
      });
    }
  }

  void firstNameValidation(String value) {
    if(value.length <2) {
      setState(() {
        firstNameErrorMessage =  Lang.of(context).translate("value_too_short");
      });
    }
  }

  void surNameValidation(String value) {
    if(value.length <3) {
      setState(() {
        surNameErrorMessage =  Lang.of(context).translate("value_too_short");
      });
    }
  }

  void phoneNumberValidation(String value) {
    if(value.length <6 && value.length>13) {
      setState(() {
        phoneNumberErrorMessage =  Lang.of(context).translate("wrong_phone_nubmer");
      });
    } else if(!isNumeric(value)) {
      setState(() {
        phoneNumberErrorMessage =  Lang.of(context).translate("wrong_phone_nubmer");
      });
    }
  }

  void loginValidation(String value) {
    if(value.length <4) {
      setState(() {
        loginErrorMessage = Lang.of(context).translate("value_too_short");
      });
    }
  }

  void _onError(ErrorRegisterType errorRegisterType) {
    switch(errorRegisterType) {
      case ErrorRegisterType.LOGIN_EXISTS:
        setState(() {
          loginErrorMessage = Lang.of(context).translate("login_exists");
        });
        break;
      case ErrorRegisterType.EMAIL_EXISTS:
        setState(() {
          emailErrorMessage = Lang.of(context).translate("email_exists");
        });
        break;
      case ErrorRegisterType.SERVER_ERROR:
        showToast(context, Lang.of(context).translate("server_error"));
        break;
      case ErrorRegisterType.CONNECTION_ERROR:
        showToast(context, Lang.of(context).translate("connection_error"));
        break;
    }
  }
}
