package net.ptidej.log.model;

public class Entry {
	private final TimeStamp timeStamp;
	private final Type type;
	private final Data data;

	public Entry(final TimeStamp aTimeStamp, final Type aType, final Data someData) {
		this.timeStamp = aTimeStamp;
		this.type = aType;
		this.data = someData;
	}

	public TimeStamp getTimeStamp() {
		return this.timeStamp;
	}

	public Type getType() {
		return this.type;
	}

	public Data getData() {
		return this.data;
	}
}
