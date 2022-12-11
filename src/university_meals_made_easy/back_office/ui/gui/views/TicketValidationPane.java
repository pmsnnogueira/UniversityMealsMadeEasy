package university_meals_made_easy.back_office.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.data.result.TicketValidationResult;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.back_office.ui.gui.AlertBox;


/**
 * This view is a border pane where it is possible to validate meal tickets
 * using the ticket id
*/
public class TicketValidationPane extends BorderPane {
  private final ModelManager manager;
  private TextField ticketIdTextField;
  private Button btnValidateTicket;

  /**
   * Constructor for TicketValidationPane
   * @param manager ModelManager instance
   */
  public TicketValidationPane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  /**
   * This method is called once this object is created, it will configure every
   * aspect of the gui, where all buttons, text field and other elements appear.
   * In this case a TextField and a Button
   */
  private void createViews() {
    Label title = new Label("Validate Ticket");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);

    ticketIdTextField = new TextField();
    ticketIdTextField.setPromptText("Ticket Id");
    ticketIdTextField.setPrefWidth(200);

    HBox hBox = new HBox(new Label("Ticket id: "), ticketIdTextField);
    btnValidateTicket = new Button("Validate");
    hBox.setAlignment(Pos.CENTER);
    VBox vBox = new VBox(hBox, btnValidateTicket);
    vBox.setSpacing(30);
    vBox.setAlignment(Pos.CENTER);
    this.setCenter(vBox);

  }

  /**
   * this method is called after creating the view.
   * It's responsible to register all handlers and listeners
   * so the elements can be used.
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    ticketIdTextField.textProperty().addListener(
        (observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        ticketIdTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });
    btnValidateTicket.setOnAction(actionEvent -> {
      String id = ticketIdTextField.getText();
      if(id == null || id.isEmpty() || id.isBlank())
        return;
      TicketValidationResult result = manager.validateTicket(
          Integer.parseInt(id));

      AlertBox alertBox = switch (result) {
        case SUCCESS ->  new AlertBox("Success",
            "Ticket has been validated");
        case UNEXPECTED_ERROR -> new AlertBox("Error",
            "Unexpected error");
        case TICKET_ALREADY_VALIDATED -> new AlertBox("Error",
            "Ticket already validated");
        case TICKET_DOES_NOT_EXIST -> new AlertBox("Error",
            "Ticket does not exist");
        case TICKET_ALREADY_REFUNDED -> new AlertBox("Error",
            "Ticket already refunded");
      };
      alertBox.show();
      ticketIdTextField.clear();
    });
  }

  /**
   * this method is responsible to update the entire view everytime it
   * needs after a change
   */
  private void update() {
    this.setVisible(manager.getState() == State.TICKET_VALIDATION);
    ticketIdTextField.clear();
  }
}
