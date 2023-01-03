package university_meals_made_easy.customer_application.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import university_meals_made_easy.customer_application.model.ModelManager;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.customer_application.ui.gui.views.*;

/**
 * The RootPane Class creates, show and redirect the user choices to the specific menu.,
 * It contains a lot of things like
 *    A navbar with every feature for the BackOffice users
 *    Controls the user choices and redirect to the specific panel
 *    Can change the visibility
 * @version 1.0
 */
public class RootPane extends BorderPane {
  private final ModelManager manager;

  private VBox leftButtonsVBox;
  private ToggleButton btnMainMenu, btnOrderMeal, btnMenus, btnMyTickets;
  private ToggleButton btnReviewMeal, btnHistory, btnLogout;
  private ToggleGroup tgTabButtons;
  private BalancePane balancePane;

  /**
   * Constructor for class RootPane that receives and save the modelManager,
   * Create views and all the aesthetic,
   * Controls the user actions,
   * Update the Root panel visibility
   * @param manager
   */
  public RootPane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  /**
   * Method that is capable of create all the content for the navBar like
   *   Groups,
   *   Vbox,
   *   Buttons,
   *   Labels,
   *   And all the aesthetic,
   */
  private void createViews() {
    tgTabButtons = new ToggleGroup();
    Label title1 = new Label("University Meals");
    title1.setFont(Font.font(20));
    title1.setPadding(new Insets(20, 0, 20, 0));
    Label title2 = new Label("Made Easy");
    title2.setFont(Font.font(20));
    title2.setPadding(new Insets(0, 0, 150, 0));

    balancePane = new BalancePane(manager);
    this.setRight(balancePane);

    btnMainMenu = new ToggleButton("Main Menu");
    btnMainMenu.setToggleGroup(tgTabButtons);
    btnMainMenu.setPrefWidth(250);
    btnMainMenu.setPrefHeight(100);
    btnMainMenu.setSelected(true);

    btnOrderMeal = new ToggleButton("Order Meal");
    btnOrderMeal.setToggleGroup(tgTabButtons);
    btnOrderMeal.setPrefWidth(250);
    btnOrderMeal.setPrefHeight(100);

    btnMenus = new ToggleButton("Menus");
    btnMenus.setToggleGroup(tgTabButtons);
    btnMenus.setPrefWidth(250);
    btnMenus.setPrefHeight(100);

    btnMyTickets = new ToggleButton("My Tickets");
    btnMyTickets.setToggleGroup(tgTabButtons);
    btnMyTickets.setPrefWidth(250);
    btnMyTickets.setPrefHeight(100);

    btnReviewMeal = new ToggleButton("Review Meal");
    btnReviewMeal.setToggleGroup(tgTabButtons);
    btnReviewMeal.setPrefWidth(250);
    btnReviewMeal.setPrefHeight(100);

    btnHistory = new ToggleButton("History");
    btnHistory.setToggleGroup(tgTabButtons);
    btnHistory.setPrefWidth(250);
    btnHistory.setPrefHeight(100);

    btnLogout = new ToggleButton("Logout");
    btnLogout.setToggleGroup(tgTabButtons);
    btnLogout.setPrefWidth(250);
    btnLogout.setPrefHeight(100);

    leftButtonsVBox = new VBox(title1, title2, btnMainMenu, btnOrderMeal,
        btnMenus, btnMyTickets, btnReviewMeal, btnHistory, btnLogout);
    leftButtonsVBox.setMinWidth(250);
    leftButtonsVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

    this.setLeft(leftButtonsVBox);

    this.setCenter(new StackPane(new AuthenticationPane(manager), new MainMenuPane(manager),
        new OrderMealPane(manager), new MenusPane(manager),
        new MyTicketsPane(manager), new ReviewMealPane(manager),
        new HistoryPane(manager)));
  }


  /**
   * The registerHandlers method can control the users actions,
   * and can redirect the user to the specific operation
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

    btnMainMenu.setOnAction(actionEvent -> {
      manager.changeToMainMenu();
    });
    btnOrderMeal.setOnAction(actionEvent -> {
      manager.changeToMealOrdering();
    });
    btnMenus.setOnAction(actionEvent -> {
      manager.changeToMenuConsultation();
    });
    btnMyTickets.setOnAction(actionEvent -> {
      manager.changeToTicketsConsultation();
    });
    btnReviewMeal.setOnAction(actionEvent -> {
      manager.changeToReviewal();
    });
    btnHistory.setOnAction(actionEvent -> {
      manager.changeToTransactionHistory();
    });
    btnLogout.setOnAction(actionEvent -> {
      manager.logout();
    });
  }

  /**
   * The update method can control the RootPane visibility,
   * and can change if the navbar is visible
   */
  private void update() {
    if(manager.getState() == State.AUTHENTICATION) {
      leftButtonsVBox.setVisible(false);
      balancePane.setVisible(false);
    } else {
      leftButtonsVBox.setVisible(true);
      balancePane.setVisible(true);
    }
  }
}
