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
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;

/**
 * This class is capable of show the users tickets
 * @version 1.0
 */
public class MyTicketsPane extends BorderPane {
  private final ModelManager manager;

  private ListView<String> ticketListView, mealItemsListView;
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
    mealItemsListView = new ListView<>();
    mealItemsListView.setPrefHeight(700);
    mealItemsListView.setPrefWidth(600);
    mealPriceLabel = new Label("Meal Price: ");
    btnRefund = new Button("Refund");
    btnRefund.setPrefWidth(200);
    btnRefund.setPrefHeight(100);
    VBox rightVBox = new VBox(mealPriceLabel, btnRefund);
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


  /**
   * The registerHandlers method can control the users actions,
   * and can update the content of the Vbox with every meals from a specific day
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

  }

  /**
   * The update method can update the content with meals
   * for a specific day
   */
  private void update() {
    this.setVisible(manager.getState() == State.TICKETS_CONSULTATION);
    mealItemsListView.getItems().clear();
    ticketListView.getItems().clear();
  }
}
