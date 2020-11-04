import 'package:flutter/material.dart';

class ConnectionError extends StatelessWidget {

  final Function reloadPage;

  ConnectionError(this.reloadPage);

  @override
  Widget build(BuildContext context) {
    return Container(
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
                    Icon(Icons.error, size: 50,),
                    SizedBox(height: 10,),
                    Text("Coś poszło nie tak"),
                    SizedBox(height: 10,),
                    Container(
                      margin: EdgeInsets.symmetric(horizontal: 20),
                      child: MaterialButton(
                        color: Colors.yellow,
                        minWidth: MediaQuery.of(context).size.width,
                          onPressed: (){reloadPage();},
                          child: Padding(
                            padding: const EdgeInsets.all(15),
                            child: Text("Załaduj ponownie"),
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
