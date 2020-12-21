import 'package:flutter/material.dart';
import 'package:mobile/screens/main/views/change_email_view.dart';
import 'package:mobile/screens/main/views/change_password_view.dart';
import 'package:mobile/screens/main/views/change_phone_number_view.dart';
import 'package:mobile/screens/main/views/notifications_view.dart';
import 'package:mobile/screens/main/views/single_announcement_view.dart';
import 'package:mobile/screens/main/views/single_notification_view.dart';
import 'package:mobile/screens/main/views/upload_cv_view.dart';

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
        if(routeSettings.name == '/singleAnnouncement') {
          return PageRouteBuilder(
            settings: routeSettings,
            transitionsBuilder: (context, animation, secondaryAnimation, child) => customTransitionsBuilder(context, animation, secondaryAnimation, child),
            pageBuilder: (context, animation, secondaryAnimation) => SingleAnnouncementView(announcementsDto: routeSettings.arguments,)
            ,);
        } else if(routeSettings.name == '/notifications') {
          return PageRouteBuilder(
            settings: routeSettings,
            transitionsBuilder: (context, animation, secondaryAnimation, child) => customTransitionsBuilder(context, animation, secondaryAnimation, child),
            pageBuilder: (context, animation, secondaryAnimation) => NotificationsView()
            ,);
        } else if(routeSettings.name == '/notificationsDetails') {
          return PageRouteBuilder(
            settings: routeSettings,
            transitionsBuilder: (context, animation, secondaryAnimation, child) => customTransitionsBuilder(context, animation, secondaryAnimation, child),
            pageBuilder: (context, animation, secondaryAnimation) => SingleNotificationView(userPanelListOfAnnoncementsDto: routeSettings.arguments,)
            ,);
        }
        else if(routeSettings.name == '/changeEmail') {
          return PageRouteBuilder(
            settings: routeSettings,
            transitionsBuilder: (context, animation, secondaryAnimation, child) => customTransitionsBuilder(context, animation, secondaryAnimation, child),
            pageBuilder: (context, animation, secondaryAnimation) => ChangeEmailView()
            ,);
        }
        else if(routeSettings.name == '/changePassword') {
          return PageRouteBuilder(
            settings: routeSettings,
            transitionsBuilder: (context, animation, secondaryAnimation, child) => customTransitionsBuilder(context, animation, secondaryAnimation, child),
            pageBuilder: (context, animation, secondaryAnimation) => ChangePasswordView()
            ,);
        }
        else if(routeSettings.name == '/changePhoneNumber') {
          return PageRouteBuilder(
            settings: routeSettings,
            transitionsBuilder: (context, animation, secondaryAnimation, child) => customTransitionsBuilder(context, animation, secondaryAnimation, child),
            pageBuilder: (context, animation, secondaryAnimation) => ChangePhoneNumberView()
            ,);
        }
        else if(routeSettings.name == '/uploadCv') {
          return PageRouteBuilder(
            settings: routeSettings,
            transitionsBuilder: (context, animation, secondaryAnimation, child) => customTransitionsBuilder(context, animation, secondaryAnimation, child),
            pageBuilder: (context, animation, secondaryAnimation) => UploadCvView()
            ,);
        }
        if (routeSettings.name!=initialRoute) {
          return PageRouteBuilder(
            transitionsBuilder: (context, animation, secondaryAnimation, child) => customTransitionsBuilder(context, animation, secondaryAnimation, child),
            pageBuilder: (context, animation, secondaryAnimation) => builder(context),
            settings: routeSettings,
          );
        }  else {
          return MaterialPageRoute(
            builder: builder,
            settings: routeSettings,
          );
        }
      },
    );
  }
}