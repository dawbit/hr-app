import 'package:permission_handler/permission_handler.dart';

class PermissionHandler {
  static Future<bool> checkStoragePermission() async {
    if(!(await Permission.storage.isGranted)) {
      await Permission.storage.request();
      return await Permission.storage.isGranted;
    } else {
      return true;
    }
  }
}