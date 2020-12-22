import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/user_data_dto.dart';
import 'package:mobile/security/account_data_shared_pref.dart';
import 'package:mobile/utils/download_util.dart';
import 'package:mobile/utils/permissions_handeler.dart';
import 'package:mobile/values/sizes.dart';

class UserDataView extends StatefulWidget {
  @override
  _UserDataViewState createState() => _UserDataViewState();
}

class _UserDataViewState extends State<UserDataView> {

  String email = "";
  String phoneNumber = "";
  String cv;

  @override
  void initState() {
    super.initState();
    getData();
  }

  void getData() async {
    UserDataDto userDataDto = await AccountDataSharedPref.getAccountData();
    setState(() {
      email = userDataDto.email;
      phoneNumber = userDataDto.phoneNumber;
      cv = userDataDto.cv;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(Sizes.bigSpace),
      height: MediaQuery.of(context).size.height,
      width: MediaQuery.of(context).size.width,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
              child: Text(Lang.of(context).translate("my_data"), style: TextStyle(fontSize: Sizes.bigSize),)
          ),
          SizedBox(height: 20,),
          Row(
            children: [
              Expanded(
                  flex: 0,
                  child: Text("${Lang.of(context).translate("email")}: ")
              ),
              Expanded(
                  flex: 0,
                  child: Text(email)
              )
            ],
          ),
          SizedBox(height: 10,),
          Row(
            children: [
              Expanded(
                  flex: 0,
                  child: Text("${Lang.of(context).translate("phone_number")}: ")
              ),
              Expanded(
                  flex: 0,
                  child: Text(phoneNumber)
              ),
            ],
          ),
          SizedBox(height: 10,),
          Container(
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Expanded(
                    flex: 0,
                    child: Text("${Lang.of(context).translate("cv")}: ")
                ),
                Expanded(
                    flex: 0,
                    child: Container(
                      padding: cv== null ? EdgeInsets.all(0) : EdgeInsets.all(10),
                      decoration: BoxDecoration(
                        border: cv!= null ? Border.all(color: Colors.black, width: 1) :null
                      ),
                      child: Column(
                        children: [
                          cv== null ? Text(Lang.of(context).translate("no_cv")) :
                              InkWell(
                                  child: Icon(Icons.download_rounded, size: 50,),
                                onTap: () async {
                                    if(await PermissionHandler.checkStoragePermission()) {
                                      DownloadFile.download(cv);
                                    }},
                              ),
                        ],
                      ),
                    )
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
