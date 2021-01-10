import 'package:flutter_downloader/flutter_downloader.dart';
import 'package:mobile/security/token_shared_pref.dart';
import 'package:path_provider/path_provider.dart';

class DownloadFile {
  static Future<void> download(String url) async {
    if(url.startsWith("http://localhost:8080")) {
      url = url.replaceFirst("http://localhost:8080/", "http://192.168.43.228:8080/");
    }
    String token = await TokenSharedPref.getToken();
    Map <String, String> header = {"Authorization": "$token"};
    final taskId = await FlutterDownloader.enqueue(
      url: url,
      headers: header,
      savedDir: (await getExternalStorageDirectory()).path,
      showNotification: true, // show download progress in status bar (for Android)
      openFileFromNotification: true, // click on notification to open downloaded file (for Android)
    ).catchError((onError) => print(onError.toString()));
  }
}