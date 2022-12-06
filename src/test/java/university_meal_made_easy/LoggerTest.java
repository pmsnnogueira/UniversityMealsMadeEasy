package test.java.university_meal_made_easy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import university_meals_made_easy.Logger;
import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {
        @Test
        @DisplayName("Should throw 'NullPointerException' if filename is NULL")
        void fileNameNull(){
                assertThrows(NullPointerException.class,()-> {
                        Logger.createInstance(null);
                });
        }

        @Test
        @DisplayName("Should throw 'UnsupportedOperationException' if different from null")
        void loggerNotnull(){
                Logger.createInstance();
                assertThrows(UnsupportedOperationException.class,()-> {
                        Logger.createInstance();
                });
        }

        @Test
        @DisplayName("Should throw 'IllegalArgumentException' if filename is blank")
        void fileNameBlank(){
                assertThrows(IllegalArgumentException.class,()-> {
                        Logger.createInstance(" ");
                });
        }

        @Test
        @DisplayName("Should throw 'UnsupportedOperationException' " +
                "if an instance of logger is already created")
        void instanceOfLoggerAlreadyCreated(){
                Logger.createInstance();
                assertThrows(UnsupportedOperationException.class,()-> {
                        Logger.createInstance("instance");
                });
        }

        @Test
        @DisplayName("Should not throw any exception if able to create instance successfully")
        void instanceCreatedSuccefully(){
                assertDoesNotThrow(()-> {
                        Logger.createInstance("instance");
                });

        }

        @Test
        @DisplayName("Should throw 'UnsupportedOperationException' if logger equals null")
        void throwsIfLoggerNull(){
                assertThrows(UnsupportedOperationException.class,()-> {
                     Logger logger= Logger.getInstance();
                });
        }

        @Test
        @DisplayName("Should return an Instance of Logger if previously Created")
        void returnsAnInstanceOfLogger(){
                Logger.createInstance();
                assertNotNull(Logger.getInstance());
        }

        @Test
        @DisplayName("Should throw 'NullPointerException' if tag equals null (ERROR)")
        void throwsIftagNullError(){
                Logger.createInstance();
                Logger logger = Logger.getInstance();
                assertThrows(NullPointerException.class,()-> {
                        logger.error(null,"message");
                });

        }

        @Test
        @DisplayName("Should throw 'NullPointerException' if message equals null (ERROR)")
        void throwsIfMessageNullError(){
                Logger.createInstance();
                Logger logger = Logger.getInstance();
                assertThrows(NullPointerException.class,()-> {
                        logger.error("tag",null);
                });
        }

        @Test
        @DisplayName("Logs 'ERROR' correctly")
        void messageAndTagNotNullShouldLogError(){
                Logger.createInstance();
                Logger logger = Logger.getInstance();
                assertDoesNotThrow(()-> {
                        logger.error("tag","message");
                });
        }

        @Test
        @DisplayName("Should throw 'NullPointerException' if message equals null (INFO)")
        void throwsIfMessageNullInfo(){
                Logger.createInstance();
                Logger logger = Logger.getInstance();
                assertThrows(NullPointerException.class,()-> {
                        logger.info("tag",null);
                });
        }


        @Test
        @DisplayName("Should throw 'NullPointerException' if tag equals (INFO)")
        void throwsIftagNullInfo(){
                Logger.createInstance();
                Logger logger = Logger.getInstance();
                assertThrows(NullPointerException.class,()-> {
                        logger.info(null,"message");
                });
        }

        @Test
        @DisplayName("Logs 'INFO' correctly")
        void messageAndTagNotNullShouldLogInfo(){
                Logger.createInstance();
                Logger logger = Logger.getInstance();
                assertDoesNotThrow(()-> {
                        logger.info("tag","message");
                });

        }

        @Test
        @DisplayName("Should close bufferedWriter if different from null")
        void bufferedWriterIsNotNull(){
                Logger.createInstance();
                Logger logger = Logger.getInstance();
                assertDoesNotThrow(()-> {
                        logger.close();
                });
        }
}
