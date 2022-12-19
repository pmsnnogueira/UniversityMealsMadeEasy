package university_meals_made_easy.back_office.ui.gui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.transaction.Review;

import java.util.List;

/**
 * This view is a border pane that contains information about meal reviews
 */
public class MealReviewsPane extends BorderPane {
  private final ModelManager manager;
  private ListView<Meal> previousMealsListView;
  private ScrollPane scrollPane;

  /**
   * Constructor for MealReviewsPane
   * @param manager ModelManager instance
   */
  public MealReviewsPane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  /**
   * This method is called once this object is created, it will configure every
   * aspect of the gui, where all buttons, text field and other elements appear.
   * In this case a ListView
   */
  private void createViews() {
    
    Label title = new Label("Meal Reviews");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);

    Label previousMealsLabel = new Label("Previous Meals");
    previousMealsListView = new ListView<>();
    previousMealsListView.setPrefWidth(400);
    previousMealsListView.setPlaceholder(new Label(
        "There aren't previous meals"
    ));
    VBox leftVBox = new VBox(previousMealsLabel, previousMealsListView);
    leftVBox.setAlignment(Pos.CENTER);
    leftVBox.setPrefWidth(500);
    previousMealsListView.setPrefHeight(800);

    Label reviewsLabel = new Label("Reviews");
    scrollPane = new ScrollPane();
    scrollPane.setPrefHeight(800);
    scrollPane.setPrefWidth(700);
    VBox rightVBox = new VBox(reviewsLabel, scrollPane);
    rightVBox.setAlignment(Pos.CENTER);
    rightVBox.setPrefWidth(700);

    HBox centerHBox = new HBox(leftVBox, rightVBox);
    centerHBox.setAlignment(Pos.CENTER);
    centerHBox.setSpacing(30);
    this.setCenter(centerHBox);
  }

  /**
   * this method is called after creating the view.
   * It's responsible to register all handlers and listeners
   * so the elements can be used.
   */
  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    previousMealsListView.getSelectionModel().selectedItemProperty().addListener(
        (a, b,c) -> {
          Meal selectedMeal = previousMealsListView.getSelectionModel().getSelectedItem();
          if(selectedMeal == null)
            return;
          listReviews(selectedMeal);
        });
  }


  private void listReviews(Meal selectedMeal) {
    scrollPane.setContent(null);
    if(selectedMeal == null)
      return;
    List<Review> reviews = manager.getReviews(selectedMeal);
    if(reviews == null)
      return;
    VBox reviewsContainer = new VBox();
    reviewsContainer.getChildren().clear();
    reviewsContainer.setAlignment(Pos.CENTER);
    reviewsContainer.setSpacing(20);
    for(Review review : reviews) {
      Label rating = new Label(review.getRating() + "");
      TextArea comment = new TextArea(review.getComment());
      comment.setDisable(true);
      Label date = new Label(review.getDateTimeAsString());
      VBox container = new VBox(rating, comment, date);
      container.setAlignment(Pos.CENTER);
      container.setSpacing(5);
      container.setPrefWidth(600);
      container.setBackground(new Background(new BackgroundFill(
          Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
      reviewsContainer.getChildren().add(container);
    }
    scrollPane.setContent(reviewsContainer);
  }


  /**
   * this method is responsible to update the entire view everytime it
   * needs after a change
   */
  private void update() {
    scrollPane.setContent(null);
    previousMealsListView.getItems().clear();
    this.setVisible(manager.getState() == State.REVIEWS_CONSULTATION);
    List<Meal> previousMeals = manager.getPreviousMeals();
    if(previousMeals == null)
      return;
    previousMealsListView.getItems().addAll(previousMeals);
  }
}
