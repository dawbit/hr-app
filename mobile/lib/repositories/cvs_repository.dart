import 'dart:io';

import 'package:mobile/data_sources/remote/cvs_source.dart';
import 'package:mobile/models/upload_file_response_dto.dart';

class CvsRepository {

  CvsSource _cvsSource;

  CvsRepository(this._cvsSource);

  Future<UploadFileResponseDto> attemptToSendCv(File file) => _cvsSource.attemptToSendCv(file);

}