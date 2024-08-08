/*
 * (c) Copyright 2001-2003 Yann-Gaël Guéhéneuc,
 * ecole des Mines de Nantes and Object Technology International, Inc.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package caffeine.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.junit.Assert;

import JIP.engine.JIPTerm;
import caffeine.Caffeine;
import caffeine.Constants;
import caffeine.logic.Engine;
import caffeine.logic.EngineListener;
import caffeine.logic.Event;
import junit.framework.TestCase;

/**
 * @author Yann-Gaël Guéhéneuc
 * @version 0.2
 */
public class Primitive extends TestCase {
	private static final int JDK_141 = 1;
	private static final int JDK_142 = 2;
	private static int CURRENT_JDK = JDK_142;

	private StringBuffer debugBuffer = new StringBuffer();
	private EngineListener engineListener;
	private boolean isProgramTerminated;
	private Throwable thrownException;

	public Primitive(final String name) {
		super(name);
	}

	public void setProgramTerminated(final boolean isProgramTerminated) {
		this.isProgramTerminated = isProgramTerminated;
	}

	public void setUp() {
		this.engineListener = new EngineListener() {
			private LineNumberReader lineNumberReader;

			public void engineInitialized(final Engine engine) {
				System.out.println();
			}

			public void engineStarted(final Engine engine) {
				System.out.println();
				// Yann 2003/08/12: Exception.
				// To ease the development of tests, I only
				// warn the user if a "Test.trace" file cannot
				// be found.
				try {
					// Yann 2003/08/05: Trace file!
					// I assume there exists one and only one trace
					// file per test-class, named "Test.trace".
					this.lineNumberReader = new LineNumberReader(
							new InputStreamReader(Primitive.this.getClass()
									.getResourceAsStream("Test.trace")));
				}
				catch (final NullPointerException npe) {
					System.err.print(
							"Cannot find the \"Test.trace\" file for test ");
					System.err.println(Primitive.this.getClass().getName());
				}
			}

			public void engineTerminated(JIPTerm solution, Engine engine,
					long time, long steps) {
				System.out.println();

				// Yann 2003/08/12: Test!
				// If a file named "Test.solution" exists,
				// I compare its content with the solution.
				final InputStream solutionInputStream = Primitive.this
						.getClass().getResourceAsStream("Test.solution");
				if (solutionInputStream != null) {
					final LineNumberReader reader = new LineNumberReader(
							new InputStreamReader(solutionInputStream));
					try {
						Assert.assertEquals("Solution", reader.readLine(),
								solution.toString());
					}
					catch (final Throwable throwable) {
						// throwable.printStackTrace();
						Primitive.this.setThrownException(throwable);
						Primitive.this.setProgramTerminated(true);
					}
					finally {
						try {
							reader.close();
						}
						catch (final IOException e) {
							e.printStackTrace();
						}
					}
				}
				Primitive.this.setProgramTerminated(true);
			}

			public void eventEmitted(final Event event) {
				// Yann 2004/09/08: Debug!
				// I store all the emitted events in a buffer for
				// later comparison if necessary while debugging.
				Primitive.this.debugBuffer.append(event.toData());
				Primitive.this.debugBuffer.append('\n');

				// Yann 2004/09/08: JDK v1.4.2???
				// For some reasons, the JDK v1.4.2 does not behave as
				// expected: The line number is screwed up and the file
				// name for finalizer is now empty (instead of
				// "Finalizer.java").
				if (Primitive.CURRENT_JDK == Primitive.JDK_141) {
					if (this.lineNumberReader != null) {
						try {
							Assert.assertEquals(
									"Line " + (this.lineNumberReader
											.getLineNumber() + 1),
									"\n\t" + this.lineNumberReader.readLine()
											+ '\n',
									"\n\t" + event.toData() + '\n');
						}
						catch (final Throwable throwable) {
							throwable.printStackTrace();
							Primitive.this.setThrownException(throwable);
							Primitive.this.setProgramTerminated(true);
						}
					}
				}
				else if (Primitive.CURRENT_JDK == Primitive.JDK_142) {
					if (this.lineNumberReader != null) {
						Event expectedEvent = null;
						try {
							expectedEvent = Event.fromTraceData(
									event.getIdentifier(),
									this.lineNumberReader.readLine());

							Assert.assertEquals(
									"Line " + (this.lineNumberReader
											.getLineNumber() + 1),
									"\n\t" + expectedEvent.getArgumentID()
											+ '\n',
									"\n\t" + event.getArgumentID() + '\n');
							Assert.assertEquals(
									"Line " + (this.lineNumberReader
											.getLineNumber() + 1),
									"\n\t" + expectedEvent.getComment() + '\n',
									"\n\t" + event.getComment() + '\n');
							// CaffeineLauncher.assertEquals(
							// "Line "
							// + (this.lineNumberReader.getLineNumber()
							// + 1),
							// "\n\t" + expectedEvent.getIdentifier() +'\n',
							// "\n\t" + event.getIdentifier() + '\n');
							// CaffeineLauncher.assertEquals(
							// "Line "
							// + (this.lineNumberReader.getLineNumber()
							// + 1),
							// "\n\t" + expectedEvent.getLineNumber() +'\n',
							// "\n\t" + event.getLineNumber() + '\n');
							Assert.assertEquals(
									"Line " + (this.lineNumberReader
											.getLineNumber() + 1),
									"\n\t" + expectedEvent.getName() + '\n',
									"\n\t" + event.getName() + '\n');
							Assert.assertEquals(
									"Line " + (this.lineNumberReader
											.getLineNumber() + 1),
									"\n\t" + expectedEvent.getReceiverID()
											+ '\n',
									"\n\t" + event.getReceiverID() + '\n');
							Assert.assertEquals(
									"Line " + (this.lineNumberReader
											.getLineNumber() + 1),
									"\n\t" + expectedEvent.getReturnedValue()
											+ '\n',
									"\n\t" + event.getReturnedValue() + '\n');
							// CaffeineLauncher.assertEquals(
							// "Line "
							// + (this.lineNumberReader.getLineNumber()
							// + 1),
							// "\n\t" + expectedEvent.getSourceName() +'\n',
							// "\n\t" + event.getSourceName() + '\n');
							Assert.assertEquals(
									"Line " + (this.lineNumberReader
											.getLineNumber() + 1),
									"\n\t" + expectedEvent.getType() + '\n',
									"\n\t" + event.getType() + '\n');
						}
						catch (final Throwable throwable) {
							// throwable.printStackTrace();
							Primitive.this.setThrownException(throwable);
							Primitive.this.setProgramTerminated(true);
						}
					}
				}
			}
		};

		Caffeine.getUniqueInstance().addCaffeineListerner(this.engineListener);
		// Caffeine.getUniqueInstance().addCaffeineListerner(
		// new TestDataGeneratorFromEvents());

		this.isProgramTerminated = false;
		this.thrownException = null;
	}

	public void setThrownException(final Throwable thrownException) {
		this.thrownException = thrownException;
	}

	public void tearDown() {
		try {
			// Yann 2003/08/12: Multi-threading.
			// I make sure the current thread does not
			// take all the process computation time by
			// making it yield and sleep before checking
			// the stop condition.
			while (!this.isProgramTerminated) {
				Thread.yield();
				Thread.sleep(Constants.VACUUM_CLEANER_WAKE_UP_TIME);
			}

			// Yann 2013/04/27: Cleanup!
			// I did not forget to stop Caffeine to be sure
			// that the event manager, the dynamically-created
			// JVM, and so on are thrown out before the next
			// tests... but I must also wait a bit to prevent
			// some weird bug (data race?) in which the tests
			// composition3 and composition4 would get mixed up!?
			Thread.sleep(Constants.VACUUM_CLEANER_WAKE_UP_TIME
					* Constants.VACUUM_CLEANER_LATENT_PERIOD
					* Constants.VACUUM_CLEANER_LATENT_PERIOD);
		}
		catch (final InterruptedException ie) {
			ie.printStackTrace();
		}
		Caffeine.getUniqueInstance()
				.removeCaffeineListerner(this.engineListener);
		Caffeine.getUniqueInstance().stop();
		if (this.thrownException != null) {
			throw new RuntimeException(this.thrownException);
		}
	}
}
