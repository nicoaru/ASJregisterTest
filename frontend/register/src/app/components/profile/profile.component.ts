import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { toastSuccess } from 'src/main';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent{
  updateForm!: FormGroup;
  minChars: number = 2;
  maxChars: number = 25;
  user: User | undefined;
  payload: any;

  constructor(
    formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.updateForm = formBuilder.group({
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
    });

    this.user = new User();
  }

  ngAfterViewInit() {
    // this.activatedRoute.queryParamMap.subscribe((params) => {
    //   const bpmContextId = params.get('contextId');
    //   const bpmWorklistTaskId = params.get('bpmWorklistTaskId');
    //   console.log('bpmWorklistTaskId:   ', bpmWorklistTaskId);
    //   const bpmWorklistContext = params.get('bpmWorklistContext');
    //   console.log('bpmWorklistContext:   ', bpmWorklistContext);

    //   if (
    //     bpmContextId === null ||
    //     bpmWorklistTaskId === null ||
    //     bpmWorklistContext === null
    //   )
    //     return console.log('Faltan datos de BPM Context');

    //   this.userService
    //     .getBpmPayload(bpmWorklistTaskId, bpmWorklistContext)
    //     .subscribe({
    //       next: (response: any) => {
    //         if (!response.error) {
    //           this.payload = response;

    //           console.log('payload:');
    //           console.dir(this.payload);
    this.getUser();
    //         }
    //       },
    //       error: (error: string) => {
    //         console.log('Error solicitando bpm task payload: ', error);
    //       },
    //     });
    // });
  }

  getUser() {
    this.userService.getUser('1').subscribe({
      next: (response: any) => {
        if (!response.error) {
          this.user = new User();
          this.user._firstname = response.firstname;
          this.user._lastname = response.lastname;
          this.user._email = response.email;
          console.dir(this.user);
          this.updateForm.patchValue({
            firstname: this.user._firstname,
            lastname: this.user._lastname,
            email: this.user._email,
          });
        }
      },
      error: (error: string) => {
        console.log('Error solicitando el usuario: ', error);
      },
    });
  }


  updateUser() {
    this.user!._firstname = this.updateForm.value.firstname;
    this.user!._lastname = this.updateForm.value.lastname;
    this.user!._email = this.updateForm.value.email;
    console.log(this.user);

    this.userService.updateUser('1', this.user).subscribe({
      next: (response: any) => {
        if (!response.error) {
          toastSuccess.fire({
            icon: 'success',
            title: 'Usuario modificado',
          });
        }
        console.log(response);
      },
      error: (error: string) => {
        console.log('Error actualizando el usuario: ', error);
      },
    });
  }
}
