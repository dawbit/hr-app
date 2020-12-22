import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mobile/screens/login/widgets/register_holder.dart';

void main() {
  testWidgets('Button is present and triggers navigation after tapped',
          (WidgetTester tester) async {
        await tester.pumpWidget(
          MaterialApp(
            home: RegisterHolder(),
          ),
        );

        final textWidget = find.widgetWithText(TextFormField, "E-mail");
        final button = find.byType(InkWell);

        tester.enterText(textWidget, "sdgsdg");

        tester.tap(button);

        expect(find.byType(BottomNavigationBarItem), findsOneWidget);
        await tester.tap(find.byType(BottomNavigationBarItem));
        await tester.pumpAndSettle();
      });
}