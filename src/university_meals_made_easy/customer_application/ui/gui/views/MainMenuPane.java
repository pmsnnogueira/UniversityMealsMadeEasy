package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.resources.ImageManager;


/**
 * The MainMenuPane class is called when the user clicks to watch the main menu.
 * @version 1.0
 */
public class MainMenuPane extends BorderPane {
  private final ModelManager manager;

  /**
   * Constructor for class MainMenuPane that receives and save the modelManager,
   * Create views and all the aesthetic,
   * Controls the user actions,
   * Update the Root panel visibility
   * @param manager
   */
  public MainMenuPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

  /**
   * The createViews method is capable of create all the content for the Review meal view like
   *   Vbox,
   *   Labels,
   *   And all the aesthetic,
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
   * The registerHandlers method can control the users actions
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
  }


  /**
   * The update method can change the menu visibility
   */
  private void update() {
    this.setVisible(manager.getState() == State.MAIN_MENU);
  }
}
