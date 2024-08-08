package util.io;
/*
 * 
 * @author Vishnu Rameshbabu
 * @since 2024/07/11
 */
import java.io.PrintWriter;
import java.io.Writer;

import org.apache.logging.log4j.ThreadContext;

public class MultiChannelPrintWriter extends PrintWriter  {
	private LoggerPrintWriter logger;
	private PrintWriter printWriter;
	private static final String DEBUG = "debug";
	private static final String INFO = "info";
	private static final String WARN = "warn";
	private static final String TRACE = "trace";
	private static final String ERROR = "error";
	public MultiChannelPrintWriter(final Writer writer) {
		super(writer, true);
	}
	public MultiChannelPrintWriter(LoggerPrintWriter logger, final Writer writer) {
		super(writer, true);
		this.logger = logger;
		this.printWriter = (PrintWriter) writer;
		
	}
	public void write(final char[] buf, final int pos, final int len) {
		super.write(buf, pos, len);
		this.flush();
	}
	public void print(final char charc) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		
		String className = stackTrace[3].getClassName();
		ThreadContext.put("className", className);
		this.loggerPrint(charc,className);
		ThreadContext.clearAll();
		this.printWriter.print(charc);
	}
	public void print(final String message) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		
		String className = stackTrace[3].getClassName();
		ThreadContext.put("className", className);
		this.loggerPrint(message,className);
		ThreadContext.clearAll();
		this.printWriter.print(message);
	}
	public void println(final char charc) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String className = stackTrace[3].getClassName();
		ThreadContext.put("className", className);
		this.loggerPrint(charc,className);
		ThreadContext.clearAll();
		this.printWriter.println(charc);
	}
	public void println(final String message) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String className = stackTrace[3].getClassName();
		ThreadContext.put("className", className);
		this.loggerPrint(message,className);
		ThreadContext.clearAll();
		this.printWriter.println(message);
	}
	private void loggerPrint(String message,String className) {
		switch(this.logger.getLoggerLevelType()) {
			case DEBUG:
				this.logger.getLoggerPrintWriter().debug(message);
				break;
			case INFO:
				this.logger.getLoggerPrintWriter().info(message);
				break;
			case TRACE:
				this.logger.getLoggerPrintWriter().trace(message);
				break;
			case ERROR:
				this.logger.getLoggerPrintWriter().error(message);
				break;
			case WARN:
				this.logger.getLoggerPrintWriter().warn(message);
				break;
			default:
		}
		}
	private void loggerPrint(final char message,String className) {
		switch(this.logger.getLoggerLevelType()) {
			case DEBUG:
				this.logger.getLoggerPrintWriter().debug(message);
				break;
			case INFO:
				this.logger.getLoggerPrintWriter().info(message);
				break;
			case TRACE:
				this.logger.getLoggerPrintWriter().trace(message);
				break;
			case ERROR:
				this.logger.getLoggerPrintWriter().error(message);
				break;
			case WARN:
				this.logger.getLoggerPrintWriter().warn(message);
				break;
			default:
		}
		}
}
