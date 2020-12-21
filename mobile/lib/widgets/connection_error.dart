import 'package:flutter/material.dart';
import 'package:mobile/localizations/app_localization.dart';

class ConnectionError extends StatelessWidget {

  final Function onButtonClick;

  ConnectionError(this.onButtonClick);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: MediaQuery.of(context).size.height,
      width: MediaQuery.of(context).size.width,
      child: Column(
        children: [
          Expanded(
              flex: 1,
              child: SizedBox()
          ),
          Expanded(
              flex: 0,
              child: Container(
                child: Column(
                  children: [
                    Icon(Icons.signal_cellular_connected_no_internet_4_bar, size: 50,),
                    SizedBox(height: 10,),
                    Text(Lang.of(context).translate("connection_error")),
                    SizedBox(height: 10,),
                    Container(
                      margin: EdgeInsets.symmetric(horizontal: 20),
                      child: MaterialButton(
                        color: Colors.yellow,
                        minWidth: MediaQuery.of(context).size.width,
                          onPressed: (){onButtonClick();},
                          child: Padding(
                            padding: const EdgeInsets.all(15),
                            child: Text(Lang.of(context).translate("reload")),
                          ),
                      ),
                    )
                  ],
                ),
              )
          ),
          Expanded(
              flex: 1,
              child: SizedBox()
          ),
        ],
      ),
    );
  }
}
