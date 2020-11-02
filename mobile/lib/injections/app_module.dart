import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/quiz_answer_bloc.dart';
import 'package:mobile/blocs/quiz_information_bloc.dart';
import 'package:mobile/blocs/quiz_question_bloc.dart';
import 'package:mobile/data_sources/remote/quiz_source.dart';
import 'package:mobile/repositories/quiz_repository.dart';
import 'package:mobile/security/token_shared_pref.dart';

import '../Application.dart';

class AppModule extends ModuleWidget {

  @override
  List<Bloc> get blocs => [
    Bloc((i) => QuizInformationBloc(i.get())),
    Bloc((i) => QuizQuestionBloc(i.get())),
    Bloc((i) => QuizAnswerBloc(i.get())),
  ];

  @override
  List<Dependency> get dependencies => [
    Dependency((_) => Dio()),
    Dependency((i) => QuizSource(i.get())),
    Dependency((i) => QuizRepository(i.get())),
    Dependency((_) => TokenSharedPref()),
  ];

  @override
  Widget get view => Application();
  static Inject get injector => Inject<AppModule>.of();

}