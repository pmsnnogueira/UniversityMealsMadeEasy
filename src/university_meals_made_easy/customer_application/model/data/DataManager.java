package university_meals_made_easy.customer_application.model.data;

import university_meals_made_easy.Logger;

import java.sql.SQLException;

public class DataManager {
  private final static String TAG = "DataManager";
  private String username;

  public boolean login(String username) throws NullPointerException {
    if (username == null)
      throw new NullPointerException("username cannot be null");
    if (username.isEmpty() || username.contains(" ")
        || username.contains("\t"))
      return false;
    this.username = username;
    try {
      DatabaseManager.getInstance().login(username);
    } catch (SQLException e) {
      Logger.getInstance().error(TAG,
          "couldn't login in the database manager");
    }
    return true;
  }
  public void logout() throws UnsupportedOperationException {
    if (username == null)
      throw new UnsupportedOperationException(
          "cannot logout if not logged in");
    username = null;
  }
}
