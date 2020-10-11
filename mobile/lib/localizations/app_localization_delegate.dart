import 'package:flutter/material.dart';
import 'app_localization.dart';

class AppLocalizationDelegate extends LocalizationsDelegate<Lang> {

  const AppLocalizationDelegate();

  @override
  bool isSupported(Locale locale) => ['en', 'pl'].contains(locale.languageCode);


  @override
  Future<Lang> load(Locale locale) => Lang(locale).load();


  @override
  bool shouldReload(LocalizationsDelegate<Lang> old) => false;

}
