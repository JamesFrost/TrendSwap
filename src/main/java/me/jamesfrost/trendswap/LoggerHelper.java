package me.jamesfrost.trendswap;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * Handles all logging activity.
 * <p/>
 * Created by James on 30/10/2014.
 */
public class LoggerHelper implements Constants {

    private java.util.logging.Logger logger;
    private FileHandler fh;

    public LoggerHelper() {
        logger = java.util.logging.Logger.getLogger("MyLog");
    }

    /**
     * Writes log file when tweets sent.
     *
     */
    public void log(int percentageSuccess) {

        try {
            fh = new FileHandler(PATH_TO_LOG_FILE, LOG_FILE_SIZE_LIMIT, 1, true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            if (percentageSuccess == 0)
                logger.warning("No tweets sent.");
            else
                logger.info(percentageSuccess + "% swap attempts successful.");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
