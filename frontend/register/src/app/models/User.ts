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
  set setLastname(user: User) {
    this.lastname = user.lastname;
  }
  set setEmail(user: User) {
    this.email = user.email;
  }
}
