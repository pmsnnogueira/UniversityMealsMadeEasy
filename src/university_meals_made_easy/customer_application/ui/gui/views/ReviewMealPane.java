package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;


public class ReviewMealPane extends BorderPane {
  private final ModelManager manager;
  ListView<String> ticketListView, mealItemsListView;
  Button btnSubmitReview;
  Label rateThisMealLabel;
  TextArea observationsTextArea;
  Slider rateSlider;


  public ReviewMealPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

  private void createViews() {
    Label title = new Label("Review Meal");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);
    ticketListView = new ListView<>();
    ticketListView.setPrefHeight(700);
    ticketListView.setPrefWidth(600);
    mealItemsListView = new ListView<>();
    mealItemsListView.setPrefHeight(700);
    mealItemsListView.setPrefWidth(600);
    rateThisMealLabel = new Label("Rate this meal");
    rateSlider = new Slider();
    rateSlider.setMin(0);
    rateSlider.setMax(10);
    rateSlider.setValue(5);
    observationsTextArea = new TextArea();
    observationsTextArea.setPromptText("Observations...");
    observationsTextArea.setPrefHeight(200);
    observationsTextArea.setPrefWidth(200);
    btnSubmitReview = new Button("Reviews");
    btnSubmitReview.setPrefWidth(200);
    btnSubmitReview.setPrefHeight(100);
    VBox rightVBox = new VBox(rateThisMealLabel, rateSlider, observationsTextArea, btnSubmitReview);
    rightVBox.setAlignment(Pos.CENTER);
    Label leftLabel = new Label("Select a ticket for details");
    VBox leftVBox = new VBox(leftLabel, ticketListView);
    leftVBox.setAlignment(Pos.CENTER);
    Label centerLabel = new Label("Meal details");
    VBox centerVBox = new VBox(centerLabel, mealItemsListView);
    centerVBox.setAlignment(Pos.CENTER);
    HBox centerHBox = new HBox(leftVBox, centerVBox, rightVBox);
    centerHBox.setAlignment(Pos.CENTER);
    centerHBox.setPadding(new Insets(100));
    this.setCenter(centerHBox);

  }
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

  }

  private void update() {
    this.setVisible(manager.getState() == State.REVIEWAL);
    mealItemsListView.getItems().clear();
    ticketListView.getItems().clear();
    ticketListView.getItems().add("Empty List");
    mealItemsListView.getItems().add("Select meal on left first");
  }
}
