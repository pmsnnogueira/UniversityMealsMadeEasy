package university_meals_made_easy.customer_application.model.data;

import university_meals_made_easy.Logger;
import java.sql.SQLException;

/**
 * Class that manages user login state
 * logs in or out users and checks for the appropriate errors or exceptions of this process
 * @version 1.0
 */
public class DataManager {
  private final static String TAG = "DataManager";
  private String username;

  /**
   * Method for login of user according to username
   * recieves username and checks for its validaty before setting the current user as the user it recieved
   * @param username
   */
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

  /**
   * Method for logout of currently logged in user
   * after making the appropriate checks of weather a user is logged in, sets the current logged in user to null
   */
  public void logout() throws UnsupportedOperationException {
    if (username == null)
      throw new UnsupportedOperationException(
          "cannot logout if not logged in");
    username = null;
  }
}
