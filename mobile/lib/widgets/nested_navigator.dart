import 'package:flutter/material.dart';

import 'custom_page_route_builder.dart';

class NestedNavigator extends StatelessWidget {
  final GlobalKey<NavigatorState> navigationKey;
  final String initialRoute;
  final Map<String, WidgetBuilder> routes;

  NestedNavigator({
    @required this.navigationKey,
    @required this.initialRoute,
    @required this.routes,
  });

  @override
  Widget build(BuildContext context) {
    return Navigator(
      key: navigationKey,
      initialRoute: initialRoute,
      onGenerateRoute: (RouteSettings routeSettings) {
        WidgetBuilder builder = routes[routeSettings.name];
        if (routeSettings.name!=initialRoute) {
          return PageRouteBuilder(
            transitionsBuilder: (context, animation, secondaryAnimation, child) => customTransitionsBuilder(context, animation, secondaryAnimation, child),
            pageBuilder: (context, animation, secondaryAnimation) => builder(context),
            settings: routeSettings,
          );
        } else {
          return MaterialPageRoute(
            builder: builder,
            settings: routeSettings,
          );
        }
      },
    );
  }
}