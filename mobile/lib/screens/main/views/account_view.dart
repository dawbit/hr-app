import 'package:flutter/material.dart';
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
              child: CardWidget(icon: Icon(Icons.account_box), cardTitle: "Account",)
          ),
          Expanded(
            flex: 1,
            child: Column(
              children: [
                SubAccountCardWidget(icon: Icon(Icons.email), title: "Change Email", onTapFunction: onEmailTap,),
                SubAccountCardWidget(icon: Icon(Icons.lock), title: "Change Password", onTapFunction: onPasswordTap,),
                SubAccountCardWidget(icon: Icon(Icons.phone), title: "Change PhoneNumber", onTapFunction: onPhoneNumberTap,),
                SubAccountCardWidget(icon: Icon(Icons.upload_file), title: "Upload Cv", onTapFunction: onCvTap,),
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
}
