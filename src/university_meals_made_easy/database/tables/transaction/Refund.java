package university_meals_made_easy.database.tables.transaction;

import university_meals_made_easy.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Refund extends Transaction {
  private final int id;
  private final int appUserId;
  private final int ticketId;
  private final LocalDateTime dateTime;

  public Refund(int id, int appUserId, int ticketId,
                String dateTimeString)
      throws NullPointerException, IllegalArgumentException {
    if (dateTimeString == null)
      throw new NullPointerException("date time string cannot be null");
    this.id = id;
    this.appUserId = appUserId;
    this.ticketId = ticketId;
    try {
      dateTime = LocalDateTime.parse(dateTimeString,
          Logger.dateTimeFormatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
          "date time string must have valid format");
    }
  }
  public int getId() {
    return id;
  }
  public int getAppUserId() {
    return appUserId;
  }
  public int getTicketId() {
    return ticketId;
  }
  public LocalDateTime getDateTime() {
    return dateTime;
  }
  public String getDateTimeAsString() {
    return dateTime.format(Logger.dateTimeFormatter);
  }
}
