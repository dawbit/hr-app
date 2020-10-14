import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/src/widgets/framework.dart';

import '../Application.dart';

class AppModule extends ModuleWidget {

  @override
  List<Bloc> get blocs => [
    //Bloc((i) => LolApiAccountInfoBloc(i.get())),
  ];

  @override
  List<Dependency> get dependencies => [
    //Dependency((_) => ServerSelector()),
  ];

  @override
  Widget get view => Application();
  static Inject get injector => Inject<AppModule>.of();

}