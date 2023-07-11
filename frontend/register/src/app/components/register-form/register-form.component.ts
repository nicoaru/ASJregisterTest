import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../../models/User";
import {RegisterService} from "../../services/register.service";
import {toastError, toastSuccess} from "../../../main";
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {

  registerForm!: FormGroup;
  minChars: number = 2;
  maxChars: number = 25;
  user: User | undefined;
  payload: any;

  pass: RegExp = new RegExp( '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,20})');

  constructor(formBuilder: FormBuilder, private registerService: RegisterService, private router: Router, private activatedRoute:ActivatedRoute) {
    this.registerForm = formBuilder.group({
      firstname: ['', Validators.compose([Validators.required, Validators.minLength(this.minChars), Validators.maxLength(this.maxChars)])],
      lastname: ['', Validators.compose([Validators.required, Validators.minLength(this.minChars), Validators.maxLength(this.maxChars)])],
      email: ['', Validators.compose([Validators.required, Validators.email])],
      password: ['', Validators.compose([Validators.required, Validators.pattern(this.pass)])]
    })
  }

  addUser():void{
    this.user = new User(
      this.registerForm.value.firstname,
      this.registerForm.value.lastname,
      this.registerForm.value.email,
      this.registerForm.value.password
    )
    this.registerService.addUser(this.user).subscribe(
      (response:any) => {
        if(!response.error){
          toastSuccess
            .fire({
              icon: 'success',
              title: 'Usuario registrado'
            })
            .then(() => location.reload())
        }
      },
        (error:string)=> {
          toastError.fire({
            icon: 'error',
            title: error,
          })
      }
    )
  }

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        console.log('Current URL:', event.url); // Output the actual URL
      }
    });
    
    
    this.activatedRoute.queryParamMap.subscribe(params => {
      const bpmContextId = params.get("contextId");
      const bpmWorklistTaskId = params.get("bpmWorklistTaskId");
      console.log("bpmWorklistTaskId:   ", bpmWorklistTaskId);
      const bpmWorklistContext = params.get("bpmWorklistContext");
      console.log("bpmWorklistContext:   ", bpmWorklistContext);

      if(bpmContextId === null || bpmWorklistTaskId === null || bpmWorklistContext === null) return console.log("Faltan datos de BPM Context");

      this.registerService.getBpmPayload(bpmWorklistTaskId, bpmWorklistContext).subscribe({
        next: (response:any) => {
          if(!response.error){
            this.payload = response;
            
            console.log("payload:");
            console.dir(this.payload);
          }
        },
        error: (error:string)=> {
          console.log("Error solicitand bpm task payload: ", error)
        }
      })
    });


  }
}
