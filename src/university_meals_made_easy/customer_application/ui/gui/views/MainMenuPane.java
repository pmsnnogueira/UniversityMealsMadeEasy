package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.scene.layout.BorderPane;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;

public class MainMenuPane extends BorderPane {
  private final ModelManager manager;

  public MainMenuPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

  private void createViews() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

  }

  private void registerHandlers() {
    this.setVisible(manager.getState() == State.MAIN_MENU);
  }

  private void update() {

  }
}
