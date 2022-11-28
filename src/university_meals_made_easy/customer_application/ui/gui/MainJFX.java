package university_meals_made_easy.customer_application.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.data.DataManager;

public class MainJFX extends Application {
  private final ModelManager manager;

  public MainJFX() {
    this.manager = new ModelManager(new DataManager());
  }

  @Override
  public void start(Stage stage) {
    configureStage(stage);
  }

  private void configureStage(Stage stage) {
    RootPane root = new RootPane(manager);
    Scene scene = new Scene(root, 1600, 900);
    stage.setScene(scene);
    stage.setTitle("UniversityMealsMadeEasy");
    stage.show();
    stage.setOnCloseRequest(windowEvent -> Platform.exit());
  }

}
