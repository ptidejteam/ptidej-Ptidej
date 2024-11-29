package util.io;

/*
 * 
 * @author Vishnu Rameshbabu
 * @since 2024/07/11
 */
import java.io.PrintWriter;
import java.io.Writer;

import org.apache.logging.log4j.ThreadContext;

public class MultiChannelPrintWriter extends PrintWriter {
	private PrintWriter printWriter1;
	private PrintWriter printWriter2;

	public MultiChannelPrintWriter(final Writer writer) {
		super(writer, true);
	}

	public MultiChannelPrintWriter(final PrintWriter writer1,
			final PrintWriter writer2) {
		super(writer2, true);
		this.printWriter1 = writer1;
		this.printWriter2 = writer2;
	}

	public void write(final char[] buf, final int pos, final int len) {
		super.write(buf, pos, len);
		this.flush();
	}

	public void print(final char charc) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String className = stackTrace[3].getClassName();
		ThreadContext.put("className", className);
		ThreadContext.clearAll();
		this.printWriter1.print(charc);
		this.printWriter2.print(charc);

	}

	public void print(final String message) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String className = stackTrace[3].getClassName();
		ThreadContext.put("className", className);

		this.printWriter1.print(message);
		this.printWriter2.print(message);
	}

	public void println(final char charc) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String className = stackTrace[3].getClassName();
		ThreadContext.put("className", className);

		this.printWriter1.println(charc);
		this.printWriter2.println(charc);
	}

	public void println(final String message) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String className = stackTrace[3].getClassName();
		ThreadContext.put("className", className);

		this.printWriter1.println(message);
		this.printWriter2.println(message);
	}

}
