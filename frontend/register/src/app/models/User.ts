export class User {
  constructor(
    public firstname?: string,
    public lastname?: string,
    public email?: string,
    public password?: string
  ) {
  }

  set setFirstname(firstname: string) {
    this.firstname = firstname;
  }

  get getFirstname(): string {
    return <string>this.firstname;
  }

  set setLastname(lastname: string) {
    this.lastname = lastname;
  }

  get getLastname(): string {
    return <string>this.lastname;
  }

  set setEmail(email: string) {
    this.email = email;
  }

  get getEmail(): string {
    return <string>this.email;
  }
}
