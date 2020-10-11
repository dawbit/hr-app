import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'app_localization_delegate.dart';

class Lang {
  final Locale locale;

  Lang(this.locale);

  static Lang of(BuildContext context) {
    return Localizations.of<Lang>(context, Lang);
  }

  static const LocalizationsDelegate<Lang> delegate = AppLocalizationDelegate();

  Map<String, String> _localizedStrings;

  Future<Lang> load() async {
    Map<String, dynamic> jsonMap = json.decode(await rootBundle.loadString('assets/i18n/${locale.languageCode}.json'));
    _localizedStrings = jsonMap.map((key, value) => MapEntry(key, value.toString()));
    return this;
  }

  String translate(String key) => _localizedStrings[key];
}
