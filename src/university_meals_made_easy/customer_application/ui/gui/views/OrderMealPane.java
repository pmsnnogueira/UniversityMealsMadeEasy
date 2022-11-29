package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;

public class OrderMealPane extends BorderPane {
  private final ModelManager manager;
  ChoiceBox<String> dayChoiceBox, periodChoiceBox;
  Label totalPriceLabel;
  Button btnBuy;

  public OrderMealPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

  private void createViews() {
    Label title = new Label("Order Meal");
    title.setFont(Font.font(50));
    dayChoiceBox = new ChoiceBox<>();
    dayChoiceBox.setPrefWidth(300);
    periodChoiceBox = new ChoiceBox<>();
    periodChoiceBox.setPrefWidth(300);
    periodChoiceBox.getItems().addAll("Lunch", "Dinner");
    HBox topHBox = new HBox(new Label("Select Day: "), dayChoiceBox, new Label("Select Period: "), periodChoiceBox);
    topHBox.setSpacing(50);
    topHBox.setAlignment(Pos.CENTER);
    VBox topVBox = new VBox(title, topHBox);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);

    String[] st = { "Arroz Branco - 0.5€", "Feijão Preto - 0.7€", "Entremeada - 0.8€" };

    VBox leftVBox = new VBox(new Label("Choose your meal Items"));
    for (String s : st) {
      CheckBox c = new CheckBox(s);
      leftVBox.getChildren().add(c);
      c.setIndeterminate(true);
    }
    leftVBox.setAlignment(Pos.CENTER);


    ToggleGroup toggleGroup = new ToggleGroup();
    RadioButton r1 = new RadioButton("12:00 - 12:15");
    r1.setToggleGroup(toggleGroup);
    RadioButton r2 = new RadioButton("12:15 - 12:30");
    r2.setToggleGroup(toggleGroup);
    RadioButton r3 = new RadioButton("12:30 - 12:45");
    r3.setToggleGroup(toggleGroup);

    VBox centerVBox = new VBox(new Label("Pick a timeslot"), r1, r2, r3);
    centerVBox.setAlignment(Pos.CENTER);

    totalPriceLabel = new Label("Total Price: ");
    btnBuy = new Button("Buy");
    VBox rightVBox = new VBox(totalPriceLabel, btnBuy);
    rightVBox.setAlignment(Pos.CENTER);

    leftVBox.setPrefWidth(300);
    centerVBox.setPrefWidth(300);
    rightVBox.setPrefWidth(300);
    HBox centerHBox = new HBox(leftVBox, centerVBox, rightVBox);
    centerHBox.setAlignment(Pos.CENTER);
    centerHBox.setSpacing(30);
    this.setCenter(centerHBox);
  }

  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

  }

  private void update() {
    this.setVisible(manager.getState() == State.MEAL_ORDERING);
  }
}
