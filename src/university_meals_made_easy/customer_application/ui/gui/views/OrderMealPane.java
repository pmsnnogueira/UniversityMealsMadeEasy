package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.ui.gui.AlertBox;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.data.result.BuyResult;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
  private VBox leftVBox;
  private VBox centerVBox;
  private Meal selectedMeal;
  private ToggleGroup toggleGroup;
  private List<FoodItem> selectedFoodItems;
  private TimeSlot selectedTimeSlot;



  /**
   * Constructor for class OrderMealPane that receives and save the modelManager
   * Create views and all the aesthetic
   * Update the Root panel visibility
   * @param manager
   */
  public OrderMealPane(ModelManager manager) {
    this.manager = manager;
    selectedFoodItems = new ArrayList<>();
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
    datePicker = new DatePicker(LocalDate.now());
    datePicker.setPrefWidth(150);
    periodChoiceBox = new ChoiceBox<>();
    periodChoiceBox.setPrefWidth(300);
    periodChoiceBox.getItems().addAll("Lunch", "Dinner");
    periodChoiceBox.getSelectionModel().selectFirst();
    HBox topHBox = new HBox(new Label("Select Day: "), datePicker,
        new Label("Select Period: "), periodChoiceBox);
    topHBox.setSpacing(50);
    topHBox.setAlignment(Pos.CENTER);
    VBox topVBox = new VBox(title, topHBox);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);

    // Create and show every meal items for that specific day and meal period
    leftVBox = new VBox(new Label("Choose your meal Items"));
    leftVBox.setAlignment(Pos.CENTER);

    //Create and show available time slots for that specific meal period
    toggleGroup = new ToggleGroup();

    centerVBox = new VBox(new Label("Pick a timeslot")); //Pick time Slot label
    centerVBox.setAlignment(Pos.CENTER); //Center the timeSlot label in Vbox

    //Create and show VBox with total Price for the specific choosed meal
    totalPriceLabel = new Label("Total Price: "); //Create totalPriceLabel
    totalPriceLabel.setFont(Font.font(15));
    btnBuy = new Button("Buy"); //Create the buy Button
    btnBuy.setPrefWidth(150);
    btnBuy.setPrefHeight(100);
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

    datePicker.setOnAction( actionEvent -> update());

    periodChoiceBox.setOnAction(actionEvent -> update());

    btnBuy.setOnAction(actionEvent -> {
      if(selectedTimeSlot == null || selectedFoodItems.isEmpty()) {
        AlertBox alertBox = new AlertBox("Warning",
            "Please select an available time slot or pick at least " +
                "one meal");
        alertBox.show();
        return;
      }
      BuyResult result = manager.buy(selectedTimeSlot, selectedFoodItems);
      AlertBox alertBox = switch (result) {
        case SUCCESS -> new AlertBox("Success",
            "Ticket bought with success");
        case UNEXPECTED_ERROR -> new AlertBox("Error",
            "Unexpected Error");
        case SLOT_ALREADY_FULL -> new AlertBox("Error",
            "Time slot is already full");
        case INSUFFICIENT_BALANCE -> new AlertBox("Error",
            "Insufficient balance");
      };
      alertBox.show();
    });
  }

  private void getSelectedMeal() {
    MealPeriod period = switch(periodChoiceBox.getValue()) {
      case "Lunch" -> MealPeriod.LUNCH;
      default -> MealPeriod.DINNER;
    };
    LocalDate date = datePicker.getValue();
    if( date == null)
      return;
    selectedMeal = manager.getMeal(date, period);
  }

  /**
    *Function capable of change the Visibility of OrderMealPane
    */
  private void update() {
    getSelectedMeal();
    centerVBox.getChildren().clear();
    leftVBox.getChildren().clear();
    selectedFoodItems.clear();
    updateTotalPrice();
    this.setVisible(manager.getState() == State.MEAL_ORDERING);
    leftVBox.getChildren().add(new Label("No food items available"));
    centerVBox.getChildren().add(new Label("No time slot available"));
    if(selectedMeal == null)
      return;
    centerVBox.getChildren().clear();
    leftVBox.getChildren().clear();
    List<FoodItem> foodItems = manager.getFoodItems(selectedMeal);
    if(foodItems == null)
      return;
    for (FoodItem foodItem : foodItems) {
      CheckBox c = new CheckBox(foodItem.toString());
      leftVBox.getChildren().add(c);
      c.setIndeterminate(true);
      c.setUserData(foodItem);
      c.setFont(Font.font(15));
      c.setOnAction(actionEvent -> {
        if(c.isSelected()) {
          selectedFoodItems.add((FoodItem) c.getUserData());
        } else {
          selectedFoodItems.remove((FoodItem) c.getUserData());
        }
        updateTotalPrice();
      });
    }
    List<TimeSlot> timeSlots = manager.getAvailableTimeSlots(selectedMeal);
    if(timeSlots == null)
      return;
    for(TimeSlot timeSlot : timeSlots) {
      RadioButton radioButton = new RadioButton(timeSlot.getTimeOfStartAsString() +
          " : " +timeSlot.getTimeOfEndAsString());
      radioButton.setToggleGroup(toggleGroup);
      radioButton.setUserData(timeSlot);
      radioButton.setFont(Font.font(15));
      centerVBox.getChildren().add(radioButton);
      radioButton.setOnAction(actionEvent -> {
        if(radioButton.isSelected()) {
          selectedTimeSlot = (TimeSlot) radioButton.getUserData();
        }
      });
    }
  }

  private void updateTotalPrice() {
    float totalPrice = 0;
    for(FoodItem foodItem :selectedFoodItems) {
      totalPrice += foodItem.getPrice();
    }
    totalPriceLabel.setText(String.format("Total Price: %.2f €", totalPrice));
  }
}
