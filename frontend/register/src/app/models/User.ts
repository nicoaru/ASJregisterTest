export class User {
  constructor(
    private firstname?: string,
    private lastname?: string,
    private email?: string,
    private password?: string
  ) {}

  set _firstname(value: string | undefined) {
    this.firstname = value;
  }

  get _firstname(): string | undefined {
    return this.firstname;
  }

  set _lastname(value: string | undefined) {
    this.lastname = value;
  }

  get _lastname(): string | undefined {
    return this.lastname;
  }

  set _email(value: string | undefined) {
    this.email = value;
  }

  get _email(): string | undefined {
    return this.email;
  }
}
