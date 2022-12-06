package university_meals_made_easy.back_office.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.fsm.State;

/**
 * This view is a border pane that creates a view where it is possible to
 * insert food items to a meal.
 */
public class MealInsertionPane extends BorderPane {
  private final ModelManager manager;
  private DatePicker datePicker;
  private ChoiceBox<String> periodChoiceBox;
  private TextField mealItemName, mealItemPrice;
  private ListView<String> currentMealItemsListView;
  private Button btnInsert, btnReset;

  /**
   * Constructor for MealInsertionPane
   * @param manager
   */
  public MealInsertionPane(ModelManager manager) {
    this.manager = manager;

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
    datePicker = new DatePicker();
    Label choosePeriodLabel = new Label("Select period");
    periodChoiceBox = new ChoiceBox<>();
    periodChoiceBox.getItems().addAll("Lunch", "Dinner");
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
  }

  /**
   * this method is responsible to update the entire view everytime it
   * needs after a change
   */
  private void update() {
    this.setVisible(manager.getState() == State.MEAL_INSERTION);
  }
}
