import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../../models/User';
import { UserService } from '../../services/user.service';
import { toastError, toastSuccess } from '../../../main';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css'],
})
export class RegisterFormComponent implements OnInit {
  registerForm!: FormGroup;
  minChars: number = 2;
  maxChars: number = 25;
  user: User | undefined;
  payload: any;
  bpmContextId: any;
  bpmWorklistTaskId: any;
  bpmWorklistContext: any;

  pass: RegExp = new RegExp('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,20})');

  constructor(
    formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.registerForm = formBuilder.group({
      firstname: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(this.minChars),
          Validators.maxLength(this.maxChars),
        ]),
      ],
      lastname: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(this.minChars),
          Validators.maxLength(this.maxChars),
        ]),
      ],
      email: ['', Validators.compose([Validators.required, Validators.email])],
      password: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(this.pass),
        ]),
      ],
    });
  }

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        console.log('Current URL:', event.url); // Output the actual URL
      }
    });

    this.activatedRoute.queryParamMap.subscribe((params) => {
      this.bpmContextId = params.get('contextId');
      this.bpmWorklistTaskId = params.get('bpmWorklistTaskId');
      console.log('bpmWorklistTaskId:   ', this.bpmWorklistTaskId);
      this.bpmWorklistContext = params.get('bpmWorklistContext');
      console.log('bpmWorklistContext:   ', this.bpmWorklistContext);
    });

    this.getBpmPayload();
  }

  addUser(): void {
    this.user = new User(
      this.registerForm.value.firstname,
      this.registerForm.value.lastname,
      this.registerForm.value.email,
      this.registerForm.value.password
    );
    this.userService.addUser(this.user)
      .subscribe({
        next: async (response:any) => {
          if (!response.error) {

            const updatedPayload = {...this.payload, userId: response.id}
            console.log("payload para update: ", JSON.stringify(updatedPayload));
            await this.updateBpmPayload(updatedPayload);
            
            toastSuccess
              .fire({
                icon: 'success',
                title: 'Usuario registrado',
              })
              //.then(() => location.reload());
          }
        },
        error: (error: string) => {
          toastError.fire({
            icon: 'error',
            title: error,
          });
        }
      });
  }


  async getBpmPayload() {
    if (
      this.bpmContextId === null ||
      this.bpmWorklistTaskId === null ||
      this.bpmWorklistContext === null
    )
      return console.log('Faltan datos de BPM Context');

    this.userService
      .getBpmPayload(this.bpmWorklistTaskId, this.bpmWorklistContext)
      .subscribe({
        next: (response: any) => {
          if (!response.error) {
            this.payload = response;

            console.log('payload: ', JSON.stringify(this.payload));
          }
        },
        error: (error: string) => {
          console.log('Error solicitando bpm task payload: ', error);
        },
      });
  }


  async updateBpmPayload(updatedPayload:Record<string, string>) {
    console.log("Entró en updateBpmPayload");
    
    if(!this.payload) return console.log("No hay payload para actualizar")

    this.userService.updateBpmPayload(this.bpmWorklistTaskId, this.bpmWorklistContext, updatedPayload)
    .subscribe({
      next: (response: any) => {
        if (!response?.error) {
          console.log('updatedPayload: ', response);
        }
        
      },
      error: (error: string) => {
        console.log('Error haciendo update del payload: ', error);
      },
    });
    
  }


}
