import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:mobile/injections/login_module.dart';
import 'package:mobile/screens/main/main_screen.dart';
import 'package:mobile/screens/quiz/quiz_screen.dart';
import 'package:mobile/screens/splash/splash_screen.dart';

import 'config/routes.dart';
import 'localizations/app_localization.dart';

class Application extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Listener(
      onPointerDown: (_) {
        FocusScopeNode currentFocus = FocusScope.of(context);
        if (!currentFocus.hasPrimaryFocus && currentFocus.focusedChild != null) {
          currentFocus.focusedChild.unfocus();
        }
      },
      child: MaterialApp(
        onGenerateRoute: (settings) {
          if(settings.name == quizScreenRoute) {
            return _createRoute(settings);
          } else {
            return null;
          }
        },
        debugShowCheckedModeBanner: false,
        title: "Hr-App",
        supportedLocales: _supportedLocales,
        localizationsDelegates: _localizationsDelegates,
        routes: _routes,
      ),
    );
  }

  Route _createRoute(RouteSettings settings) {
    return PageRouteBuilder(
      transitionsBuilder: (context, animation, secondaryAnimation, child) {
        var begin = Offset(1.0, 0.0);
        var end = Offset.zero;
        var curve = Curves.easeInOutExpo;

        var tween = Tween(begin: begin, end: end);
        var curvedAnimation = CurvedAnimation(
          parent: animation,
          curve: curve,
        );
        return SlideTransition(
          position: tween.animate(curvedAnimation),
          child: child,
        );
      },
      pageBuilder: (context, animation, secondaryAnimation) => QuizScreen(quizInformationDto: settings.arguments),
    );
  }

  Map<String, Widget Function(BuildContext)> get _routes => {
    splashScreenRoute: (context) => SplashScreen(),
    mainScreenRoute: (context) => MainScreen(),
    loginScreenRoute: (context) => LoginModule(),
  };

  List<Locale> get _supportedLocales => [
    Locale('en'),
    Locale('pl'),
  ];

  List<LocalizationsDelegate> get _localizationsDelegates => [
    Lang.delegate,
    GlobalMaterialLocalizations.delegate,
    GlobalWidgetsLocalizations.delegate,
  ];
}
