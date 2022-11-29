package university_meals_made_easy.back_office.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.fsm.State;

public class OrderedMealsPane extends BorderPane {
  private final ModelManager manager;
  DatePicker datePicker;
  ChoiceBox<String> periodChoiceBox;
  ListView<String> timeslotListView;

  public OrderedMealsPane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  private void createViews() {
    Label title = new Label("Ordered Meals");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);
    Label chooseDayLabel = new Label("Choose day");
    datePicker = new DatePicker();
    Label choosePerioLabel = new Label("Choose Period");
    periodChoiceBox = new ChoiceBox<>();
    periodChoiceBox.getItems().addAll("Lunch", "Dinner");
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
    ScrollPane scrollPane = new ScrollPane();
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

  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
  }

  private void update() {

    this.setVisible(manager.getState() == State.ORDERED_MEALS_CONSULTATION);
  }
}
