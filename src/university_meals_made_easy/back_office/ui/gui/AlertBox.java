package university_meals_made_easy.back_office.ui.gui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class AlertBox extends Dialog<ButtonType> {
  public AlertBox(String title, String content) {
    super();
    this.setTitle(title);
    this.setContentText(content);
    this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
  }
}
