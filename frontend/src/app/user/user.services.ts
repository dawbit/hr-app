import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrlUser = 'http://localhost:8080/user';
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
      'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token'
    })
  };

  constructor(private http: HttpClient) {}

  getUserList(): Observable<any> {
    return this.http.get(this.baseUrlUser, this.httpOptions).pipe(
    map(this.extractData));
  }

  private extractData(res: Response) {
    return res || {}; // If 'res' is null, it returns empty object
  }

  getUser(id: number): Observable<any> {
    return this.http.get(`${this.baseUrlUser}/id/${id}`);
  }

  createUser(User: object): Observable<object> {
    return this.http.post(`${this.baseUrlUser}/save`, User);
  }

  updateUser(id: number, value: any): Observable<object> {
    return this.http.put(`${this.baseUrlUser}/update`, value);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrlUser}/delete?id=${id}`, {
      responseType: 'text'
    });
  }

}
