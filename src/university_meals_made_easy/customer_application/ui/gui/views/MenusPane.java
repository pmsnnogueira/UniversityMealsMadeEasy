package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.Logger;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;

import java.time.LocalDate;
import java.util.List;

public class MenusPane extends BorderPane {
  private final ModelManager manager;
  private ChoiceBox<String> periodChoiceBox;
  private Button btnPrevious, btnNext;
  private ListView<FoodItem> mealElementsListView;
  private Label lbDatetime;
  private LocalDate localDate;


  /**
   * Constructor for class MenusPane that receives and save the modelManager,
   * Create views and all the aesthetic,
   * Controls the user actions,
   * Update the Root panel visibility
   * @param manager
   */
  public MenusPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }


  /**
   * The createViews method is capable of create all the content for the weekly menus like
   *   Vbox,
   *   Lists,
   *   Buttons,
   *   Labels,
   *   And all the aesthetic,
   */
  private void createViews() {
    Label title = new Label("Daily Menu");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);
    localDate = LocalDate.now();
    lbDatetime = new Label();
    Label selectPeriodLabel = new Label("Period: ");
    periodChoiceBox = new ChoiceBox<>();
    periodChoiceBox.getItems().addAll("Lunch", "Dinner");
    periodChoiceBox.getSelectionModel().selectFirst();
    btnPrevious = new Button("Previous");
    btnNext = new Button("Next");
    mealElementsListView = new ListView<>();
    mealElementsListView.setPrefHeight(700);
    HBox dayHBox = new HBox(btnPrevious, lbDatetime, btnNext);
    dayHBox.setAlignment(Pos.CENTER);
    dayHBox.setSpacing(100);
    VBox centerVBox = new VBox(selectPeriodLabel, periodChoiceBox, dayHBox, mealElementsListView);
    centerVBox.setPadding(new Insets(100));
    centerVBox.setAlignment(Pos.CENTER);
    this.setCenter(centerVBox);
  }


  /**
   * The registerHandlers method can control the users actions,
   * and can update the content of the Vbox with menus for a
   * specific day and meal period
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    btnNext.setOnAction(actionEvent -> {
      localDate = localDate.plusDays(1);
      update();
    });
    btnPrevious.setOnAction(actionEvent -> {
      localDate = localDate.minusDays(1);
      update();
    });

    periodChoiceBox.setOnAction(actionEvent -> update());

  }

  /**
   * The update method can update the content of the
   * weekly menus for a specific day and meal period
   */
  private void update() {
    this.setVisible(manager.getState() == State.MENU_CONSULTATION);
    mealElementsListView.getItems().clear();
    lbDatetime.setText(localDate.getDayOfWeek() + " " + localDate.format(Logger.dateFormatter));
    btnPrevious.setDisable(localDate.isEqual(LocalDate.now()));
    MealPeriod selectedMealPeriod = switch(periodChoiceBox.getValue()) {
      case "Lunch" -> MealPeriod.LUNCH;
      default -> MealPeriod.DINNER;
    };
    if(selectedMealPeriod == null)
      return;
    if(localDate == null)
      return;
    Meal selectedMeal = manager.getMeal(localDate, selectedMealPeriod);
    if(selectedMeal == null)
      return;
    List<FoodItem> foodItems = manager.getFoodItems(selectedMeal);
    if(foodItems == null)
      return;
    mealElementsListView.getItems().addAll(foodItems);
  }
}
