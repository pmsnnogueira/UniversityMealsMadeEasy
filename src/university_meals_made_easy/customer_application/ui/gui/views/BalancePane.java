package university_meals_made_easy.customer_application.ui.gui.views;

import com.sun.webkit.Timer;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import university_meals_made_easy.customer_application.model.ModelManager;

/**
 * The BalancePane class is called when the user clicks to add balance.
 * @version 1.0
 */
public class BalancePane extends BorderPane {
  private final ModelManager manager;

  Label usernameLabel;
  Label balanceLabel;
  Button btnAdd;


  /**
   * Constructor for class BalancePane that receives and save the modelManager,
   * Create views and all the aesthetic,
   * Controls the user actions,
   * Update the Root panel visibility
   * @param manager
   */
  public BalancePane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  /**
   * The createViews method is capable of create all the content for the adding balance like
   *   Groups,
   *   Vbox,
   *   Lists,
   *   Buttons,
   *   Labels,
   *   And all the aesthetic,
   */
  private void createViews() {
    usernameLabel = new Label("username");
    usernameLabel.setFont(Font.font(30));
    this.setTop(usernameLabel);

    balanceLabel = new Label("0 €");
    balanceLabel.setFont(Font.font(30));
    btnAdd = new Button("Add");
    HBox hBox = new HBox(balanceLabel, btnAdd);
    hBox.setSpacing(10);
    this.setCenter(hBox);
    this.setPadding(new Insets(30));
  }


  /**
   * The registerHandlers method can control the users actions,
   * and can increment the user balance
   */
  private void registerHandlers() {
    btnAdd.setOnAction(actionEvent -> {
      Dialog<ButtonType> dialog = new Dialog<>();
      ButtonType loginButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
      dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
      dialog.setContentText("Add balance");
      TextField textFieldBalance = new TextField();
      dialog.getDialogPane().setContent(textFieldBalance);
      dialog.show();
    });
  }

  /**
   */
  private void update() {

  }

}
