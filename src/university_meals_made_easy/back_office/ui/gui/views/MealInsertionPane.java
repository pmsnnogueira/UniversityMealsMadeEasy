package university_meals_made_easy.back_office.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.data.result.MealFoodItemInsertionResult;
import university_meals_made_easy.back_office.model.data.result.MealFoodItemsClearingResult;
import university_meals_made_easy.back_office.model.data.result.MealInsertionResult;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.back_office.ui.gui.AlertBox;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This view is a border pane that creates a view where it is possible to
 * insert food items to a meal.
 */
public class MealInsertionPane extends BorderPane {
  private final ModelManager manager;
  private DatePicker datePicker;
  private ChoiceBox<String> periodChoiceBox;
  private TextField mealItemName, mealItemPrice;
  private ListView<FoodItem> currentMealItemsListView;
  private Button btnInsert, btnReset;

  private List<FoodItem> foodItems;
  private Meal selectedMeal;

  /**
   * Constructor for MealInsertionPane
   * @param manager ModelManager instance
   */
  public MealInsertionPane(ModelManager manager) {
    this.manager = manager;
    foodItems = new ArrayList<>();
    createViews();
    registerHandlers();
    update();
  }

  /**
   * This method is called once this object is created, it will configure every
   * aspect of the gui, where all buttons, text field and other elements appear.
   * In this case a DatePicker, a ChoiceBox, 2 TextFields, a  ListView and 2
   * Buttons
   */
  private void createViews() {

    Label title = new Label("Meal Insertion");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);

    Label chooseDay = new Label("Choose Day");
    datePicker = new DatePicker(LocalDate.now());
    Label choosePeriodLabel = new Label("Select period");
    periodChoiceBox = new ChoiceBox<>();
    periodChoiceBox.getItems().addAll("Lunch", "Dinner");
    periodChoiceBox.getSelectionModel().selectFirst();
    Label itemNameLabel = new Label("Name");
    mealItemName = new TextField();
    mealItemName.setPrefWidth(300);
    mealItemName.setPromptText("name");
    Label itemPriceLabel = new Label("Price");
    mealItemPrice = new TextField();
    mealItemPrice.setPrefWidth(300);
    mealItemPrice.setPromptText("0.5");
    btnInsert = new Button("Insert");
    VBox leftVBox = new VBox(chooseDay, datePicker, choosePeriodLabel,
        periodChoiceBox, itemNameLabel, mealItemName, itemPriceLabel,
        mealItemPrice, btnInsert);
    leftVBox.setPrefHeight(800);
    leftVBox.setAlignment(Pos.CENTER);

    Label currentMealItemsLabel = new Label("Current Meal Items");
    currentMealItemsListView = new ListView<>();
    currentMealItemsListView.setPrefHeight(700);
    currentMealItemsListView.setPrefWidth(500);
    currentMealItemsListView.setPlaceholder(new Label(
        "This meal has no items"
    ));
    btnReset = new Button("Reset");

    VBox rightVBox = new VBox(currentMealItemsLabel, currentMealItemsListView,
        btnReset);
    rightVBox.setAlignment(Pos.CENTER);
    rightVBox.setPrefHeight(800);

    HBox centerHBox = new HBox(leftVBox, rightVBox);
    centerHBox.setSpacing(30);
    centerHBox.setAlignment(Pos.CENTER);
    this.setCenter(centerHBox);
  }

  /**
   * this method is called after creating the view.
   * It's responsible to register all handlers and listeners
   * so the elements can be used.
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    datePicker.setOnAction( actionEvent -> listCurrentFoodItems());

    periodChoiceBox.setOnAction(actionEvent -> listCurrentFoodItems());

    btnInsert.setOnAction(actionEvent -> {
      String name = mealItemName.getText();
      String price = mealItemPrice.getText();
      if(selectedMeal == null)
        return;
      if(price == null || name == null)
        return;
      if(price.isEmpty() || name.isEmpty())
        return;
      MealFoodItemInsertionResult result =  manager.insertFoodItem(
          selectedMeal, Float.parseFloat(price), name);
      AlertBox alertBox = switch (result) {
        case SUCCESS -> new AlertBox("Success",
            "New item has been inserted");
        case UNEXPECTED_ERROR -> new AlertBox("Error",
            "Unexpected Error");
        case DUPLICATE_FOOD_ITEM -> new AlertBox("Error",
            "This item already exists");
      };
      alertBox.show();
      listCurrentFoodItems();
    });

    btnReset.setOnAction(actionEvent -> {
      MealFoodItemsClearingResult result = manager.clearFoodItems(selectedMeal);
      AlertBox alertBox = switch(result) {
        case SUCCESS -> new AlertBox("Success",
            "All items were cleared");
        case UNEXPECTED_ERROR -> new AlertBox("Error",
            "Unexpected Error");
        case NO_FOOD_ITEMS -> new AlertBox("Error",
            "This meal has no items already");
        case ALREADY_BOUGHT_TICKETS -> new AlertBox("Error",
            "Items have been bought already");
      };
      alertBox.show();
      listCurrentFoodItems();
    });

  }

  /**
   * function that takes the date picker value and selected period and fills
   * the listview with the right food items
   */
  private void listCurrentFoodItems() {
    currentMealItemsListView.getItems().clear();
    MealPeriod period = switch(periodChoiceBox.getValue()) {
      case "Lunch" -> MealPeriod.LUNCH;
      default -> MealPeriod.DINNER;
    };
    LocalDate date = datePicker.getValue();
    if( date == null)
      return;
    selectedMeal = manager.getMeal(date, period);
    if(selectedMeal == null) {
      MealInsertionResult result = manager.insertMeal(period, date);
      handleMealInsertionResult(result);
      selectedMeal = manager.getMeal(date, period);
      return;
    }
    foodItems = manager.getFoodItems(selectedMeal);
    if(foodItems == null)
      return;
    currentMealItemsListView.getItems().addAll(foodItems);
  }

  /**
   * This method handles the result of creating a new meal
   * It will create an alert box depending on the result
   * @param result MealInsertionResult
   */
  private void handleMealInsertionResult(MealInsertionResult result) {
    if(result == null)
      return;
    AlertBox alertBox = switch (result) {
      case SUCCESS -> new AlertBox("Success",
          "New meal has been created");
      case ALREADY_EXISTS -> new AlertBox("Error",
          "This meal already exists");
    };
    alertBox.show();
  }

  /**
   * this method is responsible to update the entire view everytime it
   * needs after a change
   */
  private void update() {
    this.setVisible(manager.getState() == State.MEAL_INSERTION);
    listCurrentFoodItems();
  }
}
