
import 'package:mobile/models/token.dart';
import 'package:shared_preferences/shared_preferences.dart';

class TokenSharedPref {

  static Future<void> clearToken() async {
    SharedPreferences sharedPreferences =  await SharedPreferences.getInstance();
    sharedPreferences.setString('token', null);
  }

  static Future<void> setToken(Token token) async {
    SharedPreferences sharedPreferences =  await SharedPreferences.getInstance();
    sharedPreferences.setString('token', token.accessToken);
  }

  static Future<String> getToken() async {
    SharedPreferences sharedPreferences =  await SharedPreferences.getInstance();
    return sharedPreferences.getString('token');
  }
}