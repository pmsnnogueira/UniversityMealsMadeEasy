package university_meals_made_easy.customer_application.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.data.DataManager;

/**
 * The MainJFX Class calls the backoffice application
 * @version 1.0
 */
public class MainJFX extends Application {
  private final ModelManager manager;

  /**
   * Constructor for MainJFX that creates a new ModelManager with a Data Manager
   */
  public MainJFX() {
    this.manager = new ModelManager(new DataManager());
  }


  /**
   * This functions prepare the scene
   * to starts the BackOffice application
   *
   * @param stage
   */
  @Override
  public void start(Stage stage) {
    configureStage(stage);
  }


  /**
   * Configure the scene to receive the BackOffice UI
   * Set styles for the stage and shows the UI
   *
   * @param stage
   */
  private void configureStage(Stage stage) {
    RootPane root = new RootPane(manager);
    Scene scene = new Scene(root, 1600, 900);
    stage.setScene(scene);
    stage.setTitle("UniversityMealsMadeEasy");
    stage.show();
    stage.setOnCloseRequest(windowEvent -> Platform.exit());
  }

}
