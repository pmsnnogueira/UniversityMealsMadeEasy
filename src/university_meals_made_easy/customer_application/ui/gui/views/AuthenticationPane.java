package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;

public class AuthenticationPane extends BorderPane {
  private final ModelManager manager;

  TextField usernameField;
  Button btnLogin;

  public AuthenticationPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

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

  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

    btnLogin.setOnAction(actionEvent -> {
      if(!manager.login(usernameField.getText())) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setContentText("Invalid username");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.show();
      }
    });
  }

  private void update() {
    this.setVisible(manager.getState() == State.AUTHENTICATION);
  }
}
