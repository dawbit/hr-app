import 'package:flutter/material.dart';

class RoundedBottomClipper extends CustomClipper<Path> {

  @override
  getClip(Size size) {
    double height = size.height;
    double width = size.width;
    return Path()
    ..lineTo(0, height * 0.84)
    ..quadraticBezierTo(width / 2, height , width, height * 0.84)
    ..lineTo(width, 0);
  }

  @override
  bool shouldReclip(CustomClipper oldClipper) {
    return true;
  }
}