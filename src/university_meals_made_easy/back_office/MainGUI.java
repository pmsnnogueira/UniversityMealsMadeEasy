package university_meals_made_easy.back_office;

import javafx.application.Application;
import university_meals_made_easy.Logger;
import university_meals_made_easy.back_office.ui.gui.MainJFX;
import university_meals_made_easy.customer_application.model.data.DatabaseManager;

import java.sql.SQLException;

/**
 * Main class for launching the program
 */
public class MainGUI {
  private static final String TAG = "MainGUI";
  public static void main(String[] args) {
    if (1 <= args.length && !args[0].isBlank())
      Logger.createInstance(args[0]);
    else
      Logger.createInstance();
    try {
      DatabaseManager.getInstance();
      Application.launch(MainJFX.class, args);
    } catch (SQLException e) {
      Logger.getInstance().error(TAG,
          "couldn't get first database instance");
    } finally {
      try {
        DatabaseManager.getInstance().close();
      } catch (SQLException e) {
        Logger.getInstance().error(TAG,
            "couldn't close connection");
      }
    }
  }
}