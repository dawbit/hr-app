import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:mobile/screens/main/main_screen.dart';
import 'package:mobile/screens/splash/splash_screen.dart';

import 'config/routes.dart';
import 'localizations/app_localization.dart';

class Application extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: "Hr-App",
      supportedLocales: _supportedLocales,
      localizationsDelegates: _localizationsDelegates,
      theme: ThemeData(
        brightness: Brightness.dark,
        primaryColor: Color(0xff2d2d2d),
        accentColor: Color(0xffc4b998),
      ),
      routes: _routes,
    );
  }

  Map<String, Widget Function(BuildContext)> get _routes => {
    splashScreenRoute: (context) => SplashScreen(),
    mainScreenRoute: (context) => MainScreen()
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
