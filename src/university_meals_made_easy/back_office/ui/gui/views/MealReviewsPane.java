package university_meals_made_easy.back_office.ui.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import university_meals_made_easy.back_office.model.ModelManager;
import university_meals_made_easy.back_office.model.fsm.State;

public class MealReviewsPane extends BorderPane {
  private final ModelManager manager;
  ListView<String> previousMealsListView;
  public MealReviewsPane(ModelManager manager) {
    this.manager = manager;

    createViews();
    registerHandlers();
    update();
  }

  private void createViews() {
    Label title = new Label("Meal Reviews");
    title.setFont(Font.font(50));
    VBox topVBox = new VBox(title);
    topVBox.setAlignment(Pos.CENTER);
    this.setTop(topVBox);

    Label previousMealsLabel = new Label("Previous Meals");
    previousMealsListView = new ListView<>();
    previousMealsListView.setPrefWidth(400);
    VBox leftVBox = new VBox(previousMealsLabel, previousMealsListView);
    leftVBox.setAlignment(Pos.CENTER);
    leftVBox.setPrefWidth(500);
    previousMealsListView.setPrefHeight(800);

    Label reviewsLabel = new Label("Reviews");
    ScrollPane scrollPane = new ScrollPane();
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

  private void registerHandlers() {
    manager.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
  }

  private void update() {

    this.setVisible(manager.getState() == State.REVIEWS_CONSULTATION);
  }
}
