package university_meals_made_easy.database.tables;

public class AppUser {
  private final int id;
  private final String username;
  private final float balance;

  public AppUser(int id, String username, float balance)
      throws IllegalArgumentException {
    if (balance <= 0)
      throw new IllegalArgumentException("balance cannot be 0 or negative");
    this.id = id;
    this.username = username;
    this.balance = balance;
  }
  public int getId() {
    return id;
  }
  public String getUsername() {
    return username;
  }
  public float getBalance() {
    return balance;
  }
}
