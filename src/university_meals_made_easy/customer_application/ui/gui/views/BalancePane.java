package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.ui.gui.AlertBox;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.data.result.BalanceTopOffResult;

import java.util.Optional;

/**
 * The BalancePane class is called when the user clicks to add balance.
 * @version 1.0
 */
public class BalancePane extends BorderPane {
  private final ModelManager manager;

  private Label usernameLabel;
  private Label balanceLabel;
  private Button btnAdd;


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
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

    btnAdd.setOnAction(actionEvent -> {
      Dialog<ButtonType> dialog = new Dialog<>();
      ButtonType loginButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
      dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
      dialog.setContentText("Add balance");
      TextField textFieldBalance = new TextField();
      dialog.getDialogPane().setContent(textFieldBalance);
      Optional<ButtonType> result = dialog.showAndWait();
      if(result.isPresent()) {
        if(result.get() != ButtonType.CANCEL) {
          String balance = textFieldBalance.getText();
          if(balance == null || balance.isBlank() || balance.isEmpty())
            return;
          try {
            float value = Float.parseFloat(balance);
            BalanceTopOffResult result1 = manager.topOffBalance(value);
            handleTopOffResult(result1);
          } catch (Exception ignored) {
          }
        }
      }
    });
  }

  private void handleTopOffResult(BalanceTopOffResult result) {
    if(result == null)
      return;
    AlertBox alertBox = switch (result) {
      case SUCCESS -> new AlertBox("Success",
          "Balance has been added");
      case UNEXPECTED_ERROR -> new AlertBox("Error",
          "Unexpected Error");
      case USER_DOES_NOT_EXIST -> new AlertBox("Error",
          "This user does not exist");
    };
    alertBox.show();
  }

  /**
   * this method updates the view of this pane by placing the right username
   * and balance of the user
   */
  private void update() {
    usernameLabel.setText(manager.getUsername());
    balanceLabel.setText(String.format("%.2f €", manager.getBalance()));
  }

}
