package university_meals_made_easy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
  public static void createInstance() throws UnsupportedOperationException {
    if (logger != null)
      throw new UnsupportedOperationException("instance already created");
    logger = new Logger();
  }
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
  public static Logger getInstance() throws UnsupportedOperationException {
    if (logger == null)
      throw new UnsupportedOperationException("instance hasn't been created");
    return logger;
  }
  private Logger() {
    bufferedWriter = null;
  }
  private Logger(String filename) throws IOException {
    bufferedWriter = new BufferedWriter(new FileWriter(filename));
  }
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
  public void error(String tag, String message) throws NullPointerException {
    if (tag == null)
      throw new NullPointerException("tag cannot be null");
    if (message == null)
      throw new NullPointerException("message cannot be null");
    log("ERROR", tag, message);
  }
  public void info(String tag, String message) {
    if (tag == null)
      throw new NullPointerException("tag cannot be null");
    if (message == null)
      throw new NullPointerException("message cannot be null");
    log("INFO", tag, message);
  }
  public void close() {
    try {
      if (bufferedWriter != null)
        bufferedWriter.close();
    } catch (IOException ignored) {}
  }
}
