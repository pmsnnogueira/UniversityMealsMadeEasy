package university_meals_made_easy.database.tables.transaction;

import university_meals_made_easy.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Ticket extends Transaction {
  private final int id;
  private final int appUserId;
  private final int timeSlotId;
  private final LocalDateTime dateTimeOfPurchase;
  private final LocalDateTime dateTimeOfValidation;

  public Ticket(int id, int appUserId, int timeSlotId,
                String dateTimeOfPurchaseString,
                String dateTimeOfValidationString)
      throws NullPointerException, IllegalArgumentException {
    if (dateTimeOfPurchaseString == null)
      throw new NullPointerException(
          "date time of purchase string cannot be null");
    this.id = id;
    this.appUserId = appUserId;
    this.timeSlotId = timeSlotId;
    try {
      dateTimeOfPurchase = LocalDateTime.parse(dateTimeOfPurchaseString,
          Logger.dateTimeFormatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
          "date time of purchase string must have valid format");
    }
    if (dateTimeOfValidationString == null)
      dateTimeOfValidation = null;
    else
      try {
        dateTimeOfValidation = LocalDateTime.parse(
            dateTimeOfValidationString, Logger.dateTimeFormatter);
      } catch (DateTimeParseException e) {
        throw new IllegalArgumentException(
            "date time of validation must have valid format");
      }
  }
  public int getId() {
    return id;
  }
  public int getAppUserId() {
    return appUserId;
  }
  public int getTimeSlotId() {
    return timeSlotId;
  }
  public LocalDateTime getDateTimeOfPurchase() {
    return dateTimeOfPurchase;
  }
  public String getDateTimeOfPurchaseAsString() {
    return dateTimeOfPurchase.format(Logger.dateTimeFormatter);
  }
  public LocalDateTime getDateTimeOfValidation() {
    return dateTimeOfValidation;
  }
  public String getDateTimeOfValidationAsString() {
    if (dateTimeOfValidation == null)
      return null;
    return dateTimeOfValidation.format(Logger.dateTimeFormatter);
  }

  @Override
  public String toString() {
    return String.format("Bought ticket: on %s", getDateTimeOfPurchaseAsString());
  }
}
