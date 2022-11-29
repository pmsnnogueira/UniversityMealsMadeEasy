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

public class MyTicketsPane extends BorderPane {
  private final ModelManager manager;

  ListView<String> ticketListView, mealItemsListView;
  Button btnRefund;
  Label mealPriceLabel;


  public MyTicketsPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

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

  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

  }

  private void update() {
    this.setVisible(manager.getState() == State.TICKETS_CONSULTATION);
    mealItemsListView.getItems().clear();
    ticketListView.getItems().clear();
  }
}
