/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package caffeine.logic;

/**
 * The model of events manage by the EventManager.
 * 
 * @version 	0.2
 * @author		Yann-Gaël Guéhéneuc
 */
public final class Event {
	public static final long NoID = -1;
	public static final String NoSourceName = "No source name";
	public static final int NoLineNumber = -1;
	public static final String NoComment = "No comment";
	public static final String NoReturnedValue = "void";

	public final static Event fromTraceData(
		final int uniqueIdentifier,
		final String data) {

		// Yann 2013/04/27: Weird behaviour of StringTokenizer
		// With the string (notice the two consecutive delimiters)
		//	NativeMethodAccessorImpl.java|-1|fieldModification0|Objects|-1|-1||java.lang.Object[]
		// The tokeniser:
		//	final StringTokenizer tokenizer = new StringTokenizer(data, "|");
		// return NO "null"/empty token for the two consecutive delimiters
		// thus screwing the building of the event. I know use a RegEx, which
		// fixes the problem nicely.
		// Hence, the following code is not valid anymore... How did it work
		// before already?
		//	final String[] tokens = new String[9];
		//	final StringTokenizer tokenizer = new StringTokenizer(data, "|");
		//	for (int i = 0; tokenizer.hasMoreTokens(); i++) {
		//		tokens[i] = tokenizer.nextToken();
		//	}
		//
		//	final Event event =
		//		new Event(
		//			tokens[0],
		//			Integer.parseInt(tokens[1]),
		//			tokens[2],
		//			tokens[3],
		//			Long.parseLong(tokens[4]),
		//			Long.parseLong(tokens[5]),
		//			tokens[6],
		//			uniqueIdentifier,
		//			tokens[7]);

		final String[] tokens = data.split("\\|");
		final Event event =
			new Event(
				tokens[0],
				Integer.parseInt(tokens[1]),
				tokens[2],
				tokens[3],
				Long.parseLong(tokens[4]),
				Long.parseLong(tokens[5]),
				tokens[6],
				uniqueIdentifier,
				tokens[7]);

		return event;
	}

	final private String sourceName;
	final private int lineNumber;
	final private String type;
	final private String name;
	final private long receiverID;
	final private long argumentID;
	final private String comment;
	final private int identifier;
	final private Object returnedValue;
	public Event(
		final String sourceName,
		final int lineNumber,
		final String type,
		final String name,
		final long receiverID,
		final long argumentID,
		final String comment,
		final int identifier,
		final Object returnedValue) {

		this.sourceName = sourceName;
		this.lineNumber = lineNumber;
		this.type = type;
		this.name = name;
		this.receiverID = receiverID;
		this.argumentID = argumentID;
		this.comment = comment;
		this.identifier = identifier;
		this.returnedValue = returnedValue;
	}
	public String getSourceName() {
		return this.sourceName;
	}
	public int getLineNumber() {
		return this.lineNumber;
	}
	public String getType() {
		return this.type;
	}
	public String getName() {
		return this.name;
	}
	public long getReceiverID() {
		return this.receiverID;
	}
	public long getArgumentID() {
		return this.argumentID;
	}
	public String getComment() {
		return this.comment;
	}
	public int getIdentifier() {
		return this.identifier;
	}
	public Object getReturnedValue() {
		return this.returnedValue;
	}
	public String toData() {
		// Yann 2002/07/03: Fix for returned value.
		// The return value may contain \n or \r,
		// I want to replace them with \\n or \\r.
		final String stringedReturnedValue = this.returnedValue.toString();
		final StringBuffer bufferedReturnedValue = new StringBuffer();
		for (int i = 0; i < stringedReturnedValue.length(); i++) {
			final char c = stringedReturnedValue.charAt(i);
			if (c == '\n') {
				bufferedReturnedValue.append("\\n");
			}
			else if (c == '\r') {
				bufferedReturnedValue.append("\\r");
			}
			else {
				bufferedReturnedValue.append(c);
			}
		}

		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.sourceName);
		buffer.append('|');
		buffer.append(this.lineNumber);
		buffer.append('|');
		buffer.append(this.type);
		buffer.append('|');
		buffer.append(this.name);
		buffer.append('|');
		buffer.append(this.receiverID);
		buffer.append('|');
		buffer.append(this.argumentID);
		buffer.append('|');
		buffer.append(this.comment);
		buffer.append('|');
		buffer.append(bufferedReturnedValue.toString());
		return buffer.toString();
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("\nUnique Id      = ");
		buffer.append(this.identifier);
		buffer.append("\nSource name    = ");
		buffer.append(this.sourceName);
		buffer.append("\nLine number    = ");
		buffer.append(this.lineNumber);
		buffer.append("\nType           = ");
		buffer.append(this.type);
		buffer.append("\nName           = ");
		buffer.append(this.name);
		buffer.append("\nReceiver ID    = ");
		buffer.append(this.receiverID);
		buffer.append("\nArgument ID    = ");
		buffer.append(this.argumentID);
		buffer.append("\nComment        = ");
		buffer.append(this.comment);
		buffer.append("\nReturned Value = ");
		buffer.append(this.returnedValue);
		return buffer.toString();
	}
	public String toTraceData() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.sourceName);
		buffer.append('|');
		buffer.append(this.lineNumber);
		buffer.append('|');
		buffer.append(this.type);
		buffer.append('|');
		buffer.append(this.name);
		buffer.append('|');
		buffer.append(this.receiverID);
		buffer.append('|');
		buffer.append(this.argumentID);
		buffer.append('|');
		buffer.append(this.comment);
		// Yann 2013/04/27: Test!
		// Test data are "anonymous", no identifiers.
		//	buffer.append('|');
		//	buffer.append(this.identifier);
		buffer.append('|');
		buffer.append(this.returnedValue);
		return buffer.toString();
	}
}
