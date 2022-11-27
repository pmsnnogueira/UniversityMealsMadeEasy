package university_meals_made_easy.customer_application.model.data;

public class DataManager {
  private String username;

  public void login(String username) throws NullPointerException {
    if (username == null)
      throw new NullPointerException("username cannot be null");
    this.username = username;
  }
  public void logout() throws UnsupportedOperationException {
    if (username == null)
      throw new UnsupportedOperationException(
          "cannot logout if not logged in");
    username = null;
  }
}
