import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GlobalConstants } from './../common/global-constants';

@Injectable({
  providedIn: 'root'
})
export class QuizService {
  private apiURL = GlobalConstants.apiURL;
  private baseQuizUrl = GlobalConstants.apiURL + '/quiz';
  private baseUrl = GlobalConstants.apiURL;
  private httpOptions = GlobalConstants.httpOptions;

  constructor(private http: HttpClient) { }

  getQuizInfo(quizCode: string): Observable<any> {
    if (quizCode) {
      return this.http.get(this.baseQuizUrl + '/getQuizInformations/' + quizCode);
    } else {
      return;
    }
  }

  getQuestion(quizId: number, testCode: string, currentQuestionNumber: number): Observable<any> {
    return this.http.get(this.baseQuizUrl + `/quizquestion/${quizId}/${testCode}/${currentQuestionNumber}`);
  }

  getFullQuizInfo(quizId: number) {
    return this.http.get(this.baseQuizUrl + `/backpossible/${quizId}`);
  }

  sendQuestionAnswer(value: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/question/setanswer`, value, { observe: 'response' });
  }

  sendQuiz(body: object): Observable<any> {
    return this.http.post(`${this.baseQuizUrl}/add`, body, { observe: 'response' });
  }

  getQuizList(): Observable<any>{
    return this.http.get(this.baseQuizUrl + '/list');
  }
}
