package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.ui.gui.AlertBox;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.data.result.RefundResult;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.transaction.Ticket;

import java.util.List;

/**
 * This class is capable of show the users tickets
 * @version 1.0
 */
public class MyTicketsPane extends BorderPane {
  private final ModelManager manager;

  private ListView<Ticket> ticketListView;
  private ListView<FoodItem> foodItemsListView;
  private Button btnRefund;
  private Label mealPriceLabel;

  /**
   * Constructor for class MyTicketsPane that receives and save the modelManager,
   * Create views and all the aesthetic,
   * Controls the user actions,
   * Update the Root panel visibility
   * @param manager
   */
  public MyTicketsPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

  /**
   * Method with every details to be possible show user meals
   * with day, meal period, meal elements.
   */
  private void createViews() {
    Label title = new Label("My Tickets");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);
    ticketListView = new ListView<>();
    ticketListView.setPrefHeight(700);
    ticketListView.setPrefWidth(600);
    ticketListView.setPlaceholder(new Label(
        "You don't have any tickets right now"));
    foodItemsListView = new ListView<>();
    foodItemsListView.setPrefHeight(700);
    foodItemsListView.setPrefWidth(600);
    foodItemsListView.setPlaceholder(new Label(
        "Select a ticket on the left"));
    mealPriceLabel = new Label("Meal Price: ");
    btnRefund = new Button("Refund");
    btnRefund.setPrefWidth(300);
    btnRefund.setPrefHeight(100);
    VBox rightVBox = new VBox(mealPriceLabel, btnRefund);
    rightVBox.setPrefWidth(500);
    rightVBox.setSpacing(30);
    rightVBox.setAlignment(Pos.CENTER);
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
   * and can update the content of the Vbox with every meals from a specific day
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

    ticketListView.getSelectionModel().selectedItemProperty().
        addListener((a,b,c)-> {
          Ticket selectedTicket = ticketListView.getSelectionModel().getSelectedItem();
          foodItemsListView.getItems().clear();
          if(selectedTicket == null)
            return;
          List<FoodItem> foodItems = manager.getTicketItems(selectedTicket);
          if(foodItems == null)
            return;
          foodItemsListView.getItems().addAll(foodItems);
          float totalPrice = 0;
          for(FoodItem foodItem : foodItems) {
            totalPrice += foodItem.getPrice();
          }
          mealPriceLabel.setText(String.format("Total price: %.2f €", totalPrice));
    });

    btnRefund.setOnAction(actionEvent -> {
      Ticket selectedTicket = ticketListView.getSelectionModel().getSelectedItem();
      if(selectedTicket == null)
        return;
      RefundResult result = manager.refund(selectedTicket);
      AlertBox alertBox = switch (result) {
        case SUCCESS -> new AlertBox("Success", "Ticket refunded");
        case TODAY -> new AlertBox("Error", "Cannot refund for the same day");
        case UNEXPECTED_ERROR -> new AlertBox("Error",
            "Unexpected error");
      };
      alertBox.show();
    });
  }

  /**
   * The update method can update the content with meals
   * for a specific day
   */
  private void update() {
    this.setVisible(manager.getState() == State.TICKETS_CONSULTATION);
    mealPriceLabel.setText("Total price: 0");
    foodItemsListView.getItems().clear();
    ticketListView.getItems().clear();
    List<Ticket> tickets = manager.getTickets();
    if(tickets == null)
      return;
    ticketListView.getItems().addAll(tickets);
  }
}
