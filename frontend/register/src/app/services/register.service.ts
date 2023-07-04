import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {User} from "../models/User";
import {catchError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  _registerURL: string = "http://localhost:8080/api/users/register"
  constructor(private http: HttpClient) { }

  private getHttpOptions(){
    return {
      headers: new HttpHeaders({
        'content-type': 'application/json'
      })
    }
  }

  private handlerException(error: HttpErrorResponse): any{
    if(error.error instanceof ErrorEvent){
      console.log("Front Error: " + error.status + ' - ' + error.error.message)
      throw new Error("Error de Front: " + error.error.message)
    }else {
      console.log("Back Error: " + error.error.message)
      throw new Error("Error de Back: " + ' - ' + error.error.message)
    }
  }
    addUser(user: User){
      return this.http.post<User>(this._registerURL, user, this.getHttpOptions()).pipe(catchError(this.handlerException));
    }

}
