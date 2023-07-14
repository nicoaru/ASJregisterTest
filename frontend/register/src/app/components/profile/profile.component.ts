import { AfterViewInit, Component, OnInit } from '@angular/core';
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
export class ProfileComponent implements AfterViewInit {
  updateForm!: FormGroup;
  minChars: number = 2;
  maxChars: number = 25;
  user: User | undefined;
  payload: any;
  bpmContextId: any;
  bpmWorklistTaskId: any;
  bpmWorklistContext: any;

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

  getUser() {
    this.userService.getUser(this.payload.userId).subscribe({
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

    this.userService.updateUser(this.payload.userId, this.user).subscribe({
      next: (response: any) => {
        if (!response.error) {
          if(this.payload) {
            const updatedPayload = {...this.payload, userId: response.id}
            this.avanzarSolicitud(updatedPayload);
          }
          else console.log("No se pudo obtener el payload. No se puede avanzar.");

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

  async getBpmPayload() {
    if (
      this.bpmContextId === null ||
      this.bpmWorklistTaskId === null ||
      this.bpmWorklistContext === null
    )
      return console.log('Faltan datos de BPM para obtener el payload');

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

  avanzarSolicitud(updatedPayload: Record<string, string>) {
    console.log('Entró en avanzarSolicitud');

    const body: Record<string, string> = {
      ...updatedPayload,
      outcome: 'OK',
    };

    this.userService
      .avanzarBpmProcess(this.bpmWorklistTaskId, this.bpmWorklistContext, body)
      .subscribe({
        next: (response: any) => {
          if (!response?.error) {
            console.log('AVANZAR SOLICITUD: OK');
          }
        },
        error: (error: string) => {
          console.log('Error haciendo avanzar la solicitud: ', error);
        },
      });
  }

  // ngOnInit(): void {
  //   this.activatedRoute.queryParamMap.subscribe((params) => {
  //     this.bpmContextId = params.get('contextId');
  //     this.bpmWorklistTaskId = params.get('bpmWorklistTaskId');
  //     this.bpmWorklistContext = params.get('bpmWorklistContext');

  //     console.log('bpmWorklistTaskId:   ', this.bpmWorklistTaskId);
  //     console.log('bpmWorklistContext:   ', this.bpmWorklistContext);
  //   });

  //   this.getBpmPayload();
    
  // }

  ngAfterViewInit() {
    this.activatedRoute.queryParamMap.subscribe((params) => {
      this.bpmContextId = params.get('contextId');
      this.bpmWorklistTaskId = params.get('bpmWorklistTaskId');
      this.bpmWorklistContext = params.get('bpmWorklistContext');

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

              console.log('payload:');
              console.dir(this.payload);
              this.getUser();
            }
          },
          error: (error: string) => {
            console.log('Error solicitando bpm task payload: ', error);
          },
        });
    });
  }
}
