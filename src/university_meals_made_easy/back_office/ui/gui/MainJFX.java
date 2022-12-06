package university_meals_made_easy.back_office.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.data.DataManager;

/**
 * Responsible class for launching the graphical user interface
 */
public class MainJFX extends Application {
  private final ModelManager manager;

  /**
   * Constructor for MainJFX
   */
  public MainJFX() {
    this.manager = new ModelManager(new DataManager());
  }

  /**
   * Method responsible for creating the main window of the gui
   * @param stage
   */
  @Override
  public void start(Stage stage) {
    configureStage(stage);
  }

  /**
   * This method configures all the needed aspects of the main gui window
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
