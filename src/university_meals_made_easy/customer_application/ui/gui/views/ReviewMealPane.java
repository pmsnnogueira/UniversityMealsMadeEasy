package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.ui.gui.AlertBox;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.data.result.ReviewResult;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.transaction.Ticket;

import java.util.List;


/**
 * The ReviewMealPane class is called when the user clicks to rate a meal.
 * @version 1.0
 */
public class ReviewMealPane extends BorderPane {
  private final ModelManager manager;
  private ListView<Ticket> ticketListView;
  private ListView<FoodItem> foodItemsListView;
  private Button btnSubmitReview;
  private Label rateThisMealLabel;
  private TextArea observationsTextArea;
  private Slider rateSlider;

  /**
   * Constructor for class ReviewMealPane that receives and save the modelManager,
   * Create views and all the aesthetic,
   * Controls the user actions,
   * Update the Root panel visibility
   *
   * @param manager
   */
  public ReviewMealPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }


  /**
   * The createViews method is capable of create all the content for the Review meal view like
   * Groups,
   * Vbox,
   * Lists,
   * Buttons,
   * Labels,
   * And all the aesthetic,
   */
  private void createViews() {
    Label title = new Label("Review Meal");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);
    ticketListView = new ListView<>();
    ticketListView.setPrefHeight(700);
    ticketListView.setPrefWidth(600);
    foodItemsListView = new ListView<>();
    foodItemsListView.setPrefHeight(700);
    foodItemsListView.setPrefWidth(600);
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
    btnSubmitReview.setPrefWidth(300);
    btnSubmitReview.setPrefHeight(100);
    VBox rightVBox = new VBox(rateThisMealLabel, rateSlider, observationsTextArea, btnSubmitReview);
    rightVBox.setAlignment(Pos.CENTER);
    rightVBox.setPrefWidth(500);
    rightVBox.setSpacing(30);
    Label leftLabel = new Label("Select a ticket for details");
    VBox leftVBox = new VBox(leftLabel, ticketListView);
    leftVBox.setAlignment(Pos.CENTER);
    Label centerLabel = new Label("Meal details");
    VBox centerVBox = new VBox(centerLabel, foodItemsListView);
    centerVBox.setAlignment(Pos.CENTER);
    HBox centerHBox = new HBox(leftVBox, centerVBox, rightVBox);
    centerHBox.setAlignment(Pos.CENTER);
    centerHBox.setPadding(new Insets(100));
    centerHBox.setSpacing(30);
    this.setCenter(centerHBox);

  }

  /**
   * The registerHandlers method can control the users actions,
   * and can update the content of the Vbox with every meal review for a specific day
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    ticketListView.getSelectionModel().selectedItemProperty().
        addListener((a, b, c) -> {
          Ticket selectedTicket = ticketListView.getSelectionModel().getSelectedItem();
          foodItemsListView.getItems().clear();
          if (selectedTicket == null)
            return;
          List<FoodItem> foodItems = manager.getTicketItems(selectedTicket);
          if (foodItems == null)
            return;
          foodItemsListView.getItems().addAll(foodItems);
        });

    btnSubmitReview.setOnAction(actionEvent -> {
      Ticket selectedTicket = ticketListView.getSelectionModel().getSelectedItem();
      if (selectedTicket == null)
        return;
      int rate = (int) rateSlider.getValue();
      String observations = observationsTextArea.getText();
      if (observations == null)
        observations = "No observations";
      ReviewResult result = manager.review(selectedTicket, rate, observations);
      AlertBox alertBox = switch (result) {
        case SUCCESS -> new AlertBox("Success",
            "Ticket reviewed with success");
        case UNEXPECTED_ERROR -> new AlertBox("Error", "Unexpected error");
      };
      alertBox.show();
    });

  }


  /**
   * The update method can update the content of the
   * review meal for a specific day
   */
  private void update() {
    this.setVisible(manager.getState() == State.REVIEWAL);
    foodItemsListView.getItems().clear();
    ticketListView.getItems().clear();
    List<Ticket> tickets = manager.getTickets();
    if (tickets == null)
      return;
    ticketListView.getItems().addAll(tickets);
  }
}
