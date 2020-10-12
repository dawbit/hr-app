import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GlobalConstants } from './../common/global-constants';

@Injectable({
  providedIn: 'root'
})
export class QuizService {
  private apiURL = GlobalConstants.apiURL;
  private baseUrl = GlobalConstants.apiURL + '/quiz';
  private httpOptions = GlobalConstants.httpOptions;

  constructor(private http: HttpClient) { }

  getQuizInfo(quizCode: string): Observable<any> {
    if (quizCode) {
      return this.http.get(this.baseUrl + '/getQuizInformations/' + quizCode);
    } else {
      return;
    }
  }

  getQuestion(quizId: number, testCode: string, currentQuestionNumber: number): Observable<any> {
    return this.http.get(this.baseUrl + `/quizquestion/${quizId}/${testCode}/${currentQuestionNumber}`);
  }

  getFullQuizInfo(quizId: number) {
    return this.http.get(this.baseUrl + `/backpossible/${quizId}`);
  }

}
