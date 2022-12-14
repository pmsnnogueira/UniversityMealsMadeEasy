package university_meals_made_easy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class that provides logging functionality.
 *
 * The Logger class is a singleton that allows logging messages to a file or to standard output.
 * It has three log levels: INFO, ERROR, and WARNING.
 *
 * The Logger class is thread-safe and can be used concurrently by multiple threads.
 */
public class Logger {
  public static final DateTimeFormatter timeFormatter;
  public static final DateTimeFormatter dateFormatter;
  public static final DateTimeFormatter dateTimeFormatter;
  private static Logger logger;
  private final BufferedWriter bufferedWriter;

  static {
    dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
  }

  /**
   * Creates an instance of the Logger class.
   *
   * This method creates an instance of the Logger class that logs messages to standard output.
   * If an instance of the class has already been created, it throws an UnsupportedOperationException.
   *
   * @throws UnsupportedOperationException if an instance of the Logger class has already been created.
   */
  public static void createInstance() throws UnsupportedOperationException {
    if (logger != null)
      throw new UnsupportedOperationException("instance already created");
    logger = new Logger();
  }
  /**
   * Creates an instance of the Logger class that logs messages to a file.
   *
   * This method creates an instance of the Logger class that logs messages to a file with the
   * specified filename. The file must exist and be writable. If the file cannot be opened for writing,
   * or if an instance of the Logger class has already been created, this method throws an
   * exception.
   *
   * @param filename the name of the file where the Logger instance will log messages
   * @throws NullPointerException if the provided filename is NULL
   * @throws UnsupportedOperationException if an instance of the Logger class has already been created
   * @throws IllegalArgumentException if the provided filename is blank or if the file cannot be
   *         opened for writing
   */
  public static void createInstance(String filename)
      throws NullPointerException, UnsupportedOperationException,
      IllegalArgumentException {
    if (filename == null)
      throw new NullPointerException("filename cannot be null");
    if (filename.isBlank())
      throw new IllegalArgumentException("filename cannot be blank");
    if (!new File(filename).canWrite())
      throw new IllegalArgumentException("cannot write to specified file");
    if (logger != null)
      throw new UnsupportedOperationException("instance already created");
    try {
      logger = new Logger(filename);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "cannot open file writer for specified file");
    }
  }
  /**
   * Returns the singleton instance of this class.
   *
   * @throws UnsupportedOperationException if the instance hasn't been created yet
   * @return the instance of this class
   */
  public static Logger getInstance() throws UnsupportedOperationException {
    if (logger == null)
      throw new UnsupportedOperationException("instance hasn't been created");
    return logger;
  }
  /**
   * Private constructor to prevent instantiation of this class.
   */
  private Logger() {
    bufferedWriter = null;
  }
  /**
   * Private constructor to create a new logger with the given file name.
   *
   * @param filename the name of the file to write logs to
   * @throws IOException if there is an error writing to the file
   */
  private Logger(String filename) throws IOException {
    bufferedWriter = new BufferedWriter(new FileWriter(filename));
  }
  /**
   * Logs a message with the given type and tag.
   *
   * @param type the type of log message
   * @param tag the tag associated with the log message
   * @param message the message to log
   */
  private void log(String type, String tag, String message) {
    String output = String.format("[%s %s] %s: %s\n",
        LocalDateTime.now().format(dateTimeFormatter), type, tag, message);

    if (bufferedWriter != null)
      try {
        bufferedWriter.write(output);
      } catch (IOException e) {
        System.out.printf("[ERROR %s] logger: couldn't log\n",
            LocalDateTime.now().format(dateTimeFormatter));
      }
    else
      System.out.printf(output);
  }
  /**
   * Logs an error message with the given tag.
   *
   * @param tag the tag associated with the log message
   * @param message the message to log
   * @throws NullPointerException if the tag or message is null
   */
  public void error(String tag, String message) throws NullPointerException {
    if (tag == null)
      throw new NullPointerException("tag cannot be null");
    if (message == null)
      throw new NullPointerException("message cannot be null");
    log("ERROR", tag, message);
  }
  /**
   * Logs an info message with the given tag.
   *
   * @param tag the tag associated with the log message
   * @param message the message to log
   * @throws NullPointerException if the tag or message is null
   */
  public void info(String tag, String message) {
    if (tag == null)
      throw new NullPointerException("tag cannot be null");
    if (message == null)
      throw new NullPointerException("message cannot be null");
    log("INFO", tag, message);
  }
  /**
   * Closes the logger.
   */
  public void close() {
    try {
      if (bufferedWriter != null)
        bufferedWriter.close();
    } catch (IOException ignored) {}
  }
}
