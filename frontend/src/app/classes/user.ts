import { AccountTypes } from './account-types';

export class User {
  'id': number;
  'firstName': string;
  'middleName': string;
  'surname': string;
  'email': string;
  'phoneNumber': string;
  'login': string;
  'password': string;
  'fk_userAccountTypes': AccountTypes;
  'isActive' = true;
}
