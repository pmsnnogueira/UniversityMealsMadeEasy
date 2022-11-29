package university_meals_made_easy.customer_application.ui.gui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;



public class HistoryPane extends BorderPane {
  private final ModelManager manager;

  ListView<String> transactionHistoryListView;

  public HistoryPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

  private void createViews() {
    Label title = new Label("Transaction History");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);

    transactionHistoryListView = new ListView<>();
    transactionHistoryListView.setPrefHeight(700);
    VBox centerVBox = new VBox(transactionHistoryListView);
    centerVBox.setPadding(new Insets(100));
    centerVBox.setAlignment(Pos.CENTER);
    this.setCenter(centerVBox);
  }

  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

  }

  private void update() {
    this.setVisible(manager.getState() == State.TRANSACTION_HISTORY);
    transactionHistoryListView.getItems().clear();
    transactionHistoryListView.getItems().add("Empty List");
  }
}
