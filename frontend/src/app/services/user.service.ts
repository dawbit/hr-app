import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GlobalConstants } from './../common/global-constants';
import { User } from '../classes/user';
// import { map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})

export class UserService {
  private apiURL = GlobalConstants.apiURL;
  private baseUrl = GlobalConstants.apiURL + '/user';
  private httpOptions = GlobalConstants.httpOptions;

  constructor(private http: HttpClient) { }

  login(credentials): Observable<any> {
    return this.http.post(this.apiURL + '/login', {
      login: credentials.login,
      password: credentials.password
    }, { observe: 'response' });
  }

  register(user: User): Observable<any> {
    return this.http.post(this.baseUrl + '/register', user, { observe: 'response' });
  }

  // getAllUsers(): Observable<any>{
  //   // this.http.get('http://localhost:8080/users/getall').toPromise().then(data => {
  //   //   for (let key in data){
  //   //     if (data.hasOwnProperty(key)) {
  //   //       this.elements.push({id: data[key].id, firstName: data[key].firstName, surname: data[key].surname, email: data[key].email});
  //   //     }
  //   //   }
  //   // });
  //   return this.http.get('http://localhost:8080/users/getall', this.httpOptions).pipe(map(this.extractData));
  // }

  // private extractData(res: Response) {
  //   return res || {}; // If 'res' is null, it returns empty object
  // }
}
