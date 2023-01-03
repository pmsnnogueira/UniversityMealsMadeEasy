package university_meals_made_easy.back_office.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.ui.gui.views.*;

/**
 * This is the base window pane for the gui, everything on this gui is on
 * top of it
 */
public class RootPane extends BorderPane {
  private final ModelManager manager;

  private VBox leftButtonsVBox;
  private ToggleButton btnMainMenu, btnTicketValidation, btnOrderedMeals, btnMealReviews;
  private ToggleButton btnMealInsertion, btnConfiguration;
  private ToggleGroup tgTabButtons;

  /**
   * Constructor for RootPane
   * @param manager
   */
  public RootPane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  /**
   * This method is called once this object is created, it will configure every
   * aspect of the gui, where all buttons, text field and other elements appear.
   * In this case all navigation Buttons on the left, account balance and all
   * the views stacked on top of each other
   */
  private void createViews() {
    tgTabButtons = new ToggleGroup();
    Label title1 = new Label("University Meals");
    title1.setFont(Font.font(20));
    title1.setPadding(new Insets(20, 0, 20, 0));
    Label title2 = new Label("Made Easy - backoffice");
    title2.setFont(Font.font(20));
    title2.setPadding(new Insets(0, 0, 200, 0));

    btnMainMenu = new ToggleButton("Main Menu");
    btnMainMenu.setToggleGroup(tgTabButtons);
    btnMainMenu.setPrefWidth(250);
    btnMainMenu.setPrefHeight(100);
    btnMainMenu.setSelected(true);

    btnTicketValidation = new ToggleButton("Validate Tickets");
    btnTicketValidation.setToggleGroup(tgTabButtons);
    btnTicketValidation.setPrefWidth(250);
    btnTicketValidation.setPrefHeight(100);

    btnOrderedMeals = new ToggleButton("Ordered Meals");
    btnOrderedMeals.setToggleGroup(tgTabButtons);
    btnOrderedMeals.setPrefWidth(250);
    btnOrderedMeals.setPrefHeight(100);

    btnMealReviews = new ToggleButton("Meal Reviews");
    btnMealReviews.setToggleGroup(tgTabButtons);
    btnMealReviews.setPrefWidth(250);
    btnMealReviews.setPrefHeight(100);

    btnMealInsertion = new ToggleButton("Insert Meals");
    btnMealInsertion.setToggleGroup(tgTabButtons);
    btnMealInsertion.setPrefWidth(250);
    btnMealInsertion.setPrefHeight(100);

    btnConfiguration = new ToggleButton("Configuration");
    btnConfiguration.setToggleGroup(tgTabButtons);
    btnConfiguration.setPrefWidth(250);
    btnConfiguration.setPrefHeight(100);

    leftButtonsVBox = new VBox(title1, title2, btnMainMenu, btnTicketValidation,
        btnOrderedMeals, btnMealReviews, btnMealInsertion, btnConfiguration);
    leftButtonsVBox.setMinWidth(250);
    leftButtonsVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

    this.setLeft(leftButtonsVBox);

    this.setCenter(new StackPane(new MainMenuPane(manager),
        new TicketValidationPane(manager), new OrderedMealsPane(manager),
        new MealReviewsPane(manager), new MealInsertionPane(manager),
        new ConfigurationPane(manager)));
  }

  /**
   * this method is called after creating the view.
   * It's responsible to register all handlers and listeners
   * so the elements can be used.
   */
  private void registerHandlers() {
    btnMainMenu.setOnAction(actionEvent -> {
      manager.changeToMainMenu();
    });
    btnTicketValidation.setOnAction(actionEvent -> {
      manager.changeToTicketValidation();
    });
    btnOrderedMeals.setOnAction(actionEvent -> {
      manager.changeToOrderedMealsConsultation();
    });
    btnMealReviews.setOnAction(actionEvent -> {
      manager.changeToReviewsConsultation();
    });
    btnMealInsertion.setOnAction(actionEvent -> {
      manager.changeToMealInsertion();
    });
    btnConfiguration.setOnAction(actionEvent -> {
      manager.changeToConfiguration();
    });
  }
  private void update() {
  }
}
