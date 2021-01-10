import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/models/change_email_command_dto.dart';
import 'package:mobile/models/change_password_command_dto.dart';
import 'package:mobile/models/change_phone_number_command_dto.dart';
import 'package:mobile/models/response_transfer.dart';
import 'package:mobile/repositories/account_repository.dart';
import 'package:rxdart/rxdart.dart';

class AccountBloc extends BlocBase {
  AccountRepository _accountRepository;

  AccountBloc(this._accountRepository);

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<ResponseTransfer> _changePasswordResponseSubject = PublishSubject();
  Stream<ResponseTransfer> get changePasswordResponseObservable => _changePasswordResponseSubject.stream;

  PublishSubject<ResponseTransfer> _changeEmailResponseSubject = PublishSubject();
  Stream<ResponseTransfer> get changeEmailResponseObservable => _changeEmailResponseSubject.stream;

  PublishSubject<ResponseTransfer> _changePhoneNumberResponseSubject = PublishSubject();
  Stream<ResponseTransfer> get changePhoneNumberResponseObservable => _changePhoneNumberResponseSubject.stream;

  PublishSubject<Object> _errorBodySubject = PublishSubject();
  Stream<Object> get errorBodyObservable => _errorBodySubject.stream;

  Future<void> changePassword(ChangePasswordCommandDto changePasswordCommandDto) async {
    _isLoadingSubject.add(true);
    _accountRepository.changePassword(changePasswordCommandDto).then(_onSuccessPassword).catchError(_onError);
  }

  Future<void> changeEmail(ChangeEmailCommandDto changeEmailCommandDto) async {
    _isLoadingSubject.add(true);
    _accountRepository.changeEmail(changeEmailCommandDto).then(_onSuccessEmail).catchError(_onError);
  }

  Future<void> changePhoneNumber(ChangePhoneNumberCommandDto changePhoneNumberCommandDto) async {
    _isLoadingSubject.add(true);
    _accountRepository.changePhoneNumber(changePhoneNumberCommandDto).then(_onSuccessPhoneNumber).catchError(_onError);
  }

  void _onSuccessPassword(ResponseTransfer responseTransfer) {
    _changePasswordResponseSubject.add(responseTransfer);
    _isLoadingSubject.add(false);
  }

  void _onSuccessEmail(ResponseTransfer responseTransfer) {
    _changeEmailResponseSubject.add(responseTransfer);
    _isLoadingSubject.add(false);
  }

  void _onSuccessPhoneNumber(ResponseTransfer responseTransfer) {
    _changePhoneNumberResponseSubject.add(responseTransfer);
    _isLoadingSubject.add(false);
  }

  void _onError(Object obj) {
    _errorBodySubject.add(obj);
    _isLoadingSubject.add(false);
  }


}