import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/announcement_apply_bloc.dart';
import 'package:mobile/blocs/announcements_bloc.dart';
import 'package:mobile/blocs/quiz_answer_bloc.dart';
import 'package:mobile/blocs/quiz_information_bloc.dart';
import 'package:mobile/blocs/quiz_question_bloc.dart';
import 'package:mobile/blocs/quiz_solver_bloc.dart';
import 'package:mobile/data_sources/remote/announcements_source.dart';
import 'package:mobile/data_sources/remote/api_client/api_provider.dart';
import 'package:mobile/data_sources/remote/quiz_source.dart';
import 'package:mobile/repositories/announcements_repository.dart';
import 'package:mobile/repositories/quiz_repository.dart';

import '../Application.dart';

class AppModule extends ModuleWidget {

  @override
  List<Bloc> get blocs => [
    Bloc((i) => QuizInformationBloc(i.get())),
    Bloc((i) => QuizQuestionBloc(i.get())),
    Bloc((i) => QuizAnswerBloc(i.get())),
    Bloc((i) => QuizSolverBloc(i.get())),
    Bloc((i) => AnnouncementsBloc(i.get())),
    Bloc((i) => AnnouncementApplyBloc(i.get())),
  ];

  @override
  List<Dependency> get dependencies => [
    Dependency((_) => getApiClient()),
    Dependency((i) => QuizSource(i.get())),
    Dependency((i) => AnnouncementsSource(i.get())),
    Dependency((i) => AnnouncementsRepository(i.get())),
    Dependency((i) => QuizRepository(i.get())),
  ];

  @override
  Widget get view => Application();
  static Inject get injector => Inject<AppModule>.of();

}