import 'package:dio/dio.dart';
import 'package:mobile/models/announcements_dto.dart';
import 'package:mobile/models/response_transfer.dart';
import 'package:retrofit/http.dart';
import 'package:retrofit/retrofit.dart';

part 'announcements_source.g.dart';

@RestApi(baseUrl: "http://192.168.43.228:8080/announcements")
abstract class AnnouncementsSource{

  factory AnnouncementsSource(Dio dio, {String baseUrl})= _AnnouncementsSource;

  @GET("/find")
  Future<List<AnnouncementsDto>> getAnnouncements(@Query("q") String query);

  @POST("/apply/{id}")
  Future<ResponseTransfer> announcementApply(@Path("id") int id);

}