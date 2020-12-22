import 'package:mobile/models/user_data_dto.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AccountDataSharedPref {
  static Future<void> clearAccountData() async {
    SharedPreferences sharedPreferences =  await SharedPreferences.getInstance();
    sharedPreferences.setString('email', null);
    sharedPreferences.setString('phone_number', null);
    sharedPreferences.setString('cv', null);
  }

  static Future<void> setAccountData(UserDataDto userDataDto) async {
    SharedPreferences sharedPreferences =  await SharedPreferences.getInstance();
    sharedPreferences.setString('email', userDataDto.email);
    sharedPreferences.setString('phone_number', userDataDto.phoneNumber);
    sharedPreferences.setString('cv', userDataDto.cv);
  }

  static Future<UserDataDto> getAccountData() async {
    SharedPreferences sharedPreferences =  await SharedPreferences.getInstance();
    String email =  await sharedPreferences.getString('email');
    String phonenumber = await sharedPreferences.getString('phone_number');
    String cv = await sharedPreferences.getString('cv');

    return UserDataDto(
      phoneNumber: phonenumber,
      email: email,
      cv: cv,
    );
  }
}