package util.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * 
 * @author Vishnu Rameshbabu
 * @since 2024/07/11
 */
public class LoggerPrintWriter {
	private Logger logger;
	private String loggerLevelType;

	public LoggerPrintWriter(final String aLoggerLevelType) {
		this.loggerLevelType = aLoggerLevelType;
		this.logger = LogManager.getLogger();
	}

	public Logger getLoggerPrintWriter() {
		return this.logger;
	}

	public void setLoggerPrintWriter(final Logger aLogger) {
		this.logger = aLogger;
	}

	public void setLoggerLevelType(final String aLoggerLevelType) {
		this.loggerLevelType = aLoggerLevelType;
	}

	public String getLoggerLevelType() {
		return this.loggerLevelType;
	}
}
