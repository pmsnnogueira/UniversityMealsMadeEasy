package university_meals_made_easy.back_office.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.resources.ImageManager;

/**
 * This view is a border pane that contains simple information about the app
 * and its usage
 */
public class MainMenuPane extends BorderPane {
  private final ModelManager manager;

  /**
   * Constructor for MainMenu
   * @param manager ModelManager instance
   */
  public MainMenuPane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  /**
   * This method is called once this object is created, it will configure every
   * aspect of the gui, where all buttons, text field and other elements appear.
   * In this case a little TextView and a Label with some information.
   */
  private void createViews() {
    Label title = new Label("Main Menu");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);
    ImageView imageView = new ImageView(ImageManager.getImage("cantina.jpg"));
    this.setCenter(imageView);
  }

  /**
   * this method is called after creating the view.
   * It's responsible to register all handlers and listeners
   * so the elements can be used.
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
  }
  /**
   * this method is responsible to update the entire view everytime it
   * needs after a change
   */
  private void update() {
    this.setVisible(manager.getState() == State.MAIN_MENU);
  }
}
