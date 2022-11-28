package university_meals_made_easy.back_office.ui.gui.views;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.fsm.State;

public class ConfigurationPane extends BorderPane {
  private final ModelManager manager;

  public ConfigurationPane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  private void createViews() {
    Label title = new Label("Configuration");
    this.setTop(title);
  }

  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
  }

  private void update() {
    this.setVisible(manager.getState() == State.CONFIGURATION);
  }
}
