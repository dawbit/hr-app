import 'package:flutter/material.dart';

SlideTransition customTransitionsBuilder(context, animation, secondaryAnimation, child) {
  var begin = Offset(1.0, 0.0);
  var end = Offset.zero;
  var curve = Curves.easeInOutExpo;

  var tween = Tween(begin: begin, end: end);
  var curvedAnimation = CurvedAnimation(
    parent: animation,
    curve: curve,
  );
  return SlideTransition(
    position: tween.animate(curvedAnimation),
    child: child,
  );
}