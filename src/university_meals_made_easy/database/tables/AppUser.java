package university_meals_made_easy.database.tables;

/**
 * The AppUser Class contains a specific user.
 * Has the methods to return information about the user.
 */
public class AppUser {
  private final int id;
  private final String username;
  private final float balance;

  /**
   * Constructor for AppUser class that
   * receives some information about the user,
   * and assign them to the local variables.
   * @param id
   * @param username
   * @param balance
   * @throws IllegalArgumentException
   */
  public AppUser(int id, String username, float balance)
      throws IllegalArgumentException {
    if (balance <= 0)
      throw new IllegalArgumentException("balance cannot be 0 or negative");
    this.id = id;
    this.username = username;
    this.balance = balance;
  }

  /**
   * The getId method returns an id for a specific user.
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * The getUsername method returns the username for a specific user.
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * The getBalance method returns the user balance.
   * @return balance
   */
  public float getBalance() {
    return balance;
  }
}
