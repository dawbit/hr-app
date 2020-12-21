import 'package:mobile/data_sources/remote/account_source.dart';
import 'package:mobile/models/change_email_command_dto.dart';
import 'package:mobile/models/change_password_command_dto.dart';
import 'package:mobile/models/change_phone_number_command_dto.dart';
import 'package:mobile/models/response_transfer.dart';

class AccountRepository {

  AccountSource _accountSource;

  AccountRepository(this._accountSource);

  Future<ResponseTransfer> changeEmail(ChangeEmailCommandDto changeEmailCommandDto) => _accountSource.changeEmail(changeEmailCommandDto);

  Future<ResponseTransfer> changePassword(ChangePasswordCommandDto changePasswordCommandDto) => _accountSource.changePassowrd(changePasswordCommandDto);

  Future<ResponseTransfer> changePhoneNumber(ChangePhoneNumberCommandDto changePhoneNumberCommandDto) => _accountSource.changePhoneNumber(changePhoneNumberCommandDto);


}