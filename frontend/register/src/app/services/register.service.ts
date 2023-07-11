import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http";
import {User} from "../models/User";
import {catchError, Observable} from "rxjs";
import { provideAnimations } from '@angular/platform-browser/animations';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  _registerURL: string = "http://localhost:8080/api/users/register";
  _getPayloadURL:string = "http://localhost:8080/api/BPM/getPayload";

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
      console.log("Back Error: " + error);
      console.dir(error);
      throw new Error("Error de Back: " + ' - ' + error.message)
    }
  }
    
  addUser(user: User){
    return this.http.post<User>(this._registerURL, user, this.getHttpOptions()).pipe(catchError(this.handlerException));
  }

  getBpmPayload(bpmWorklistTaskId: string, bpmWorklistContext: string){
    let params = new HttpParams({fromObject: {
      bpmWorklistTaskId: bpmWorklistTaskId,
      bpmWorklistContext: bpmWorklistContext
    }});

    
    return this.http.get(this._getPayloadURL+"?"+params.toString(), this.getHttpOptions()).pipe(catchError(this.handlerException));
    
  }

}