package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.scene.layout.BorderPane;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;


public class ReviewMealPane extends BorderPane {
  private final ModelManager manager;

  public ReviewMealPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

  private void createViews() {

  }

  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

  }

  private void update() {
    this.setVisible(manager.getState() == State.REVIEWAL);
  }
}
