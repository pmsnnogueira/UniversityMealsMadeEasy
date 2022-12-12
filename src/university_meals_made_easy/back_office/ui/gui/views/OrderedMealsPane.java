package university_meals_made_easy.back_office.ui.gui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;

import java.time.LocalDate;
import java.util.List;

/**
 * This view is a border pane that contains information about the meals that
 * were bought by customers
 */
public class OrderedMealsPane extends BorderPane {
  private final ModelManager manager;
  private DatePicker datePicker;
  private ChoiceBox<String> periodChoiceBox;
  private ListView<TimeSlot> timeslotListView;
  private ScrollPane scrollPane;

  /**
   * Constructor for OrderedMealsPane
   * @param manager ModelManager instance
   */
  public OrderedMealsPane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  /**
   * This method is called once this object is created, it will configure every
   * aspect of the gui, where all buttons, text field and other elements appear.
   * In this case a DatePicker, a ChoiceBox and a ListView
   */
  private void createViews() {
    Label title = new Label("Ordered Meals");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);
    Label chooseDayLabel = new Label("Choose day");
    datePicker = new DatePicker(LocalDate.now());
    Label choosePerioLabel = new Label("Choose Period");
    periodChoiceBox = new ChoiceBox<>();
    periodChoiceBox.getItems().addAll("Lunch", "Dinner");
    periodChoiceBox.getSelectionModel().selectFirst();
    periodChoiceBox.setPrefWidth(200);
    Label timeslotLabel = new Label("Timeslot");
    timeslotListView = new ListView<>();
    timeslotListView.setPrefHeight(700);
    timeslotListView.setPrefWidth(200);
    VBox leftVBox = new VBox(chooseDayLabel, datePicker, choosePerioLabel,
        periodChoiceBox, timeslotLabel, timeslotListView);
    leftVBox.setAlignment(Pos.CENTER);
    leftVBox.setPrefWidth(500);
    Label boughtMealsLabel = new Label("Bought Meals");
    scrollPane = new ScrollPane();
    scrollPane.setPrefHeight(800);
    scrollPane.setPrefWidth(700);
    VBox rightVBox = new VBox(boughtMealsLabel, scrollPane);
    rightVBox.setAlignment(Pos.CENTER);
    rightVBox.setPrefWidth(800);

    HBox centerHBox = new HBox(leftVBox, rightVBox);
    centerHBox.setAlignment(Pos.CENTER);
    centerHBox.setSpacing(30);
    this.setCenter(centerHBox);

  }

  /**
   * this method is called after creating the view.
   * It's responsible to register all handlers and listeners
   * so the elements can be used.
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

    datePicker.setOnAction(actionEvent -> listTimeSlots());

    periodChoiceBox.setOnAction(actionEvent -> listTimeSlots());

    timeslotListView.getSelectionModel().selectedItemProperty().addListener(
        (a, b,c) -> {
          TimeSlot selectedTimeSlot = timeslotListView.getSelectionModel()
              .getSelectedItem();
          if(selectedTimeSlot == null)
            return;
          List<List<FoodItem>> foodItems = manager.getOrderedMealsFoodItems(
              selectedTimeSlot);
          VBox foodItemsContainer = new VBox();
          foodItemsContainer.setAlignment(Pos.CENTER);
          foodItemsContainer.setSpacing(20);
          for(List<FoodItem> list : foodItems) {
            VBox container = new VBox();
            container.setAlignment(Pos.CENTER);
            for(FoodItem foodItem : list) {
              Label name = new Label(foodItem.getDescription());
              Label price = new Label(foodItem.getPrice()+ "");
              HBox foodItemHBox = new HBox(name, price);
              foodItemHBox.setPrefWidth(400);
              foodItemHBox.setPrefHeight(100);
              foodItemHBox.setBackground(new Background(new BackgroundFill(
                  Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
              foodItemHBox.setAlignment(Pos.CENTER);
              foodItemHBox.setSpacing(5);
              container.getChildren().add(foodItemHBox);
            }
            foodItemsContainer.getChildren().add(container);
          }
          scrollPane.setContent(foodItemsContainer);
        });
  }

  /**
   * This method gets the meal from a chosen day and a period a lists all
   * timeslots on the view
   */
  private void listTimeSlots() {
    scrollPane.setContent(null);
    timeslotListView.getItems().clear();
    LocalDate date = datePicker.getValue();
    if(date == null)
      return;
    MealPeriod period = switch(periodChoiceBox.getValue()) {
      case "Lunch" -> MealPeriod.LUNCH;
      default -> MealPeriod.DINNER;
    };
    Meal selectedMeal = manager.getMeal(date, period);
    if(selectedMeal == null)
      return;
    List<TimeSlot> timeSlots = manager.getTimeSlots(selectedMeal);
    if(timeSlots == null)
      return;
    timeslotListView.getItems().addAll(timeSlots);
  }
  /**
   * this method is responsible to update the entire view everytime it
   * needs after a change
   */
  private void update() {
    scrollPane.setContent(null);
    this.setVisible(manager.getState() == State.ORDERED_MEALS_CONSULTATION);
    listTimeSlots();
  }
}
