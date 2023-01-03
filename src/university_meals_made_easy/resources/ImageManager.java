package university_meals_made_easy.resources;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;

/**
 * The AppUser Class contains images that can be displayed
 * inside the application.
 * Has the methods to get and return the images.
 */
public class ImageManager {

  private ImageManager() { }
  private static final HashMap<String, Image> images = new HashMap<>();

  /**
   *  Method that receives a filename and saves the
   *  specific image in a local variable.
   * @param filename
   * @return image
   */
  public static Image getImage(String filename) {
    Image image = images.get(filename);
    if (image == null)
      try (InputStream is = ImageManager.class.getResourceAsStream("images/"+filename)) {
        image = new Image(is);
        images.put(filename,image);
      } catch (Exception e) { return null; }
    return image;
  }
  /**
   *  Method that receives a filename from an external folder and saves the
   *  specific image in a local variable.
   * @param filename
   * @return
   */

  public static Image getExternalImage(String filename) {
    Image image = images.get(filename);
    if (image == null)
      try {
        image = new Image(filename);
        images.put(filename,image);
      } catch (Exception e) { return null; }
    return image;
  }

  /**
   * Method that remove from the hashMap an
   * image with a specific file name.
   * @param filename
   */
  public static void purgeImage(String filename) { images.remove(filename); }
}