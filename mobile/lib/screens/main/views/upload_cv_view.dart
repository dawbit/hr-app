import 'dart:io';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/cvs_bloc.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/values/sizes.dart';

class UploadCvView extends StatefulWidget {
  @override
  _UploadCvViewState createState() => _UploadCvViewState();
}

class _UploadCvViewState extends State<UploadCvView> {

  CvsBloc _cvsBloc;

  File file;

  @override
  void initState() {
    super.initState();
    _cvsBloc =AppModule.injector.getBloc();
  }



  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(Sizes.bigSpace),
      height: MediaQuery.of(context).size.height,
      width: MediaQuery.of(context).size.width,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Container(
              child: Text(Lang.of(context).translate("upload_cv"), style: TextStyle(fontSize: Sizes.bigSize),)
          ),
          Padding(
            padding: const EdgeInsets.only(top: 100, bottom: 10, left: 10, right: 10),
            child: InkWell(
              onTap: (){filePicker();},
              child: Container(
                width: MediaQuery.of(context).size.width,
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.black, width: 1)
                ),
                child: Padding(
                    padding: EdgeInsets.all(10),
                    child: Icon(Icons.upload_rounded, size: 50,)),
              ),
            )
          ),
          SizedBox(height: 40,),
          file != null ?
          Padding(
              padding: const EdgeInsets.only(top: 10, bottom: 10, left: 10, right: 10),
              child: Center(child: Text(file.path),)
          ) : Container(),
          file != null ?
          Padding(
              padding: const EdgeInsets.only(top: 10, bottom: 10, left: 10, right: 10),
              child: Container(
                padding: EdgeInsets.all(10),
                width: MediaQuery.of(context).size.width,
                child: MaterialButton(
                  onPressed: (){_cvsBloc.attemptToSendCv(file);},
                  child: Text(Lang.of(context).translate("upload_cv"), style: TextStyle(fontSize: 16),),
                ),
              )
          ): Container()
        ],
      ),
    );
  }

  void filePicker() async {
    FilePickerResult result = await FilePicker.platform.pickFiles(
      allowedExtensions: ['pdf'],
      type: FileType.custom
    );

    if(result != null) {
      setState(() {
        file = File(result.files.single.path);
      });
    } else {
    }
  }
}
