package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.ui.gui.AlertBox;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;


/**
 * The AuthenticationPane class is called when the user wants to login.
 * @version 1.0
 */
public class AuthenticationPane extends BorderPane {
  private final ModelManager manager;

  TextField usernameField;
  Button btnLogin;


  /**
   * Constructor for class AuthenticationPane that receives and save the modelManager,
   * Create views and all the aesthetic,
   * Controls the user actions,
   * Update the Root panel visibility
   * @param manager
   */
  public AuthenticationPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

  /**
   * The createViews method is capable of create all the content for the authentication
   *   Groups,
   *   Vbox,
   *   Lists,
   *   Buttons,
   *   Labels,
   *   And all the aesthetic,
   */
  private void createViews() {
    Label title = new Label("University Meals Made Easy");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);
    Label textFieldTitle = new Label("Username");
    usernameField = new TextField();
    usernameField.setPrefWidth(300);
    usernameField.setMaxWidth(300);
    btnLogin = new Button("Login");
    VBox centerVBox = new VBox(textFieldTitle, usernameField, btnLogin);
    centerVBox.setAlignment(Pos.CENTER);
    centerVBox.setSpacing(20);
    this.setCenter(centerVBox);
  }

  /**
   * The registerHandlers method can control the users actions,
   * and can show specific alertBox to keep users informed about the errors
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

    btnLogin.setOnAction(actionEvent -> {
      if(!manager.login(usernameField.getText())) {
        AlertBox alertBox = new AlertBox("Invalid" , "Invalid Username");
        alertBox.show();
      }
});
  }
  private void update() {this.setVisible(manager.getState() == State.AUTHENTICATION);}

}
