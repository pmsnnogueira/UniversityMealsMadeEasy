package university_meals_made_easy.back_office.ui.gui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * This a simple customizable DialogBox its purpose is to show simple messages
 */
public class AlertBox extends Dialog<ButtonType> {

  /**
   * Constructor for AlertBox
   * use AlertBox.show() after instancing it
   * @param title
   * @param content
   */
  public AlertBox(String title, String content) {
    super();
    this.setTitle(title);
    this.setContentText(content);
    this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
  }
}
