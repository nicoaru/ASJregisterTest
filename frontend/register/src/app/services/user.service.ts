import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
  HttpParams,
} from '@angular/common/http';
import { User } from '../models/User';
import { catchError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  _userURL: string = 'http://localhost:8080/api/users';
  _bpmPayloadURL: string = 'http://localhost:8080/api/BPM/payload';
  _bpmAvanzarUrl: string = 'http://localhost:8080/api/BPM/avanzar';

  constructor(private http: HttpClient) {}

  private getHttpOptions() {
    return {
      headers: new HttpHeaders({
        'content-type': 'application/json',
      }),
    };
  }

  private handlerException(error: HttpErrorResponse): any {
    if (error.error instanceof ErrorEvent) {
      console.log('Front Error: ' + error.status + ' - ' + error.error.message);
      throw new Error('Error de Front: ' + error.error.message);
    } else {
      console.log('Back Error: ' + error);
      console.dir(error);
      throw new Error('Error de Back: ' + ' - ' + error.message);
    }
  }

  addUser(user: User) {
    return this.http
      .post<User>(this._userURL + '/register', user, this.getHttpOptions())
      .pipe(catchError(this.handlerException));
  }

  getUser(userId: string): any {
    return this.http
      .get(this._userURL + `/${userId}`, this.getHttpOptions())
      .pipe(catchError(this.handlerException));
  }

  updateUser(userId: string, user: User | undefined) {
    return this.http
      .put(this._userURL + `/${userId}`, user, this.getHttpOptions())
      .pipe(catchError(this.handlerException));
  }




  getBpmPayload(bpmWorklistTaskId: string, bpmWorklistContext: string) {
    let params = new HttpParams({
      fromObject: {
        bpmWorklistTaskId: bpmWorklistTaskId,
        bpmWorklistContext: bpmWorklistContext,
      },
    });

    return this.http
      .get(this._bpmPayloadURL + '?' + params.toString(), this.getHttpOptions())
      .pipe(catchError(this.handlerException));
  }

  updateBpmPayload(bpmWorklistTaskId: string, bpmWorklistContext: string, updatedPayload:Record<string, string>) {
    let params = new HttpParams({
      fromObject: {
        bpmWorklistTaskId: bpmWorklistTaskId,
        bpmWorklistContext: bpmWorklistContext,
      },
    });

    return this.http
      .put(this._bpmPayloadURL + '?' + params.toString(), updatedPayload, this.getHttpOptions())
      .pipe(catchError(this.handlerException));
  }

  avanzarBpmProcess(bpmWorklistTaskId: string, bpmWorklistContext: string, body:Record<string, string>) {
    let params = new HttpParams({
      fromObject: {
        bpmWorklistTaskId: bpmWorklistTaskId,
        bpmWorklistContext: bpmWorklistContext,
      },
    });

    return this.http
      .post(this._bpmAvanzarUrl + '?' + params.toString(), body, this.getHttpOptions())
      .pipe(catchError(this.handlerException));
  }







}
