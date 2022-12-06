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


/**
 * The HistoryPane class is called when the user clicks to watch the transaction history.
 * @version 1.0
 */
public class HistoryPane extends BorderPane {
  private final ModelManager manager;

  ListView<String> transactionHistoryListView;


  /**
   * Constructor for class HistoryPane that receives and save the modelManager,
   * Create views and all the aesthetic,
   * Controls the user actions,
   * Update the Root panel visibility
   * @param manager
   */
  public HistoryPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }

  /**
   * The createViews method is capable of create all the content for the Review meal view like
   *   Vbox,
   *   Lists,
   *   Buttons,
   *   Labels,
   *   And all the aesthetic,
   */
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


  /**
   * The registerHandlers method can control the users actions,
   * and can update the content of the Vbox with all the transactions history's
   * from the specific user
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
  }

  /**
   * The update method can update the content of the
   * transaction history list,
   * And change the visibility
   */
  private void update() {
    this.setVisible(manager.getState() == State.TRANSACTION_HISTORY);
    transactionHistoryListView.getItems().clear();
    transactionHistoryListView.getItems().add("Empty List");
  }
}
