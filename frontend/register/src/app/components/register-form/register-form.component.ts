import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../../models/User";
import {RegisterService} from "../../services/register.service";
import {toastError, toastSuccess} from "../../../main";

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent {

  registerForm!: FormGroup;
  minChars: number = 2;
  maxChars: number = 25;
  user: User | undefined;

  pass: RegExp = new RegExp( '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,20})')

  constructor(formBuilder: FormBuilder, private registerService: RegisterService) {
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
}
