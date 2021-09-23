export default class User {
  constructor(username, balance) {
    this.username = username;
    this.balance = balance;
  }
  getUsername() {
    return this.username;
  }
  setUsername(newUsername) {
    this.username = newUsername;
  }

  getHowMuchMoneyUserHasLeft() {
    return this.balance;
  }

  setHowMuchMoneyUserHasLeft(newAmount) {
    this.balance = newAmount;
  }
}