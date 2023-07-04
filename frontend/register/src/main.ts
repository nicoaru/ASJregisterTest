import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import Swal from "sweetalert2";

import { AppModule } from './app/app.module';


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));

export const toastError = Swal.mixin({
  toast: true,
  position: 'bottom-end',
  color: '#232931',
  iconColor: '#232931',
  background: '#ff5c5c',
  showConfirmButton: false,
  timer: 1500,
  timerProgressBar: false,
});
export const toastSuccess = Swal.mixin({
  toast: true,
  position: 'bottom-end',
  color: '#232931',
  iconColor: '#232931',
  background: '#2aa98a',
  showConfirmButton: false,
  timer: 1500,
  timerProgressBar: false,
});
