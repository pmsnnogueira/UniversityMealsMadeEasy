package university_meals_made_easy.customer_application;

import javafx.application.Application;
import university_meals_made_easy.Logger;
import university_meals_made_easy.customer_application.model.data.DatabaseManager;
import university_meals_made_easy.customer_application.ui.gui.MainJFX;

import java.sql.SQLException;

/**
 * The MainGUI Class launch an BackOffice application capable of show and control every details
 * to the canteen staff users
 * @author
 * Gonçalo Salgueirinho
 * a2020142627@isec.pt
 * Pedro Nogueira
 * a2020136533@isec.pt
 * Henrique Santos
 * a2020142829@isec.pt
 * João Almeida
 * a2020144466@isec.pt
 * Kylix Afonso
 * a2020146228@isec.pt
 *
 * @version 1.0
 * @since 05-12-2022
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
