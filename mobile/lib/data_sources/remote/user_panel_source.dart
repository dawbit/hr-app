import 'package:dio/dio.dart';
import 'package:mobile/models/user_panel_list_of_annoncements_dto.dart';
import 'package:retrofit/http.dart';
import 'package:retrofit/retrofit.dart';

part 'user_panel_source.g.dart';

@RestApi(baseUrl: "http://192.168.43.228:8080/user")
abstract class UserPanelSource{

  factory UserPanelSource(Dio dio, {String baseUrl})= _UserPanelSource;

  @GET("/list-of-applications")
  Future<List<UserPanelListOfAnnoncementsDto>> getListOfApplications();

}