package university_meals_made_easy.database.tables.transaction;

import university_meals_made_easy.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 *This class encapsulates the necessary data that define a Top off
 *
 */
public class TopOff extends Transaction {
  private final int id;
  private final int appUserId;
  private final LocalDateTime dateTime;
  private final float value;

  /**
   * Constructor for TopOff class that
   * receives some information about the refund,
   * and assign them to the local variables.
   * @param id
   * @param appUserId
   * @param dateTimeString
   * @param value
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  public TopOff(int id, int appUserId, String dateTimeString, float value)
      throws NullPointerException, IllegalArgumentException {
    if (dateTimeString == null)
      throw new NullPointerException("date time string cannot be null");
    if (value <= 0)
      throw new IllegalArgumentException("value cannot be 0 or negative");
    this.id = id;
    this.appUserId = appUserId;
    try {
      this.dateTime = LocalDateTime.parse(dateTimeString, Logger.dateTimeFormatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
          "date time string must have valid format");
    }
    this.value = value;
  }
  /**
   *
   * @return the id of the Top off
   */
  public int getId() {
    return id;
  }

  /**
   *
   * @return the id of the user
   */
  public int getAppUserId() {
    return appUserId;
  }
  public LocalDateTime getDateTime() {
    return dateTime;
  }
  public String getDateTimeAsString() {
    return dateTime.format(Logger.dateTimeFormatter);
  }
  public float getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.format("Top off balance: %.2f € on %s", value, getDateTimeAsString());
  }
}
