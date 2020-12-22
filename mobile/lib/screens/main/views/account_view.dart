import 'package:flutter/material.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/screens/main/widgets/card_widget.dart';
import 'package:mobile/screens/main/widgets/sub_account_card_widgetr.dart';

import '../../../Application.dart';

class AccountView extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.purple,
      child: Column(
        children: [
          Expanded(
              flex: 0,
              child: CardWidget(icon: Icon(Icons.account_box), cardTitle: Lang.of(context).translate("my_account"),)
          ),
          Expanded(
            flex: 1,
            child: Column(
              children: [
                SubAccountCardWidget(icon: Icon(Icons.view_carousel_rounded), title: Lang.of(context).translate("my_data"), onTapFunction: onUserDataTap,),
                SubAccountCardWidget(icon: Icon(Icons.email), title: Lang.of(context).translate("change_email"), onTapFunction: onEmailTap,),
                SubAccountCardWidget(icon: Icon(Icons.lock), title: Lang.of(context).translate("change_password"), onTapFunction: onPasswordTap,),
                SubAccountCardWidget(icon: Icon(Icons.phone), title: Lang.of(context).translate("change_phone_number"), onTapFunction: onPhoneNumberTap,),
                SubAccountCardWidget(icon: Icon(Icons.upload_file), title: Lang.of(context).translate("upload_cv"), onTapFunction: onCvTap,),
              ],
            ),
          ),
        ],
      ),
    );
  }

  void onEmailTap() {
    Navigator.of(navigationKey.currentContext).pushNamed('/changeEmail');
  }
  void onPasswordTap() {
    Navigator.of(navigationKey.currentContext).pushNamed('/changePassword');
  }
  void onPhoneNumberTap() {
    Navigator.of(navigationKey.currentContext).pushNamed('/changePhoneNumber');
  }
  void onCvTap() {
    Navigator.of(navigationKey.currentContext).pushNamed('/uploadCv');
  }
  void onUserDataTap() {
    Navigator.of(navigationKey.currentContext).pushNamed('/myData');
  }
}
