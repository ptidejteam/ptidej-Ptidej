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
	public LoggerPrintWriter(String loggerType) {
		this.loggerLevelType = loggerType;
		this.logger = LogManager.getLogger();
	}
	public Logger getLoggerPrintWriter() {
		return this.logger;
	}
	public void setLoggerPrintWriter(Logger logger) {
		this.logger = logger;
	}
	public void setLoggerLevelType(String loggerLevelType) {
		this.loggerLevelType=loggerLevelType;
	}
	
	public String getLoggerLevelType() {
		return this.loggerLevelType;
	}
	
}
