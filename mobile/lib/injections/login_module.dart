import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:dio/dio.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:mobile/blocs/login_bloc.dart';
import 'package:mobile/data_sources/remote/authorization_source.dart';
import 'package:mobile/repositories/login_repository.dart';
import 'package:mobile/screens/login/login_screen.dart';

class LoginModule extends ModuleWidget {

  @override
  List<Bloc> get blocs => [
    Bloc((i) => LoginBloc(i.get())),
  ];

  @override
  List<Dependency> get dependencies => [
    Dependency((i) => AuthorizationSource(i.get())),
    Dependency((i) => LoginRepository(i.get())),
    Dependency((_) => Dio()..options.connectTimeout=5000)
  ];

  @override
  Widget get view => LoginScreen();

  static Inject get injector => Inject<LoginModule>.of();

}