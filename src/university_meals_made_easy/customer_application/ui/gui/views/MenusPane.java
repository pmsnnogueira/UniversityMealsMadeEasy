package university_meals_made_easy.customer_application.ui.gui.views;

import com.sun.media.jfxmedia.events.NewFrameEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;

import java.time.LocalDateTime;

/**
 * The MenusPane class is called when the user clicks to watch the weekly menu
 * @version 1.0
 */
public class MenusPane extends BorderPane {
  private final ModelManager manager;
  ChoiceBox<String> periodChoiceBox;
  Button btnPrevious, btnNext;
  ListView<String> mealElementsListView;
  Label lbDatetime;

  /**
   * Constructor for class MenusPane that receives and save the modelManager,
   * Create views and all the aesthetic,
   * Controls the user actions,
   * Update the Root panel visibility
   * @param manager
   */
  public MenusPane(ModelManager manager) {
    this.manager = manager;
    createViews();
    registerHandlers();
    update();
  }


  /**
   * The createViews method is capable of create all the content for the weekly menus like
   *   Vbox,
   *   Lists,
   *   Buttons,
   *   Labels,
   *   And all the aesthetic,
   */
  private void createViews() {
    Label title = new Label("Daily Menu");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);
    lbDatetime = new Label("Current day (28/11/2022)");
    Label selectPeriodLabel = new Label("Period: ");
    periodChoiceBox = new ChoiceBox<>();
    periodChoiceBox.getItems().addAll("Lunch", "Dinner");
    btnPrevious = new Button("Previous");
    btnNext = new Button("Next");
    mealElementsListView = new ListView<>();
    mealElementsListView.setPrefHeight(700);
    HBox dayHBox = new HBox(btnPrevious, lbDatetime, btnNext);
    dayHBox.setAlignment(Pos.CENTER);
    dayHBox.setSpacing(100);
    VBox centerVBox = new VBox(selectPeriodLabel, periodChoiceBox, dayHBox, mealElementsListView);
    centerVBox.setPadding(new Insets(100));
    centerVBox.setAlignment(Pos.CENTER);
    this.setCenter(centerVBox);
  }


  /**
   * The registerHandlers method can control the users actions,
   * and can update the content of the Vbox with menus for a
   * specific day and meal period
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

  }

  /**
   * The update method can update the content of the
   * weekly menus for a specific day and meal period
   */
  private void update() {
    this.setVisible(manager.getState() == State.MENU_CONSULTATION);
    mealElementsListView.getItems().clear();
    mealElementsListView.getItems().add("List is empty");
  }
}
