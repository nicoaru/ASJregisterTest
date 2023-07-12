export class User {
  constructor(
    public firstname?: string,
    public lastname?: string,
    public email?: string,
    public password?: string
  ) {}

  set setFirstname(firstname: string) {
    this.firstname = firstname;
  }
  set setLastname(lastname: string) {
    this.lastname = lastname;
  }
  set setEmail(email: string) {
    this.email = email;
  }
}
