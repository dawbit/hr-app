import 'package:dio/dio.dart';
import 'package:mobile/models/response_transfer.dart';
import 'package:retrofit/http.dart';
import 'package:retrofit/retrofit.dart';

part 'alerts_source.g.dart';

@RestApi(baseUrl: "http://192.168.43.228:8080/alerts")
abstract class AlertsSource{

  factory AlertsSource(Dio dio, {String baseUrl})= _AlertsSource;

  @GET("/user")
  Future<int> getAnnouncements();

  @POST("/user/{testParticipantId}/setAsRead")
  Future<ResponseTransfer> announcementApply(@Path("testParticipantId") int testParticipantId);

}