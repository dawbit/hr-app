import 'package:flutter/material.dart';
import 'package:mobile/injections/app_module.dart';

class MainScreen extends StatefulWidget {
  @override
  _MainScreenState createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {

  int bottomNavigationBarIndex;

  @override
  void initState() {
    super.initState();
    bottomNavigationBarIndex=0;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(),
      // bottomNavigationBar: BottomNavigationBar(
      //   backgroundColor: Theme.of(context).tabBarTheme.labelColor,
      //   currentIndex: bottomNavigationBarIndex,
      //   onTap: (index){},
      //   type: BottomNavigationBarType.fixed,
      //   items: [
      //     BottomNavigationBarItem(
      //       icon: Icon(Icons.info),
      //     ),
      //     BottomNavigationBarItem(
      //       icon: Icon(Icons.videogame_asset),
      //     ),
      //     BottomNavigationBarItem(
      //       icon: Icon(Icons.history),
      //     ),
      //   ],
      // ),
    );
  }
}
