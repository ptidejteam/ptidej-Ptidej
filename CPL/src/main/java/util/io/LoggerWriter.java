package util.io;

import java.io.IOException;
import java.io.Writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * 
 * @author Vishnu Rameshbabu
 * @since 2024/07/11
 */
public class LoggerWriter extends Writer {
	private Logger logger;
	private String loggerLevelType;
	private static final String DEBUG = "debug";
	private static final String INFO = "info";
	private static final String WARN = "warn";
	private static final String TRACE = "trace";
	private static final String ERROR = "error";

	public LoggerWriter(final String loggerType) {
		this.loggerLevelType = loggerType;
		this.logger = LogManager.getLogger();
	}

	public Logger getLoggerWriter() {
		return this.logger;
	}

	public void setLoggerWriter(Logger logger) {
		this.logger = logger;
	}

	public void setLoggerLevelType(String loggerLevelType) {
		this.loggerLevelType = loggerLevelType;
	}

	public String getLoggerLevelType() {
		return this.loggerLevelType;
	}

	private void loggerWrite(final StringBuilder message) {
		switch (this.getLoggerLevelType()) {
		case DEBUG:
			this.getLoggerWriter().debug(message);
			break;
		case INFO:
			this.getLoggerWriter().info(message);
			break;
		case TRACE:
			this.getLoggerWriter().trace(message);
			break;
		case ERROR:
			this.getLoggerWriter().error(message);
			break;
		case WARN:
			this.getLoggerWriter().warn(message);
			break;
		default:
		}
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		final StringBuilder messageBuilder = new StringBuilder();

		boolean carraigeAndNewLineSkip = false;
		if (len == 2 && (cbuf[0] == '\r') && cbuf[1] == '\n') {
			carraigeAndNewLineSkip = true;
		}

		for (int i = off; i < len; i++) {
			messageBuilder.append(cbuf[i]);
		}
		if (!carraigeAndNewLineSkip) {
			this.loggerWrite(messageBuilder);
		}
	}

	@Override
	public void flush() throws IOException {
		// Nothing to do for Log4J
	}

	@Override
	public void close() throws IOException {
		// Nothing to do for Log4J
	}

}
