package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.ui.gui.AlertBox;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;

/**
 *Order Meal Pane ->
 *A class that is capable of implement every input in order a meal functionality,
 *It Creates, show and control the users inputs
 *@version 1.0
*/
public class OrderMealPane extends BorderPane {
  private final ModelManager manager;
  private DatePicker datePicker;
  private ChoiceBox<String> periodChoiceBox;
  private Label totalPriceLabel;
  private Button btnBuy;


  /**
   * Constructor for class OrderMealPane that receives and save the modelManager
   * Create views and all the aesthetic
   * Update the Root panel visibility
   * @param manager
   */
  public OrderMealPane(ModelManager manager) {
    this.manager = manager;
    createViews();   //Initialize View
    registerHandlers(); //Control View
    update();        //Update visibility
  }


  /**
    *View with every details to be possible order a meal
    *with day, meal period, meal elements and timeSlots
    */
  private void createViews() {
    Label title = new Label("Order Meal");
    title.setFont(Font.font(50));
    datePicker = new DatePicker();
    datePicker.setPrefWidth(150);
    periodChoiceBox = new ChoiceBox<>();
    periodChoiceBox.setPrefWidth(300);
    periodChoiceBox.getItems().addAll("Lunch", "Dinner");
    HBox topHBox = new HBox(new Label("Select Day: "), datePicker,
        new Label("Select Period: "), periodChoiceBox);
    topHBox.setSpacing(50);
    topHBox.setAlignment(Pos.CENTER);
    VBox topVBox = new VBox(title, topHBox);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);

    String[] st = { "Arroz Branco - 0.5€", "Feijão Preto - 0.7€",
        "Entremeada - 0.8€" };

    // Create and show every meal items for that specific day and meal period
    VBox leftVBox = new VBox(new Label("Choose your meal Items"));
    for (String s : st) {
      CheckBox c = new CheckBox(s);
      leftVBox.getChildren().add(c);
      c.setIndeterminate(true);
    }
    leftVBox.setAlignment(Pos.CENTER);

    //Create and show available time slots for that specific meal period
    ToggleGroup toggleGroup = new ToggleGroup();
    RadioButton r1 = new RadioButton("12:00 - 12:15");
    r1.setToggleGroup(toggleGroup);
    RadioButton r2 = new RadioButton("12:15 - 12:30");
    r2.setToggleGroup(toggleGroup);
    RadioButton r3 = new RadioButton("12:30 - 12:45");
    r3.setToggleGroup(toggleGroup);

    VBox centerVBox = new VBox(new Label("Pick a timeslot"), r1, r2, r3); //Pick time Slot label
    centerVBox.setAlignment(Pos.CENTER); //Center the timeSlot label in Vbox

    //Create and show VBox with total Price for the specific choosed meal
    totalPriceLabel = new Label("Total Price: "); //Create totalPriceLabel
    btnBuy = new Button("Buy"); //Create the buy Button
    VBox rightVBox = new VBox(totalPriceLabel, btnBuy); //Insert into Vbox the total price label with a buy button
    rightVBox.setAlignment(Pos.CENTER); //Align items to the center of the rightVBox

    //Set dimensions of meal items, timeSlot and buy box
    leftVBox.setPrefWidth(300);
    centerVBox.setPrefWidth(300);
    rightVBox.setPrefWidth(300);
    HBox centerHBox = new HBox(leftVBox, centerVBox, rightVBox);
    centerHBox.setAlignment(Pos.CENTER);
    centerHBox.setSpacing(30);
    this.setCenter(centerHBox);
  }

  /**
   *registerHanlers control all the information when user clicks in buy button
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    btnBuy.setOnAction(actionEvent -> {
      AlertBox alertBox = new AlertBox("Success" , "You can found your tickets " +
          "under 'My Tickets'");
      alertBox.show();

    });

  }

  /**
    *Function capble of change the Visibility of OrderMealPane
    */
  private void update() {
    this.setVisible(manager.getState() == State.MEAL_ORDERING);
  }
}
