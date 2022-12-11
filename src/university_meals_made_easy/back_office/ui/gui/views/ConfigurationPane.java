package university_meals_made_easy.back_office.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.data.result.TimeSlotCapacityConfiguringResult;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.back_office.ui.gui.AlertBox;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;

import java.time.LocalDate;
import java.util.List;

/**
 * This view is a border pane that contains information about the capacity of
 * a certain timeslot. It is also possible to change it
 */
public class ConfigurationPane extends BorderPane {
  private final ModelManager manager;

  private DatePicker datePicker;
  private ChoiceBox<String> periodChoiceBox;
  private ListView<TimeSlot> timeslotListView;
  private Button btnConfirm;
  private TextField capacityTextField;

  private TimeSlot selectedTimeSlot;


  /**
   * Constructor for Configuration Pane
   * @param manager ModelManager instance
   */
  public ConfigurationPane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  /**
   * This method is called once this object is created, it will configure every
   * aspect of the gui, where all buttons, text field and other elements appear.
   * In this case a DatePicker, a ChoiceBox, a ListView, a TextField and a
   * Button will be shown on the screen
   */
  private void createViews() {
    Label title = new Label("Configuration");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);

    Label chooseDayLabel = new Label("Choose day");
    datePicker = new DatePicker(LocalDate.now());
    datePicker.setPrefWidth(300);
    Label choosePeriodLabel = new Label("Choose Period");
    periodChoiceBox = new ChoiceBox<>();
    periodChoiceBox.getItems().addAll("Lunch", "Dinner");
    periodChoiceBox.getSelectionModel().selectFirst();
    periodChoiceBox.setPrefWidth(200);
    Label timeslotLabel = new Label("Timeslot");
    timeslotListView = new ListView<>();
    timeslotListView.setPrefHeight(700);
    timeslotListView.setPrefWidth(200);
    VBox leftVBox = new VBox(chooseDayLabel, datePicker, choosePeriodLabel,
        periodChoiceBox, timeslotLabel, timeslotListView);
    leftVBox.setAlignment(Pos.CENTER);
    leftVBox.setPrefWidth(500);

    Label timeslotCapacityLabel = new Label("Timeslot Capacity");
    capacityTextField = new TextField("30");
    capacityTextField.setPrefWidth(300);
    btnConfirm = new Button("Confirm");
    btnConfirm.setPrefWidth(100);
    btnConfirm.setPrefHeight(50);
    VBox rightVBox = new VBox(timeslotCapacityLabel, capacityTextField,
        btnConfirm);
    rightVBox.setAlignment(Pos.CENTER);
    rightVBox.setPrefHeight(800);

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

    capacityTextField.textProperty().addListener(
        (observable, oldValue, newValue) -> {
          if (!newValue.matches("\\d*")) {
            capacityTextField.setText(newValue.replaceAll("\\D", ""));
          }
        });

    datePicker.setOnAction(actionEvent -> listTimeSlots());

    periodChoiceBox.setOnAction(actionEvent -> listTimeSlots());

    timeslotListView.getSelectionModel().selectedItemProperty().addListener(
        (a, b,c) -> {
          selectedTimeSlot = timeslotListView.getSelectionModel().getSelectedItem();
          if(selectedTimeSlot == null)
            return;
          capacityTextField.setText(selectedTimeSlot.getCapacity()+"");
    });

    btnConfirm.setOnAction(actionEvent -> {
      String capacity = capacityTextField.getText();
      if(capacity == null || capacity.isEmpty() || capacity.isBlank())
        return;
      if(selectedTimeSlot == null)
        return;
      TimeSlotCapacityConfiguringResult result = manager.configureCapacity(
          selectedTimeSlot, Integer.parseInt(capacity));
      AlertBox alertBox = switch (result) {
        case SUCCESS -> new AlertBox("Success",
            "New Meals has been inserted");
        case UNEXPECTED_ERROR -> new AlertBox("Error",
            "Unexpected Error");
        case ALREADY_TOO_MANY_BOUGHT_TICKETS -> new AlertBox("Error",
            "A lot of tickets have been bought already");
      };
      alertBox.show();
    });
  }

  private void listTimeSlots() {
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
    System.out.println(timeSlots);
    if(timeSlots == null)
      return;
    timeslotListView.getItems().addAll(timeSlots);
  }

  /**
   * this method is responsible to update the entire view everytime it
   * needs after a change
   */
  private void update() {
    this.setVisible(manager.getState() == State.CONFIGURATION);
  }
}
